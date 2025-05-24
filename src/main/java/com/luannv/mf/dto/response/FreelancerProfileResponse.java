package com.luannv.mf.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class FreelancerProfileResponse {
	Set<SkillResponse> freelancerSkills = new HashSet<>();
	Set<ProjectResponse> receivedProjects = new HashSet<>();
	Set<ProposalResponse> sentProposals = new HashSet<>();
}
