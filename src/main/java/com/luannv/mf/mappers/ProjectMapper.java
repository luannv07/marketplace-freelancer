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
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.UserRepository;
import com.luannv.mf.services.SkillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.luannv.mf.utils.SecurityUtils.getCurrentUsername;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectMapper implements GenericMapper<Project, ProjectRequest, ProjectResponse> {
	SkillService skillService;
	SkillMapper skillMapper;
	UserRepository userRepository;
	@Override
	public Project toEntity(ProjectRequest projectRequest) {
		String username = getCurrentUsername();
		if (username == null)
			throw new SingleErrorException(ErrorCode.UNAUTHENTICATED);
		Set<Skill> skills = skillService.resolveSkills(projectRequest.getSkills());
		User userToFind = userRepository.findByUsername(username).get();
		return Project.builder()
						.deadline(projectRequest.getDeadline())
						.createAt(LocalDateTime.now())
						.title(projectRequest.getTitle())
						.description(projectRequest.getDescription())
						.budgetMax(projectRequest.getBudgetMax())
						.budgetMin(projectRequest.getBudgetMin())
						.skills(skills)
						.client(userToFind.getClientProfile())
						.build();
	}

	@Override
	public ProjectResponse toResponse(Project project) {
		Set<SkillResponse> skillsResponse = project.getSkills()
						.stream()
						.map(skill -> skillMapper.toResponse(skill))
						.collect(Collectors.toSet());
		StringBuilder freelancerProfile = new StringBuilder("");
		if (project.getStatus() != null)
			 freelancerProfile.append(project.getDeveloper().getId());
		return ProjectResponse.builder()
						.status(project.getStatus() == null ? 0 : project.getStatus())
						.budgetMax(project.getBudgetMax())
						.budgetMin(project.getBudgetMin())
						.title(project.getTitle())
						.createAt(project.getCreateAt())
						.description(project.getDescription())
						.deadline(project.getDeadline())
						.skills(skillsResponse)
						.userId(project.getClient().getUserClientProfile().getId())
						.developerId(freelancerProfile.toString())
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
