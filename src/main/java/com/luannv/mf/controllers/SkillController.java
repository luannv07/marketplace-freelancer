package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.SkillRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.services.SkillService;
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
public class SkillController {
	SkillService skillService;

	@GetMapping
	public ResponseEntity<ApiResponse> getAllSkills() {
		return ResponseEntity.ok().body(ApiResponse.<Void, Set<SkillResponse>>builder()
						.result(skillService.getAll())
						.timestamp(System.currentTimeMillis())
						.build());
	}

	@PutMapping("/{skill}/deactive")
	public ResponseEntity<ApiResponse> deactiveSkill(@PathVariable String skill) {
		skillService.deactiveAnSkill(skill);
		return ResponseEntity.ok().body(ApiResponse.<String, Void>builder()
						.timestamp(System.currentTimeMillis())
						.message("Success deactive: " + skill)
						.build());
	}
}
