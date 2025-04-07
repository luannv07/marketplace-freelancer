package com.luannv.mf.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
// 0 admin, 1 client, 2 freelancer
public class UserUpdateRequest {
	String oldPassword;
	String password;
	String confirmPassword;
	String address;
//	String userType;
	Set<String> roles;
}
