package com.luannv.mf.services;

import com.luannv.mf.dto.request.ClientFieldsRequest;
import com.luannv.mf.dto.request.FreelancerFieldsRequest;
import com.luannv.mf.dto.request.UserPasswordUpdateRequest;
import com.luannv.mf.dto.request.UserUpdateRequest;
import com.luannv.mf.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {

	List<UserResponse> getAll();

	UserResponse getByUsername(String username);

	UserResponse updateUser(String username, @Valid UserUpdateRequest userUpdateRequest, BindingResult bindingResult);

	String deleteUserByUsername(String username);


	Void deleteAll();

	UserResponse addFieldDetailsClient(String username, ClientFieldsRequest clientFieldsRequest);

	UserResponse addFieldDetailsFreelancer(String username, FreelancerFieldsRequest freelancerFieldsRequest);

	UserResponse getMyInfo();

	UserResponse updatePasswordUser(String username, @Valid UserPasswordUpdateRequest userPasswordUpdateRequest, BindingResult bindingResult);
}
