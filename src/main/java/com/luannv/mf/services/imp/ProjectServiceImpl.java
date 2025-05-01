package com.luannv.mf.services.imp;

import com.luannv.mf.dto.request.ProjectRequest;
import com.luannv.mf.dto.request.ProjectUpdateRequest;
import com.luannv.mf.dto.response.ProjectResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.MultipleErrorsException;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.ProjectMapper;
import com.luannv.mf.models.Project;
import com.luannv.mf.repositories.ProjectRepository;
import com.luannv.mf.services.ProjectService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

import static com.luannv.mf.utils.ThrowBindingResult.baseBindingResult;

public class ProjectServiceImpl implements ProjectService {
	ProjectRepository projectRepository;
	ProjectMapper projectMapper;

	@Override
	public ProjectResponse getProjectById(String id) {
		Project project = projectRepository
						.findById(id)
						.orElseThrow(() -> new SingleErrorException(ErrorCode.PROJECT_NOTFOUND));
		return projectMapper.toResponse(project);
	}
	
	@Override
	public ProjectResponse updateProjectInfo(String id, ProjectUpdateRequest projectUpdateRequest, BindingResult bindingResult) {
		Map<String, String> errs = baseBindingResult(bindingResult);
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
	public Void deleteProject(String id) {
		return null;
	}

	@Override
	public ProjectResponse uploadProject(ProjectRequest projectRequest, BindingResult bindingResult) {
		return null;
	}

	@Override
	public List<ProjectResponse> findAllProject() {
		return List.of();
	}
}
