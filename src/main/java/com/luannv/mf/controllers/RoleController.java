package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.RoleRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.models.Role;
import com.luannv.mf.services.RoleService;
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
public class RoleController {
	RoleService roleService;

	@GetMapping
	public ResponseEntity<ApiResponse> getAllRoles() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return ResponseEntity.ok().body(ApiResponse.<Void, List<RoleResponse>>builder()
						.result(roleService.getAll())
						.timestamp(System.currentTimeMillis())
						.build());
	}
	@PostMapping
	public ResponseEntity<ApiResponse> createRole(@RequestBody RoleRequest roleRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, RoleResponse>builder()
						.result(roleService.addRole(roleRequest))
						.timestamp(System.currentTimeMillis())
						.build());
	}
	@PutMapping("/{name}")
	public ResponseEntity<ApiResponse> updateRole(@PathVariable String name, @RequestBody RoleRequest roleRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, RoleResponse>builder()
						.result(roleService.editRole(name, roleRequest))
						.timestamp(System.currentTimeMillis())
						.build());
	}
	@DeleteMapping("/{name}")
	public ResponseEntity<ApiResponse> deleteRoleByName(@PathVariable String name) {
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.result(roleService.removeRole(name))
						.timestamp(System.currentTimeMillis())
						.build());
	}
}
