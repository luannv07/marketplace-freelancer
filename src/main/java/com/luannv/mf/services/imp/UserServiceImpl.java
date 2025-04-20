package com.luannv.mf.services.imp;

import com.luannv.mf.dto.request.UserUpdateRequest;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.MultipleErrorsException;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.UserCreationMapper;
import com.luannv.mf.models.Role;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.RoleRepository;
import com.luannv.mf.repositories.UserRepository;
import com.luannv.mf.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
	UserRepository userRepository;
	UserCreationMapper userCreationMapper;
	RoleRepository roleRepository;
	PasswordEncoder passwordEncoder;

	public List<UserResponse> getAll() {
		return userRepository.findAll().stream().map(user -> userCreationMapper.toResponse(user)).toList();
	}

	public UserResponse getByUsername(String username) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		return userCreationMapper.toResponse(user);
	}

	public UserResponse updateUser(String username, UserUpdateRequest userUpdateRequest, BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		if (bindingResult.hasErrors())
			bindingResult
							.getFieldErrors()
							.forEach(fieldError ->
											errors.put(fieldError.getField(), ErrorCode.valueOf(fieldError.getDefaultMessage())
															.getMessages()));
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));

		Set<Role> set = userUpdateRequest
						.getRoles()
						.stream()
						.map(role -> roleRepository.findByName(role)
										.orElseThrow(() -> new SingleErrorException(ErrorCode.ROLE_NOTFOUND)))
						.collect(Collectors.toSet());
		if (!passwordEncoder.matches(userUpdateRequest.getOldPassword(), user.getPassword()))
			errors.put("password", ErrorCode.PASSWORD_NOTVALID.getMessages());
		if (!errors.isEmpty())
			throw new MultipleErrorsException(errors);
		user.setRoles(set);
		user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
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
