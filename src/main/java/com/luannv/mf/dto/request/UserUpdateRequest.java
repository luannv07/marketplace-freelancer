package com.luannv.mf.dto.request;

import com.luannv.mf.validators.constraints.ConfirmPasswordConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfirmPasswordConstraint(first = "password", second = "confirmPassword")
// 0 admin, 1 client, 2 freelancer
public class UserUpdateRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String address;
	@NotNull(message = "FIELD_NOTBLANK")
	Set<String> roles;
}
