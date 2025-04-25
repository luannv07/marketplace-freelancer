package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.*;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.repositories.PermissionRepository;
import com.luannv.mf.repositories.UserRepository;
import com.luannv.mf.services.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

		Boolean containRoles = SecurityContextHolder
						.getContext()
						.getAuthentication()
						.getAuthorities()
						.stream()
						.anyMatch(grantedAuthority -> {
							System.out.println(RoleEnum.ADMIN.name() + " " + grantedAuthority.getAuthority());
							return grantedAuthority
											.getAuthority()
											.equalsIgnoreCase("ROLE_" + RoleEnum.ADMIN.name());
						});
		if (containRoles) {
			System.out.println(">> Contain");
		}
		return ResponseEntity.ok().body(ApiResponse.<Void, Void>builder().build());
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

	@PreAuthorize(value = "hasAuthority('USER_VIEW')")
	@GetMapping("/me")
	@Operation(description = "My info.", summary = "My info")
	public ResponseEntity<ApiResponse> getMyInfo() {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.getMyInfo())
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
	@PutMapping("/{username}/password")
	@Operation(description = "Only user who have the ADMIN role or username equals username gave can update.", summary = "Update user password")
	public ResponseEntity<ApiResponse> editUser(@Parameter(description = "username for update", required = true) @PathVariable String username,
																							@Valid @RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest,
																							BindingResult bindingResult) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.updatePasswordUser(username, userPasswordUpdateRequest, bindingResult))
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

	@PreAuthorize(value = "hasRole('ADMIN')")
	@DeleteMapping
	@Operation(description = "Clear all users.", summary = "Delete all user")
	public ResponseEntity<ApiResponse> deleteAllUsers() {
		return ResponseEntity.ok().body(ApiResponse.<Void, Void>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.deleteAll())
						.build());
	}

	@PreAuthorize(value = "hasRole('ADMIN') or #username == authentication.name")
	@PostMapping("/completed/details/client/{username}")
	@Operation(description = "Add company name", summary = "Addtional fields")
	public ResponseEntity<ApiResponse> addClientDetals(@PathVariable String username, @RequestBody ClientFieldsRequest clientFieldsRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.addFieldDetailsClient(username, clientFieldsRequest)) // username lay tu SecurityContextHolder
						.build());
	}

	@PreAuthorize(value = "hasRole('ADMIN') or #username == authentication.name")
	@PostMapping("/completed/details/freelancer/{username}")
	@Operation(description = "Add skills", summary = "Addtional fields")
	public ResponseEntity<ApiResponse> addFreelancerDetals(@PathVariable String username, @RequestBody FreelancerFieldsRequest freelancerFieldsRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(userService.addFieldDetailsFreelancer(username, freelancerFieldsRequest)) // username lay tu SecurityContextHolder
						.build());
	}

}
