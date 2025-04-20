package com.luannv.mf.controllers;

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
import com.luannv.mf.utils.ServletUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User Controller", description = "Users Management")
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
		String token = "";
//		try {
//			token = new JwtUtils(permissionRepository).generateToken(user, "dITUgWzVXsZguJ3c/+tVFF2thcHHP/LIpaefcp3HhcbllObpJBXppBLLImVUgqOd");
//		} catch (JOSEException e) {
//			throw new RuntimeException(e);
//		}
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(token)
						.build());
	}
	@PreAuthorize(value = "hasRole('ADMIN')")
	@GetMapping
	@Operation(description = "Only for User who have the ADMIN role.", summary = "Get all users")
	public ResponseEntity<ApiResponse> getAllUsers() {
		List<UserResponse> users = userService.getAll();
		return ResponseEntity.ok().body(ApiResponse.<Void, List<UserResponse>>builder()
						.timestamp(System.currentTimeMillis())
						.result(users)
						.build());
	}
	@PreAuthorize(value = "hasAuthority('USER_VIEW')")
	@GetMapping("/{username}")
	@Operation(description = "All user can find another user.", summary = "Get user info")
	public ResponseEntity<ApiResponse> getUserByUsername(@Parameter(description = "username for find", required = true) @PathVariable String username) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.getByUsername(username))
						.build());
	}
	@PreAuthorize(value = "hasRole('ADMIN') or #username == authentication.name")
	@PutMapping("/{username}")
	@Operation(description = "Only user who have the ADMIN role or username equals username gave can update.", summary = "Update user")
	public ResponseEntity<ApiResponse> editUser(@Parameter(description = "username for update", required = true) @PathVariable String username,
																							@Valid @RequestBody UserUpdateRequest userUpdateRequest,
																							BindingResult bindingResult) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.updateUser(username, userUpdateRequest, bindingResult))
						.build());
	}
	@PreAuthorize(value = "hasRole('ADMIN') or #username == authentication.name")
	@DeleteMapping("/{username}")
	@Operation(description = "The user who have the ADMIN role or username equals username gave can delete.", summary = "Delete user")
	public ResponseEntity<ApiResponse> removeUser(@PathVariable String username) {
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.deleteUserByUsername(username))
						.build());
	}
}
