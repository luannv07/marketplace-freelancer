package com.luannv.mf.services.imp;

import com.luannv.mf.dto.request.ProjectRequest;
import com.luannv.mf.dto.request.ProjectUpdateRequest;
import com.luannv.mf.dto.response.ProjectResponse;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.MultipleErrorsException;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.ProjectMapper;
import com.luannv.mf.models.Project;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.ProjectRepository;
import com.luannv.mf.repositories.RoleRepository;
import com.luannv.mf.repositories.UserRepository;
import com.luannv.mf.services.ProjectService;
import com.luannv.mf.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.luannv.mf.utils.SecurityUtils.getCurrentUsername;
import static com.luannv.mf.utils.ThrowBindingResult.baseBindingResult;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectServiceImpl implements ProjectService {
	ProjectRepository projectRepository;
	ProjectMapper projectMapper;
	UserRepository userRepository;
	RoleRepository roleRepository;

	@Override
	public ProjectResponse getProjectById(String id) {
		Project project = projectRepository
						.findById(id)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.PROJECT_NOTFOUND));
		return projectMapper.toResponse(project);
	}

	@Override
	public ProjectResponse updateProjectInfo(String id, ProjectUpdateRequest projectUpdateRequest) {
		Map<String, String> errs = new HashMap<>();
		if (projectUpdateRequest.getBudgetMax() < projectUpdateRequest.getBudgetMin())
			errs.put("budgetMin", ErrorCode.BUDGET_MIN_NOTVALID.getMessages());
		if (!errs.isEmpty())
			throw new MultipleErrorsException(errs);
		Project p = projectRepository.findById(id)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.PROJECT_NOTFOUND));
		projectMapper.update(projectUpdateRequest, p);
		p = projectRepository.save(p);
		return projectMapper.toResponse(p);
	}

	@Override
	public void deleteProject(String id) {
		Project p = projectRepository.findById(id)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.PROJECT_NOTFOUND));
		projectRepository.delete(p);
	}

	@Override
	public ProjectResponse uploadProject(ProjectRequest projectRequest, BindingResult bindingResult) throws ParseException {
		String currentUsername = SecurityUtils.getCurrentUsername();
		User currentUser = userRepository.findByUsername(currentUsername).get();
		if (currentUser.getRoles().contains(roleRepository.findByName(RoleEnum.FREELANCER.name()).get()))
			throw new SingleErrorException(ErrorCode.FORBIDDEN);
		Map<String, String> errs = baseBindingResult(bindingResult);
		if (projectRequest.getBudgetMax() < projectRequest.getBudgetMin())
			errs.put("budgetMin", ErrorCode.BUDGET_MIN_NOTVALID.getMessages());
		if (!errs.isEmpty())
			throw new MultipleErrorsException(errs);
		Project p = projectMapper.toEntity(projectRequest);
		p = projectRepository.save(p);
		return projectMapper.toResponse(p);
	}

	@Override
	public List<ProjectResponse> findAllProject() {
		return projectRepository
						.findAll()
						.stream()
						.map(project -> projectMapper.toResponse(project))
						.collect(Collectors.toList());
	}

	@Override
	public ProjectResponse claimProject(String id) {
		String currentUser = getCurrentUsername();
		if (currentUser == null)
			throw new SingleErrorException(ErrorCode.UNAUTHENTICATED);
		Project project = projectRepository
						.findById(id)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.PROJECT_NOTFOUND));
		User userToFind = userRepository.findByUsername(currentUser).get();
		project.setDeveloper(userToFind.getFreelancerProfile());
		return projectMapper.toResponse(project);
	}
}
