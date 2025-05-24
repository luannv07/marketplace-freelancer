package com.luannv.mf.models;

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
@Table(name = "users")
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	String username;
	String email;
	String password;
	LocalDateTime createAt;
	LocalDateTime updateAt;
	String address;
	Double ratePoint;
	@ManyToMany
	Set<Role> roles;
	@OneToOne(mappedBy = "userClientProfile", cascade = CascadeType.ALL)
	ClientProfile clientProfile;
	@OneToOne(mappedBy = "userFreelancerProfile", cascade = CascadeType.ALL)
	FreelancerProfile freelancerProfile;
}
