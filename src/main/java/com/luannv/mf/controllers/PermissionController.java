package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.PermissionRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.PermissionResponse;
import com.luannv.mf.services.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Permission Controller", description = "Manage system permissions")
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {
	PermissionService permissionService;

	@GetMapping
	@Operation(summary = "Get all permissions", description = "Retrieve all permissions available in the system")
	public ResponseEntity<ApiResponse> getAllPermissions() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return ResponseEntity.ok().body(ApiResponse.<Void, List<PermissionResponse>>builder()
						.timestamp(System.currentTimeMillis())
						.result(permissionService.getAll())
						.build());
	}

	@PostMapping
	@Operation(summary = "Create a permission", description = "Create a new permission in the system")
	public ResponseEntity<ApiResponse> createPermission(
					@Parameter(description = "Permission request data", required = true)
					@RequestBody PermissionRequest permissionRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, PermissionResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(permissionService.create(permissionRequest))
						.build());
	}

	@PutMapping("/{name}")
	@Operation(summary = "Update a permission", description = "Update an existing permission by its name")
	public ResponseEntity<ApiResponse> updatePermission(
					@Parameter(description = "Name of the permission to update", required = true)
					@PathVariable String name,
					@Parameter(description = "Updated permission data", required = true)
					@RequestBody PermissionRequest permissionRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, PermissionResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(permissionService.editPermission(name, permissionRequest))
						.build());
	}

	@DeleteMapping("/{name}")
	@Operation(summary = "Delete a permission", description = "Delete a permission by its name")
	public ResponseEntity<ApiResponse> removePermission(
					@Parameter(description = "Name of the permission to delete", required = true)
					@PathVariable String name) {
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(permissionService.deletePermission(name))
						.build());
	}

	@DeleteMapping
	@Operation(summary = "Delete all permissions", description = "Remove all permissions from the system")
	public ResponseEntity<ApiResponse> removeAllPermission() {
		boolean isDeleted = permissionService.deleteAllPermission();
		return ResponseEntity.ok().body(ApiResponse.<Void, Boolean>builder()
						.timestamp(System.currentTimeMillis())
						.result(isDeleted)
						.build());
	}
}
