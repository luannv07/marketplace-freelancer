package com.luannv.mf.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.dto.request.UserUpdateRequest;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateMapper implements GenericMapper<User, UserUpdateRequest, UserResponse> {
	PasswordEncoder passwordEncoder;
	RoleRepository roleRepository;
	RoleMapper roleMapper;
	UserResponseMapper userResponseMapper;

	@Override
	public User toEntity(UserUpdateRequest userUpdateRequest) {

		return User.builder()
						.address(userUpdateRequest.getAddress())
						.build();
	}

	@Override
	public UserResponse toResponse(User user) {
		return userResponseMapper.toResponse(user);
	}

}
