package com.luannv.mf.services.imp;

import com.luannv.mf.dto.request.*;
import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.MultipleErrorsException;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.SkillMapper;
import com.luannv.mf.mappers.UserCreationMapper;
import com.luannv.mf.mappers.UserMapper;
import com.luannv.mf.mappers.UserUpdateMapper;
import com.luannv.mf.models.*;
import com.luannv.mf.repositories.*;
import com.luannv.mf.services.SkillService;
import com.luannv.mf.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
	UserRepository userRepository;
	UserCreationMapper userCreationMapper;
	RoleRepository roleRepository;
	PasswordEncoder passwordEncoder;
	UserUpdateMapper userUpdateMapper;
	UserMapper userMapper;
	ClientDetailRepository clientDetailRepository;
	FreelancerDetailRepository freelancerDetailRepository;
	SkillRepository skillRepository;
	SkillService skillService;
	SkillMapper skillMapper;

	public List<UserResponse> getAll() {
		return userRepository.findAll().stream().map(user -> userMapper.toResponse(user)).toList();
	}

	public UserResponse getByUsername(String username) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		return userMapper.toResponse(user);
	}

	public UserResponse updateUser(String username, UserUpdateRequest userUpdateRequest, BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		if (bindingResult.hasErrors())
			bindingResult
							.getFieldErrors()
							.forEach(fieldError ->
											errors.put(fieldError.getField(), ErrorCode
															.valueOf(fieldError.getDefaultMessage())
															.getMessages()));
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		Boolean containRoles = SecurityContextHolder
						.getContext()
						.getAuthentication()
						.getAuthorities()
						.stream()
						.anyMatch(grantedAuthority -> grantedAuthority
										.getAuthority()
										.equalsIgnoreCase("ROLE_" + RoleEnum.ADMIN));
		if (containRoles) {
			Set<Role> set = userUpdateRequest
							.getRoles()
							.stream()
							.map(role -> roleRepository.findByName(role)
											.orElseThrow(() -> new SingleErrorException(ErrorCode.ROLE_NOTFOUND)))
							.collect(Collectors.toSet());
			user.setRoles(set);
		}
//		if (!passwordEncoder.matches(userUpdateRequest.getOldPassword(), user.getPassword()))
//			errors.put("password", ErrorCode.PASSWORD_NOTVALID.getMessages());
		if (!errors.isEmpty())
			throw new MultipleErrorsException(errors);
//		user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
		user.setAddress(userUpdateRequest.getAddress());
		user = userRepository.save(user);
		return userUpdateMapper.toResponse(user);
	}

	@Override
	public UserResponse updatePasswordUser(String username, UserPasswordUpdateRequest userPasswordUpdateRequest, BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		if (bindingResult.hasErrors())
			bindingResult
							.getFieldErrors()
							.forEach(fieldError ->
											errors.put(fieldError.getField(), ErrorCode.valueOf(fieldError.getDefaultMessage())
															.getMessages()));
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		if (userPasswordUpdateRequest.getOldPassword() == null)
			errors.put("oldPassword", ErrorCode.FIELD_NOTBLANK.getMessages());
		if (userPasswordUpdateRequest.getNewPassword() == null)
			errors.put("newPassword", ErrorCode.FIELD_NOTBLANK.getMessages());
		if (userPasswordUpdateRequest.getConfirmPassword() == null)
			errors.put("confirmPassword", ErrorCode.FIELD_NOTBLANK.getMessages());
		if (!errors.isEmpty())
			throw new MultipleErrorsException(errors);
		if (!passwordEncoder.matches(userPasswordUpdateRequest.getOldPassword(), user.getPassword()))
			throw new SingleErrorException(ErrorCode.PASSWORD_NOTVALID);
		user.setPassword(passwordEncoder.encode(userPasswordUpdateRequest.getNewPassword()));
		user = userRepository.save(user);
		return userUpdateMapper.toResponse(user);
	}

	public String deleteUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));
		userRepository.delete(user);
		return username;
	}

	@Override
	public Void deleteAll() {
		userRepository.deleteAll();
		return null;
	}

	@Override
	public UserResponse addFieldDetailsClient(String username, ClientFieldsRequest clientFieldsRequest) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));

		if (!checkValidRole(user.getRoles(), RoleEnum.CLIENT.name()) || clientDetailRepository.existsByUserClientProfile(user))
			throw new SingleErrorException(ErrorCode.SPAM_CLIENT_DETAIL_FIELD);
		user.setClientProfile(ClientProfile.builder()
						.companyName(clientFieldsRequest.getCompanyName())
						.userClientProfile(user)
						.build());
		user = userRepository.save(user);
		return userMapper.toResponse(user);
	}

	@Override
	public UserResponse addFieldDetailsFreelancer(String username, FreelancerFieldsRequest freelancerFieldsRequest) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.USER_NOTFOUND));

		if (!checkValidRole(user.getRoles(), RoleEnum.FREELANCER.name()) || freelancerDetailRepository.existsByUserFreelancerProfile(user))
			throw new SingleErrorException(ErrorCode.SPAM_FREELANCER_DETAIL_FIELD);
		Set<Skill> skills = freelancerFieldsRequest
						.getSkills()
						.stream()
						.map(s -> {
							if (skillRepository.existsByName(s))
								return skillRepository.findByName(s).get();
							return skillService.saveSkill(SkillRequest.builder()
															.name(s)
															.build());
						})
						.collect(Collectors.toSet());
		user.setFreelancerProfile(FreelancerProfile.builder()
						.skills(skills)
						.userFreelancerProfile(user)
						.build());
		user = userRepository.save(user);
		return userMapper.toResponse(user);
	}

	@Override
	public UserResponse getMyInfo() {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(currentUser).get();
		return userMapper.toResponse(user);
	}

	public Boolean checkValidRole(Set<Role> roles, String roleName) {
		return roles
						.stream()
						.anyMatch(role -> role
										.getName().equalsIgnoreCase(roleName));
	}
}
