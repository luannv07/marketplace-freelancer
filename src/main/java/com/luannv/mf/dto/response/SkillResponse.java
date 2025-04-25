package com.luannv.mf.dto.response;

import com.luannv.mf.models.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SkillResponse {
	String name;
	Integer isActive;
}
