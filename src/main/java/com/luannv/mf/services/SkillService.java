package com.luannv.mf.services;

import com.luannv.mf.dto.request.SkillRequest;
import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.models.Skill;

import java.util.Set;

public interface SkillService {
	Set<SkillResponse> getAll();
	Skill saveSkill(SkillRequest skill);

	void deactiveAnSkill(String skill);
}
