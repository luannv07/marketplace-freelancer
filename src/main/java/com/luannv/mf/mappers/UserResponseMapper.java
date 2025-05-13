package com.luannv.mf.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.models.Skill;
import com.luannv.mf.models.User;
import com.luannv.mf.services.SkillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseMapper {
	RoleMapper roleMapper;
	SkillMapper skillMapper;
	public UserResponse toResponse(User user) {
		Set<RoleResponse> set = user.getRoles()
						.stream()
						.map(role -> roleMapper.toResponse(role))
						.collect(Collectors.toSet());
		String company = null;
		if (user.getClientProfile() != null)
			company = user.getClientProfile().getCompanyName();
		Set<SkillResponse> skills = null;
		if (user.getFreelancerProfile() != null)
			skills = user.getFreelancerProfile()
							.getSkills()
							.stream()
							.map(skill -> skillMapper.toResponse(skill))
							.collect(Collectors.toSet());
		return UserResponse.builder()
						.roles(set)
						.rating(user.getRatePoint())
						.address(user.getAddress())
						.username(user.getUsername())
						.createAt(user.getCreateAt())
						.updateAt(user.getUpdateAt())
						.email(user.getEmail())
						.companyName(company)
						.skills(skills)
						.build();
	}
}
