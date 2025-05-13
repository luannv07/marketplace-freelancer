package com.luannv.mf.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.dto.request.PermissionRequest;
import com.luannv.mf.dto.response.PermissionResponse;
import com.luannv.mf.models.Permission;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
