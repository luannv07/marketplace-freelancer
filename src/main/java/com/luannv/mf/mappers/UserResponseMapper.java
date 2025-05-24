package com.luannv.mf.mappers;

import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.models.ClientProfile;
import com.luannv.mf.models.FreelancerProfile;
import com.luannv.mf.models.Role;
import com.luannv.mf.models.User;
import com.luannv.mf.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseMapper {
	RoleMapper roleMapper;
	SkillMapper skillMapper;
	ClientProfileMapper clientProfileMapper;
	FreelancerProfileMapper freelancerProfileMapper;

	public UserResponse toResponse(User user) {
//		Set<RoleResponse> set = user.getRoles()
//						.stream()
//						.map(role -> roleMapper.toResponse(role))
//						.collect(Collectors.toSet());
		Set<String> set = user.getRoles()
						.stream()
						.map(role -> roleMapper.toResponse(role).getName())
						.collect(Collectors.toSet());
		ClientProfile clientProfileOutput = user.getClientProfile();
		FreelancerProfile freelancerProfileOutput = user.getFreelancerProfile();

		UserResponse.UserResponseBuilder response = UserResponse.builder()
						.roles(set)
						.rating(user.getRatePoint())
						.address(user.getAddress())
						.username(user.getUsername())
						.createAt(user.getCreateAt())
						.updateAt(user.getUpdateAt())
						.email(user.getEmail());

		if (clientProfileOutput != null) {
			response.clientProfile(clientProfileMapper.toResponse(clientProfileOutput));
		} else if (set.contains(RoleEnum.CLIENT.name())) {
			response.clientProfile(clientProfileMapper.toResponse(new ClientProfile()));
		}
		if (freelancerProfileOutput != null) {
			response.freelancerProfile(freelancerProfileMapper.toResponse(freelancerProfileOutput));
		} else if (set.contains(RoleEnum.FREELANCER.name())) {
			response.freelancerProfile(freelancerProfileMapper.toResponse(new FreelancerProfile()));
		}

		return response.build();
	}
}
