package com.luannv.mf.services;

import com.luannv.mf.dto.request.PermissionRequest;
import com.luannv.mf.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

	List<PermissionResponse> getAll();

	PermissionResponse create(PermissionRequest permissionRequest);

	PermissionResponse editPermission(String name, PermissionRequest permissionRequest);

	String deletePermission(String name);
}
