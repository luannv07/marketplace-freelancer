package com.luannv.mf.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.dto.request.ClientFieldsRequest;
import com.luannv.mf.dto.response.ClientProfileResponse;
import com.luannv.mf.models.ClientProfile;
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
public class ClientProfileMapper implements GenericMapper<ClientProfile, ClientFieldsRequest, ClientProfileResponse> {
	SkillService skillService;
	SkillMapper skillMapper;
	UserRepository userRepository;
	ProjectMapper projectMapper;


	@Override
	public ClientProfile toEntity(ClientFieldsRequest clientFieldsRequest) {
		return ClientProfile.builder()
						.companyName(clientFieldsRequest.getCompanyName())
						.build();
	}

	@Override
	public ClientProfileResponse toResponse(ClientProfile clientProfile) {
		return ClientProfileResponse.builder()
						.companyName(clientProfile.getCompanyName())
						.postedProjects(
										clientProfile.getPostedProjects() != null
														? clientProfile.getPostedProjects()
															.stream()
																.map(project -> projectMapper.toResponse(project))
															.collect(Collectors.toSet())
														: new HashSet<>()
						)
						.build();
	}
}
