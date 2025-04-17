package com.luannv.mf.services;

import com.luannv.mf.dto.request.UserUpdateRequest;
import com.luannv.mf.dto.response.UserResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {

	List<UserResponse> getAll();

	UserResponse getByUsername(String username);

	UserResponse updateUser(String username, UserUpdateRequest userUpdateRequest, BindingResult bindingResult);

	String deleteUserByUsername(String username);
}
