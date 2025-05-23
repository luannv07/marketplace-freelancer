package com.luannv.mf.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
	String username;
	String email;
	String address;
	LocalDateTime createAt;
	LocalDateTime updateAt;
	Double rating;
	Set<RoleResponse> roles;
	ClientProfileResponse clientProfile;
	FreelancerProfileResponse freelancerProfile;
	Set<ProjectResponse> projects;
}
