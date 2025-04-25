package com.luannv.mf.dto.request;

import com.luannv.mf.validators.constraints.ConfirmPasswordConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfirmPasswordConstraint(first = "newPassword", second = "confirmPassword")
// 0 admin, 1 client, 2 freelancer
public class UserPasswordUpdateRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String oldPassword;
	@Pattern(message = "PASSWORD_NOTVALID", regexp = "^.{3,}$")
	@NotBlank(message = "FIELD_NOTBLANK")
	String newPassword;
	@NotBlank(message = "FIELD_NOTBLANK")
	String confirmPassword;
}
