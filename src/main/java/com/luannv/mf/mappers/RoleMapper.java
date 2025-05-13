package com.luannv.mf.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.dto.request.RoleRequest;
import com.luannv.mf.dto.response.PermissionResponse;
import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.models.Permission;
import com.luannv.mf.models.Role;
import com.luannv.mf.repositories.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleMapper implements GenericMapper<Role, RoleRequest, RoleResponse> {
	PermissionRepository permissionRepository;
	PermissionMapper permissionMapper;
	@Override
	public Role toEntity(RoleRequest permissionRequest) {
		Set<Permission> set = permissionRequest.getPermissions()
						.stream()
						.map(item ->
										permissionRepository.findByName(item)
														.orElseThrow(() ->
																		new SingleErrorException(ErrorCode.PERMISSION_INVALID)))
						.collect(Collectors.toSet());
		return Role.builder()
						.name(permissionRequest.getName())
						.description(permissionRequest.getDescription())
						.permissions(set)
						.build();
	}

	@Override
	public RoleResponse toResponse(Role permission) {
		Set<PermissionResponse> permissions = permission
						.getPermissions()
						.stream()
						.map(p-> permissionMapper.toResponse(p))
						.collect(Collectors.toSet());
		return RoleResponse.builder()
						.name(permission.getName())
						.description(permission.getDescription())
						.permissions(permissions)
						.build();
	}

	public void updateEntity(Role exists, RoleRequest roleRequest) {
		exists.setName(roleRequest.getName());
		exists.setDescription(roleRequest.getDescription());
		Set<Permission> set = roleRequest.getPermissions()
						.stream()
						.map(item ->
										permissionRepository.findByName(item)
														.orElseThrow(() ->
																		new SingleErrorException(ErrorCode.PERMISSION_INVALID)))
						.collect(Collectors.toSet());
		exists.setPermissions(set);
	}
}
