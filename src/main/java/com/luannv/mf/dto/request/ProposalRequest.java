package com.luannv.mf.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalRequest {
	@NotBlank(message = "FIELD_NOTBLANK")
	String messages;
	@NotBlank(message = "FIELD_NOTBLANK")
	Integer price;
}
