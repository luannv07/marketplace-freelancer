package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.ProjectRequest;
import com.luannv.mf.dto.request.ProjectUpdateRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.ProjectResponse;
import com.luannv.mf.services.ProjectService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/projects")
public class ProjectController {
	ProjectService projectService;

	@GetMapping
	public ResponseEntity<ApiResponse> getAllProject() {
		return ResponseEntity.ok().body(ApiResponse.<Void, List<ProjectResponse>>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.findAllProject())
						.build());
	}

	@PostMapping
	public ResponseEntity<ApiResponse> addAnProject(@Valid @RequestBody ProjectRequest projectRequest, BindingResult bindingResult) {
		return ResponseEntity.ok().body(ApiResponse.<Void, ProjectResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.uploadProject(projectRequest, bindingResult))
						.build());
	}
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProject(@PathVariable String id, @Valid @RequestBody ProjectUpdateRequest projectUpdateRequest, BindingResult bindingResult) {
		return ResponseEntity.ok().body(ApiResponse.<Void, ProjectResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.updateProjectInfo(id, projectUpdateRequest, bindingResult))
						.build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getAnProject(@PathVariable String id) {
		return ResponseEntity.ok().body(ApiResponse.<Void, ProjectResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.getProjectById(id))
						.build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteAProject(@PathVariable String id) {
		projectService.deleteProject(id);
		return ResponseEntity.ok().body(ApiResponse.<String, Void>builder()
						.timestamp(System.currentTimeMillis())
						.message("Success deleted: " + id)
						.build());
	}
	@PostMapping("/{id}")
	public ResponseEntity<ApiResponse> toDev(@PathVariable String id) {
		return ResponseEntity.ok().body(ApiResponse.<Void, ProjectResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.claimProject(id))
						.build());
	}
}













