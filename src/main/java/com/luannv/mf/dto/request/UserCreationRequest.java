package com.luannv.mf.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
// 0 admin, 1 client, 2 freelancer
public class UserCreationRequest {
	String username;
	String password;
	String confirmPassword;
	String email;
	String address;
	String userType;
}
