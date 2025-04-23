package com.luannv.mf.mappers;

import com.luannv.mf.dto.request.SkillRequest;
import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.models.Skill;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SkillMapper implements GenericMapper<Skill, SkillRequest, SkillResponse> {
	@Override
	public Skill toEntity(SkillRequest skillRequest) {
		return Skill.builder()
						.name(skillRequest.getName())
						.build();
	}

	@Override
	public SkillResponse toResponse(Skill skill) {
		return SkillResponse.builder()
						.name(skill.getName())
						.build();
	}
}
