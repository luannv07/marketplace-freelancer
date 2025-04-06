package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.request.UserUpdateRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.services.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
	UserService userService;

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
	public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreationRequest userCreationRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.addUser(userCreationRequest))
						.build());
	}
	@PutMapping("/{username}")
	public ResponseEntity<ApiResponse> editUser(@PathVariable String username, @RequestBody UserUpdateRequest userUpdateRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.updateUser(username, userUpdateRequest))
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
