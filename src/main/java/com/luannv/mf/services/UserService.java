package com.luannv.mf.services;

import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.request.UserUpdateRequest;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.UserCreationMapper;
import com.luannv.mf.models.Role;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.RoleRepository;
import com.luannv.mf.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
	UserRepository userRepository;
	UserCreationMapper userCreationMapper;
	RoleRepository roleRepository;

	public List<UserResponse> getAll() {
		return userRepository.findAll().stream().map(user -> userCreationMapper.toResponse(user)).toList();
	}

	public UserResponse getByUsername(String username) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		return userCreationMapper.toResponse(user);
	}

	public UserResponse addUser(UserCreationRequest userCreationRequest) {
		if (!userRepository.findByUsername(userCreationRequest.getUsername()).isEmpty())
			throw new SingleErrorException(ErrorCode.USER_EXISTED);
		if (!userRepository.findByEmail(userCreationRequest.getEmail()).isEmpty())
			throw new SingleErrorException(ErrorCode.EMAIL_EXISTED);
		User user = userCreationMapper.toEntity(userCreationRequest);
		userRepository.save(user);
		return userCreationMapper.toResponse(user);
	}
	public UserResponse updateUser(String username, UserUpdateRequest userUpdateRequest) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		Set<Role> set = userUpdateRequest
						.getRoles()
						.stream()
						.map(role -> roleRepository.findByName(role)
										.orElseThrow(() -> new SingleErrorException(ErrorCode.ROLE_NOTFOUND)))
						.collect(Collectors.toSet());
		user.setRoles(set);
		user.setPassword(userUpdateRequest.getPassword());
		user.setAddress(userUpdateRequest.getAddress());
		user = userRepository.save(user);
		return userCreationMapper.toResponse(user);
	}
	public String deleteUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		userRepository.delete(user);
		return username;
	}
}
