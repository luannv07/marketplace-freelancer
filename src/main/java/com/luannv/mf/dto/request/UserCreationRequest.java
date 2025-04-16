package com.luannv.mf.dto.request;

import com.luannv.mf.validators.constraints.ConfirmPasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

// 0 admin, 1 client, 2 freelancer
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfirmPasswordConstraint(first = "password", second = "confirmPassword")
public class UserCreationRequest {
	@Pattern(message = "USERNAME_NOTVALID", regexp = "^[a-z][a-z0-9]{4,14}$")
	@NotBlank(message = "FIELD_NOTBLANK")
	String username;
	@Pattern(message = "PASSWORD_NOTVALID", regexp = "^.{3,}$")
	@NotBlank(message = "FIELD_NOTBLANK")
	String password;
	String confirmPassword;
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "EMAIL_NOTVALID")
	@NotBlank(message = "FIELD_NOTBLANK")
	String email;
	@NotBlank(message = "FIELD_NOTBLANK")
	String address;
	@NotBlank(message = "FIELD_NOTBLANK")
	String userType;
}
