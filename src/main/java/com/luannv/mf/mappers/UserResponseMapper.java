package com.luannv.mf.mappers;

import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.models.Skill;
import com.luannv.mf.models.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserResponseMapper {
	RoleMapper roleMapper;

	public UserResponse toResponse(User user) {
		Set<RoleResponse> set = user.getRoles()
						.stream()
						.map(role -> roleMapper.toResponse(role))
						.collect(Collectors.toSet());
		String company = null;
		if (user.getClientProfile() != null)
			company = user.getClientProfile().getCompanyName();
		Set<Skill> skills = null;
		if (user.getFreelancerProfile() != null)
			skills = user.getFreelancerProfile().getSkills();
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
