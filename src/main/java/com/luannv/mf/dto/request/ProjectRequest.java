package com.luannv.mf.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luannv.mf.models.Skill;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String title;
	@NotBlank(message = "FIELD_NOTBLANK")
	String description;
	@NotNull(message = "FIELD_NOTBLANK")
	Integer budgetMin;
	@NotNull(message = "FIELD_NOTBLANK")
	Integer budgetMax;
	@NotNull(message = "FIELD_NOTBLANK")
	LocalDateTime deadline;
	@NotNull(message = "FIELD_NOTBLANK")
	Set<String> skills;
	Integer status = 0;
}
