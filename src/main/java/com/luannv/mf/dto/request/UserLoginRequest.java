package com.luannv.mf.dto.request;

import com.luannv.mf.validators.constraints.ConfirmPasswordConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String username;
	@NotBlank(message = "FIELD_NOTBLANK")
	String password;
}
