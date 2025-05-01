package com.luannv.mf.services;

import com.luannv.mf.dto.request.ProjectRequest;
import com.luannv.mf.dto.request.ProjectUpdateRequest;
import com.luannv.mf.dto.response.ProjectResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public interface ProjectService {

	List<ProjectResponse> findAllProject();
	ProjectResponse getProjectById(String id);
	ProjectResponse updateProjectInfo(String id, ProjectUpdateRequest projectUpdateRequest, BindingResult bindingResult);
	Void deleteProject(String id);
	ProjectResponse uploadProject(ProjectRequest projectRequest, BindingResult bindingResult);
}
