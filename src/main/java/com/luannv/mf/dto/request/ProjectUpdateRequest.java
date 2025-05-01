package com.luannv.mf.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectUpdateRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String title;
	@NotBlank(message = "FIELD_NOTBLANK")
	String description;
	@NotBlank(message = "FIELD_NOTBLANK")
	Integer budgetMin;
	@NotBlank(message = "FIELD_NOTBLANK")
	Integer budgetMax;
	@NotBlank(message = "FIELD_NOTBLANK")
	LocalDateTime deadline;
	@NotBlank(message = "FIELD_NOTBLANK")
	Set<String> skills;
}
