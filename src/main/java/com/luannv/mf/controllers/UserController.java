package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.request.UserUpdateRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
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
	@PostMapping("/test")
	public ResponseEntity<ApiResponse> testApi(@Valid @RequestBody UserCreationRequest userCreationRequest,
																						 BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		if (bindingResult.hasErrors())
			bindingResult.getFieldErrors()
							.forEach(fieldError -> errors
											.put(fieldError.getField(), ErrorCode.valueOf(fieldError.getDefaultMessage()).getMessages()));
		return ResponseEntity.ok().body(ApiResponse.<Void, Map<String, String>>builder()
						.timestamp(System.currentTimeMillis())
						.result(errors)
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
