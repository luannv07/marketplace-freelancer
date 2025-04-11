package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.request.UserLoginRequest;
import com.luannv.mf.dto.request.UserUpdateRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.PermissionRepository;
import com.luannv.mf.repositories.UserRepository;
import com.luannv.mf.services.UserService;
import com.luannv.mf.utils.JwtUtils;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
	UserService userService;
	// api for testing

	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	PermissionRepository permissionRepository;
	@PostMapping("/test")
	public ResponseEntity<ApiResponse> testApi(@RequestBody UserLoginRequest userLoginRequest) {
		User user = userRepository.findByUsername(userLoginRequest.getUsername())
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
			throw new SingleErrorException(ErrorCode.PASSWORD_NOTVALID);
		String token="";
		try {
			token = new JwtUtils(permissionRepository).generateToken(user, "dITUgWzVXsZguJ3c/+tVFF2thcHHP/LIpaefcp3HhcbllObpJBXppBLLImVUgqOd");
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
										.timestamp(System.currentTimeMillis())
										.result(token)
						.build());
	}
	@GetMapping
	public ResponseEntity<ApiResponse> getAllUsers() {
		List<UserResponse> users = userService.getAll();
		return ResponseEntity.ok().body(ApiResponse.<Void, List<UserResponse>>builder()
						.timestamp(System.currentTimeMillis())
						.result(users)
						.build());
	}

	@GetMapping("/{username}")
	public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.getByUsername(username))
						.build());
	}

	@PostMapping
	public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserCreationRequest userCreationRequest,
																								BindingResult bindingResult) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.addUser(userCreationRequest, bindingResult))
						.build());
	}
	@PutMapping("/{username}")
	public ResponseEntity<ApiResponse> editUser(@PathVariable String username,
																							@Valid @RequestBody UserUpdateRequest userUpdateRequest,
																							BindingResult bindingResult) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.updateUser(username, userUpdateRequest, bindingResult))
						.build());
	}
	@DeleteMapping("/{username}")
	public ResponseEntity<ApiResponse> removeUser(@PathVariable String username) {
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.deleteUserByUsername(username))
						.build());
	}
}
