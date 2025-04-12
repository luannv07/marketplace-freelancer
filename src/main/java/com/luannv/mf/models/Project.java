package com.luannv.mf.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "projects")

public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	String title;
	String description;
	Long budgetMin;
	Long budgetMax;
	LocalDate deadline;
	LocalDate createAt;
	Boolean isClaimed;
	@ManyToMany
	Set<Skill> skills;
	@OneToOne
	User user;
}
