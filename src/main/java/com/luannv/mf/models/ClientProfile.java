package com.luannv.mf.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "client_profile")

public class ClientProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String companyName;
	@OneToOne(cascade = CascadeType.ALL)
	User userClientProfile;
}
