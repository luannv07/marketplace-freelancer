package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.ProjectRequest;
import com.luannv.mf.dto.request.ProjectUpdateRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.ProjectResponse;
import com.luannv.mf.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/projects")
@Tag(name = "Project Controller", description = "Project Management")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {
	ProjectService projectService;

	@GetMapping
	@Operation(summary = "Get all projects", description = "Retrieve all projects from the system")
	public ResponseEntity<ApiResponse> getAllProject() {
		List<ProjectResponse> projects = projectService.findAllProject();
		return ResponseEntity.ok().body(ApiResponse.<String, List<ProjectResponse>>builder()
						.timestamp(System.currentTimeMillis())
						.result(projects)
						.message("Have " + projects.size() + " projects")
						.build());
	}

	@PostMapping
	@Operation(summary = "Create a new project", description = "Create a new project and assign it to the system")
	public ResponseEntity<ApiResponse> addAnProject(
					@Parameter(description = "New project data", required = true)
					@Valid @RequestBody ProjectRequest projectRequest,
					BindingResult bindingResult) throws ParseException {
		return ResponseEntity.ok().body(ApiResponse.<Void, ProjectResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.uploadProject(projectRequest, bindingResult))
						.build());
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update project information", description = "Update an existing project's details by ID")
	public ResponseEntity<ApiResponse> updateProject(
					@Parameter(description = "ID of the project to update", required = true)
					@PathVariable String id,
					@Parameter(description = "Updated project data", required = true)
					@RequestBody ProjectUpdateRequest projectUpdateRequest) {
		System.out.println("Chay vao day chua controller");
		return ResponseEntity.ok().body(ApiResponse.<Void, ProjectResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.updateProjectInfo(id, projectUpdateRequest))
						.build());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get project by ID", description = "Retrieve a project's detail by its ID")
	public ResponseEntity<ApiResponse> getAnProject(
					@Parameter(description = "ID of the project to retrieve", required = true)
					@PathVariable String id) {
		return ResponseEntity.ok().body(ApiResponse.<Void, ProjectResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.getProjectById(id))
						.build());
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a project", description = "Remove a project from the system by its ID")
	public ResponseEntity<ApiResponse> deleteAProject(
					@Parameter(description = "ID of the project to delete", required = true)
					@PathVariable String id) {
		projectService.deleteProject(id);
		return ResponseEntity.ok().body(ApiResponse.<String, Void>builder()
						.timestamp(System.currentTimeMillis())
						.message("Success deleted: " + id)
						.build());
	}

	@PostMapping("/{id}")
	@Operation(summary = "Claim a project as a developer", description = "Mark the project as claimed by a developer by project ID")
	public ResponseEntity<ApiResponse> toDev(
					@Parameter(description = "ID of the project to claim", required = true)
					@PathVariable String id) {
		return ResponseEntity.ok().body(ApiResponse.<Void, ProjectResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(projectService.claimProject(id))
						.build());
	}
}
