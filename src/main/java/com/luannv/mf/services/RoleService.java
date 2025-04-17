package com.luannv.mf.services;

import com.luannv.mf.dto.request.RoleRequest;
import com.luannv.mf.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

	List<RoleResponse> getAll();

	RoleResponse addRole(RoleRequest roleRequest);

	RoleResponse editRole(String name, RoleRequest roleRequest);

	String removeRole(String name);
}
