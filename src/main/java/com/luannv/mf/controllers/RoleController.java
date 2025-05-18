package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.RoleRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Role Controller", description = "Roles Management")
public class RoleController {
	RoleService roleService;

	@GetMapping
	@Operation(summary = "Get all roles", description = "Retrieve a list of all available roles")
	public ResponseEntity<ApiResponse> getAllRoles() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return ResponseEntity.ok().body(ApiResponse.<Void, List<RoleResponse>>builder()
						.result(roleService.getAll())
						.timestamp(System.currentTimeMillis())
						.build());
	}

	@PostMapping
	@Operation(summary = "Create a new role", description = "Only ADMIN should use this to add a new role")
	public ResponseEntity<ApiResponse> createRole(
					@Parameter(description = "Role data to be created", required = true)
					@RequestBody RoleRequest roleRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, RoleResponse>builder()
						.result(roleService.addRole(roleRequest))
						.timestamp(System.currentTimeMillis())
						.build());
	}

	@PutMapping("/{name}")
	@Operation(summary = "Update an existing role", description = "Update role's authorities or name by its current name")
	public ResponseEntity<ApiResponse> updateRole(
					@Parameter(description = "Name of the role to update", required = true)
					@PathVariable String name,
					@Parameter(description = "Updated role data", required = true)
					@RequestBody RoleRequest roleRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, RoleResponse>builder()
						.result(roleService.editRole(name, roleRequest))
						.timestamp(System.currentTimeMillis())
						.build());
	}

	@DeleteMapping("/{name}")
	@Operation(summary = "Delete a role", description = "Remove role from system by its name")
	public ResponseEntity<ApiResponse> deleteRoleByName(
					@Parameter(description = "Name of the role to delete", required = true)
					@PathVariable String name) {
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.result(roleService.removeRole(name))
						.timestamp(System.currentTimeMillis())
						.build());
	}
}
