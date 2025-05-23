package com.luannv.mf.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.dto.request.FreelancerFieldsRequest;
import com.luannv.mf.dto.response.FreelancerProfileResponse;
import com.luannv.mf.models.FreelancerProfile;
import com.luannv.mf.models.Skill;
import com.luannv.mf.repositories.UserRepository;
import com.luannv.mf.services.SkillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FreelancerProfileMapper implements GenericMapper<FreelancerProfile, FreelancerFieldsRequest, FreelancerProfileResponse> {
	SkillService skillService;
	SkillMapper skillMapper;
	UserRepository userRepository;
	ProjectMapper projectMapper;

	@Override
	public FreelancerProfile toEntity(FreelancerFieldsRequest freelancerFieldsRequest) {
		return FreelancerProfile.builder()
						.skills(freelancerFieldsRequest.getSkills().stream().map(skillStr -> Skill.builder()
														.name(skillStr)
														.isActive(1)
														.build())
										.collect(Collectors.toSet()))
						.build();
	}

	@Override
	public FreelancerProfileResponse toResponse(FreelancerProfile freelancerProfile) {
		return FreelancerProfileResponse.builder()
						.freelancerSkills(freelancerProfile.getSkills() != null
										? freelancerProfile.getSkills()
												.stream()
												.map(skill -> skillMapper.toResponse(skill))
											.collect(Collectors.toSet())
										: new HashSet<>()
										)
						.build();
	}
}
