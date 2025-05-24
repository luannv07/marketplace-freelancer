package com.luannv.mf.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectResponse {
	String id;
	String title;
	String description;
	Integer budgetMin;
	Integer budgetMax;
	LocalDateTime deadline;
	LocalDateTime createAt;
	Integer status;
//	Set<SkillResponse> skills;
	Set<String> skills;
	String userId;
	String developerId;
}
