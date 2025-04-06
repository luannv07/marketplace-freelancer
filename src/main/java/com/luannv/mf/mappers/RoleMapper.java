package com.luannv.mf.mappers;

import com.luannv.mf.dto.request.RoleRequest;
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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RoleMapper implements GenericMapper<Role, RoleRequest, RoleResponse> {
	PermissionRepository permissionRepository;

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
		return RoleResponse.builder()
						.name(permission.getName())
						.description(permission.getDescription())
						.permissions(permission.getPermissions())
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
