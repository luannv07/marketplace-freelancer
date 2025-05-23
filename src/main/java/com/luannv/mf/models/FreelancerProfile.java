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
@Table(name = "freelancer_profile")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class FreelancerProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name = "";
	@ManyToMany
	Set<Skill> skills = new HashSet<>();
	@Setter(AccessLevel.NONE)
	@OneToOne(cascade = CascadeType.ALL)
	User userFreelancerProfile;
	@OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
	Set<Project> receivedProject = new HashSet<>();
}
