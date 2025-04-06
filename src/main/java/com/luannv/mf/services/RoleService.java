package com.luannv.mf.services;

import com.luannv.mf.dto.request.RoleRequest;
import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.enums.PermissionEnum;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.RoleMapper;
import com.luannv.mf.models.Role;
import com.luannv.mf.repositories.RoleRepository;
import com.luannv.mf.utils.ItemUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
	RoleRepository roleRepository;
	RoleMapper roleMapper;

	public List<RoleResponse> getAll() {
		return roleRepository.findAll()
						.stream()
						.map(role -> roleMapper.toResponse(role))
						.toList();
	}
	public RoleResponse addRole(RoleRequest roleRequest) {
		if (roleRepository.existsByName(roleRequest.getName()))
			throw new SingleErrorException(ErrorCode.ROLE_EXISTED);
		roleRequest.getPermissions().forEach(item -> {
			if (!ItemUtils.isItemOfEnum(item, PermissionEnum.class))
				throw new SingleErrorException(ErrorCode.PERMISSION_INVALID);
		});
		Role role = roleMapper.toEntity(roleRequest);
		role = roleRepository.save(role);
		return roleMapper.toResponse(role);
	}

	public RoleResponse editRole(String name, RoleRequest roleRequest) {
		Role role = roleRepository.findByName(name)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.ROLE_NOTFOUND));
		roleRequest.getPermissions().forEach(item -> {
			if (!ItemUtils.isItemOfEnum(item, PermissionEnum.class))
				throw new SingleErrorException(ErrorCode.PERMISSION_INVALID);
		});
		if (!role.getName().equals(roleRequest.getName()) && !roleRequest.getName().isEmpty())
			throw new SingleErrorException(ErrorCode.ROLE_EXISTED);
		roleMapper.updateEntity(role, roleRequest);
		role = roleRepository.save(role);
		return roleMapper.toResponse(role);
	}

	public String removeRole(String name) {
		Role role = roleRepository.findByName(name)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.ROLE_NOTFOUND));
		roleRepository.delete(role);
		return name;
	}
}
