package com.luannv.mf.mappers;

import com.luannv.mf.dto.request.PermissionRequest;
import com.luannv.mf.dto.response.PermissionResponse;
import com.luannv.mf.models.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper implements GenericMapper<Permission, PermissionRequest, PermissionResponse> {
	@Override
	public Permission toEntity(PermissionRequest permissionRequest) {
		return Permission.builder()
						.name(permissionRequest.getName())
						.description(permissionRequest.getDescription())
						.build();
	}

	@Override
	public PermissionResponse toResponse(Permission permission) {
		return PermissionResponse.builder()
						.name(permission.getName())
						.description(permission.getDescription())
						.build();
	}
}
