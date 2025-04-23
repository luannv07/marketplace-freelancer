package com.luannv.mf.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientFieldsRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String companyName;
}
