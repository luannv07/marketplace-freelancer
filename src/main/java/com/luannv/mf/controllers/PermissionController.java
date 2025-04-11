package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.PermissionRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.PermissionResponse;
import com.luannv.mf.models.Permission;
import com.luannv.mf.services.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.Response;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
	PermissionService permissionService;

	@GetMapping
	public ResponseEntity<ApiResponse> getAllPermissions() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return ResponseEntity.ok().body(ApiResponse.<Void, List<PermissionResponse>>builder()
						.timestamp(System.currentTimeMillis())
						.result(permissionService.getAll())
						.build());
	}

	@PostMapping
	public ResponseEntity<ApiResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, PermissionResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(permissionService.create(permissionRequest))
						.build());
	}

	@PutMapping("/{name}")
	public ResponseEntity<ApiResponse> updatePermission(@PathVariable String name, @RequestBody PermissionRequest permissionRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, PermissionResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(permissionService.editPermission(name, permissionRequest))
						.build());
	}

	@DeleteMapping("/{name}")
	public ResponseEntity<ApiResponse> removePermission(@PathVariable String name) {
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(permissionService.deletePermission(name))
						.build());
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse> removeAllPermission() {
		boolean isDeleted = permissionService.deleteAllPermission();
		return ResponseEntity.ok().body(ApiResponse.<Void, Boolean>builder()
						.timestamp(System.currentTimeMillis())
						.result(isDeleted)
						.build());
	}
}
