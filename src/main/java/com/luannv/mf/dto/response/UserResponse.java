package com.luannv.mf.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luannv.mf.models.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
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
	LocalDate createAt;
	LocalDate updateAt;
	Double rating;
	Set<RoleResponse> roles;
}
