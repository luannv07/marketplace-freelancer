package com.luannv.mf.services.imp;

import com.luannv.mf.dto.request.SkillRequest;
import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.enums.SkillEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.SkillMapper;
import com.luannv.mf.models.Skill;
import com.luannv.mf.repositories.SkillRepository;
import com.luannv.mf.services.SkillService;
import com.luannv.mf.utils.ItemUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SkillServiceImpl implements SkillService {
	SkillRepository skillRepository;
	SkillMapper skillMapper;
	@Override
	public Set<SkillResponse> getAll() {
		try {
			return skillRepository.findAll()
							.stream()
							.map(skill -> skillMapper.toResponse(skill))
							.collect(Collectors.toSet());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Skill saveSkill(SkillRequest skill) {
		if (skillRepository.existsByName(skill.getName()))
			throw new SingleErrorException(ErrorCode.SKILL_EXISTED);
		if (!ItemUtils.isItemOfEnum(skill.getName(), SkillEnum.class))
			throw new SingleErrorException(ErrorCode.SKILL_INVALID);
		return skillRepository.save(skillMapper.toEntity(skill));
	}

	@Override
	public void deactiveAnSkill(String skill) {
		Skill skillEntity = skillRepository
						.findByName(skill).orElseThrow(() -> new SingleErrorException(ErrorCode.SKILL_NOTFOUND));
		skillEntity.setIsActive(0);
		skillRepository.save(skillEntity);
	}
	public Set<Skill> resolveSkills(Set<String> skills) {
		System.out.println(">>>>>>>> TEST");
		if (skills == null || skills.isEmpty())
			return new HashSet<>();
		return skills.stream()
						.map(s -> skillRepository.findByName(s)
										.orElseThrow(() -> new SingleErrorException(ErrorCode.SKILL_NOTFOUND)))
						.collect(Collectors.toSet());
	}
}
