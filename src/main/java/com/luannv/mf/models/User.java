package com.luannv.mf.models;

import com.luannv.mf.dto.response.RoleResponse;
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

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	String username;
	String email;
	String password;
	LocalDate createAt;
	LocalDate updateAt;
	String address;
	Double ratePoint;
	@ManyToMany
	Set<Role> roles;
}
