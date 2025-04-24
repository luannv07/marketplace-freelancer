package com.luannv.mf.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FreelancerUpdateRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	Set<String> skills;
}
