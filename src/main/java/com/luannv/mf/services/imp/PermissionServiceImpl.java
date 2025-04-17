package com.luannv.mf.services.imp;

import com.luannv.mf.dto.request.PermissionRequest;
import com.luannv.mf.dto.response.PermissionResponse;
import com.luannv.mf.enums.PermissionEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.PermissionMapper;
import com.luannv.mf.models.Permission;
import com.luannv.mf.repositories.PermissionRepository;
import com.luannv.mf.utils.ItemUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl {
	PermissionRepository permissionRepository;
	PermissionMapper permissionMapper;

	public List<PermissionResponse> getAll() {
		return permissionRepository
						.findAll()
						.stream()
						.map(permission -> permissionMapper.toResponse(permission))
						.toList();
	}

	public PermissionResponse create(PermissionRequest permissionRequest) {
		if (!ItemUtils.isItemOfEnum(permissionRequest.getName(), PermissionEnum.class))
			throw new SingleErrorException(ErrorCode.PERMISSION_INVALID);
		if (!permissionRepository.findByName(permissionRequest.getName()).isEmpty())
			throw new SingleErrorException(ErrorCode.PERMISSION_EXISTED);
		System.out.println("PASSED");
		Permission permission = permissionMapper.toEntity(permissionRequest);
		permission = permissionRepository.save(permission);
		return permissionMapper.toResponse(permission);
	}

	public PermissionResponse editPermission(String name, PermissionRequest permissionRequest) {
		if (permissionRepository.findByName(name).isEmpty())
			throw new SingleErrorException(ErrorCode.PERMISSION_NOTFOUND);
		Permission permission = permissionRepository.findByName(name).get();
		if (!permission.getName().equals(permissionRequest.getName()) &&
						!permissionRepository.findByName(permissionRequest.getName()).isEmpty())
			throw new SingleErrorException(ErrorCode.PERMISSION_EXISTED);
		if (!ItemUtils.isItemOfEnum(permissionRequest.getName(), PermissionEnum.class))
			throw new SingleErrorException(ErrorCode.PERMISSION_INVALID);
		permission.setName(permissionRequest.getName());
		permission.setDescription(permissionRequest.getDescription());
		permissionRepository.save(permission);
		return permissionMapper.toResponse(permission);
	}


	public String deletePermission(String name) {
		if (permissionRepository.findByName(name).isEmpty())
			throw new SingleErrorException(ErrorCode.PERMISSION_NOTFOUND);
		Permission permission = permissionRepository.findByName(name).get();
		permissionRepository.delete(permission);
		return permission.getName();
	}

	public boolean deleteAllPermission() {
		permissionRepository.deleteAll();
		return true;
	}
}
