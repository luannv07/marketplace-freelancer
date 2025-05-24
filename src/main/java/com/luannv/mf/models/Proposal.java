package com.luannv.mf.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposals")

public class Proposal {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	@Column(columnDefinition = "LONGTEXT")
	String messages;
	Integer price;
	LocalDateTime createAt = LocalDateTime.now();
	@Column(columnDefinition = "TINYINT(1) DEFAULT 0")
	Integer status = 0;

	@ManyToOne
	@JoinColumn(name = "project_id")
	Project projectProposal;

	@ManyToOne
	@JoinColumn(name = "client_id")
	ClientProfile clientProposal;

	@ManyToOne
	@JoinColumn(name = "freelancer_id")
	FreelancerProfile freelancerProposal;

}
