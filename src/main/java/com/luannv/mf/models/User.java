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

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	String username;
	String email;
	String password;
	LocalDate createAt;
	LocalDate updateAt;
	Integer rate;
	@ManyToMany
	Set<Role> roles;
}
