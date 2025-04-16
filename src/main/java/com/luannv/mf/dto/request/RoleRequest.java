package com.luannv.mf.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String name;
	@NotBlank(message = "FIELD_NOTBLANK")
	String description;
	@NotNull(message = "FIELD_NOTBLANK")
	Set<String> permissions;
}
