package com.luannv.mf.dto.response;

import com.luannv.mf.models.Skill;
import com.luannv.mf.models.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {
	String title;
	String description;
	Integer budgetMin;
	Integer budgetMax;
	LocalDateTime deadline;
	LocalDateTime createAt;
	Integer status;
	Set<SkillResponse> skills;
	String userId;
	String developerId;
}
