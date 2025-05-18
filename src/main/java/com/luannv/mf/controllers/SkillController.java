package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.SkillRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.services.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Skill Controller", description = "Skills Management")
public class SkillController {
	SkillService skillService;

	@GetMapping
	@Operation(summary = "Get all skills", description = "Retrieve a list of all available skills.")
	public ResponseEntity<ApiResponse> getAllSkills() {
		return ResponseEntity.ok().body(ApiResponse.<Void, Set<SkillResponse>>builder()
						.result(skillService.getAll())
						.timestamp(System.currentTimeMillis())
						.build());
	}

	@PutMapping("/{skill}/deactive")
	@Operation(summary = "Deactivate a skill", description = "Deactivate a specific skill by name.")
	public ResponseEntity<ApiResponse> deactiveSkill(
					@Parameter(description = "The name of the skill to deactivate", required = true)
					@PathVariable String skill) {
		skillService.deactiveAnSkill(skill);
		return ResponseEntity.ok().body(ApiResponse.<String, Void>builder()
						.timestamp(System.currentTimeMillis())
						.message("Success deactive: " + skill)
						.build());
	}
}
