package com.luannv.mf.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientProfileResponse {
	String companyName;
	Set<ProjectResponse> postedProjects = new HashSet<>();
	Set<ProposalResponse> receivedProposals = new HashSet<>();
}
