package com.luannv.mf.models;

import com.luannv.mf.dto.response.SkillResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	@Column(columnDefinition = "LONGTEXT")
	String description;
	Integer budgetMin;
	Integer budgetMax;
	LocalDateTime deadline;
	LocalDateTime createAt;
	@Column(columnDefinition = "TINYINT(1) DEFAULT 0")
	Integer status = 0;
	@ManyToMany
	Set<Skill> skills;
	@ManyToOne
	User client;
	@ManyToOne
	@Column(nullable = true)
	User developer = null;
}
