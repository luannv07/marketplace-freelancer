package com.luannv.mf.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "client_profile")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String companyName = "";
	@Setter(AccessLevel.NONE)

	@OneToOne(cascade = CascadeType.ALL) // 1 client ung 1 user
	User userClientProfile;
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL) // cac project da dang
	Set<Project> postedProjects = new HashSet<>();
	@OneToMany(mappedBy = "clientProposal", cascade = CascadeType.ALL) // cac proposal da nhan
	Set<Proposal> receivedProposal = new HashSet<>();
}
