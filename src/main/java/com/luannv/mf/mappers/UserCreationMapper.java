package com.luannv.mf.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.models.Role;
import com.luannv.mf.models.Skill;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreationMapper implements GenericMapper<User, UserCreationRequest, UserResponse> {
	PasswordEncoder passwordEncoder;
	RoleRepository roleRepository;
	RoleMapper roleMapper;
	UserResponseMapper userResponseMapper;

	@Override
	public User toEntity(UserCreationRequest userCreationRequest) {
		Role role = roleRepository.findByName(userCreationRequest.getUserType())
						.orElseThrow(() -> new SingleErrorException(ErrorCode.ROLE_NOTFOUND));
		return User.builder()
						.username(userCreationRequest.getUsername())
						.email(userCreationRequest.getEmail())
						.password(passwordEncoder.encode(userCreationRequest.getPassword()))
						.address(userCreationRequest.getAddress())
						.ratePoint(Double.valueOf(-1))
						.createAt(LocalDateTime.now())
						.updateAt(LocalDateTime.now())
						.roles(Set.of(role))
						.build();
	}

	@Override
	public UserResponse toResponse(User user) {
		return userResponseMapper.toResponse(user);
	}
}
