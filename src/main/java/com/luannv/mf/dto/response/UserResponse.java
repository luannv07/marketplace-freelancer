package com.luannv.mf.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.models.Skill;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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
	String companyName;
	Set<Skill> skills;
}
