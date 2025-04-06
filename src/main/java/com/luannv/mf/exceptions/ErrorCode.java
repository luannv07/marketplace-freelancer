package com.luannv.mf.exceptions;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public enum ErrorCode {
	PERMISSION_EXISTED(1001, "Permission existed.", HttpStatus.BAD_REQUEST),
	PERMISSION_NOTFOUND(1002, "Permission not found.", HttpStatus.NOT_FOUND),
	PERMISSION_INVALID(1003, "Permission is not valid", HttpStatus.NOT_ACCEPTABLE),
	ROLE_EXISTED(1004, "Role existed.", HttpStatus.BAD_REQUEST),
	ROLE_NOTFOUND(1005, "Role not found.", HttpStatus.NOT_FOUND),
	ROLE_INVALID(1006, "Role is not valid", HttpStatus.NOT_ACCEPTABLE),

	USER_NOTFOUND(1007, "User not found.", HttpStatus.NOT_FOUND),
	USER_EXISTED(1008, "Username has been used.", HttpStatus.NOT_ACCEPTABLE),
	EMAIL_EXISTED(1009, "Email has been used.", HttpStatus.NOT_ACCEPTABLE);
	Integer code;
	String messages;
	HttpStatus httpStatus;
}
