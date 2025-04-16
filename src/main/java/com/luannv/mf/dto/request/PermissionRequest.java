package com.luannv.mf.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String name;
	@NotBlank(message = "FIELD_NOTBLANK")
	String description;
}
