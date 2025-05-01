package com.luannv.mf.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.dto.request.ProjectRequest;
import com.luannv.mf.dto.request.ProjectUpdateRequest;
import com.luannv.mf.dto.response.ProjectResponse;
import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.models.Project;
import com.luannv.mf.models.Skill;
import com.luannv.mf.repositories.SkillRepository;
import com.luannv.mf.services.SkillService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectMapper implements GenericMapper<Project, ProjectRequest, ProjectResponse> {
	SkillService skillService;
	SkillMapper skillMapper;
	@Override
	public Project toEntity(ProjectRequest projectRequest) {
		Set<Skill> skills = skillService.resolveSkills(projectRequest.getSkills());
		return Project.builder()
						.deadline(projectRequest.getDeadline())
						.createAt(LocalDateTime.now())
						.title(projectRequest.getTitle())
						.description(projectRequest.getDescription())
						.budgetMax(projectRequest.getBudgetMax())
						.budgetMin(projectRequest.getBudgetMin())
						.skills(skills)
						.build();
	}

	@Override
	public ProjectResponse toResponse(Project project) {
		Set<SkillResponse> skillsResponse = project.getSkills()
						.stream()
						.map(skill -> skillMapper.toResponse(skill))
						.collect(Collectors.toSet());
		return ProjectResponse.builder()
						.status(project.getStatus())
						.budgetMax(project.getBudgetMax())
						.budgetMin(project.getBudgetMin())
						.title(project.getTitle())
						.createAt(project.getCreateAt())
						.description(project.getDescription())
						.deadline(project.getDeadline())
						.skills(skillsResponse)
						.userId(project.getClient().getId())
						.developerId(project.getDeveloper().getId())
						.build();
	}

	public void update(ProjectUpdateRequest request, Project project) {
		Set<Skill> skills = skillService.resolveSkills(request.getSkills());
		project.setTitle(request.getTitle());
		project.setDescription(request.getDescription());
		project.setBudgetMin(request.getBudgetMin());
		project.setBudgetMax(request.getBudgetMax());
		project.setDeadline(request.getDeadline());
		project.setSkills(skills);
	}
}
