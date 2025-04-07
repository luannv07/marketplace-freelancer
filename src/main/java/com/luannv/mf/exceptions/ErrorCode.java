package com.luannv.mf.exceptions;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public enum ErrorCode {
	UNCATEGORIED_ERROR(9999, "Uncategorized error.", HttpStatus.BAD_REQUEST),
	PERMISSION_EXISTED(1001, "Permission existed.", HttpStatus.BAD_REQUEST),
	PERMISSION_NOTFOUND(1002, "Permission not found.", HttpStatus.NOT_FOUND),
	PERMISSION_INVALID(1003, "Permission is not valid", HttpStatus.NOT_ACCEPTABLE),
	ROLE_EXISTED(1004, "Role existed.", HttpStatus.BAD_REQUEST),
	ROLE_NOTFOUND(1005, "Role not found.", HttpStatus.NOT_FOUND),
	ROLE_INVALID(1006, "Role is not valid", HttpStatus.NOT_ACCEPTABLE),
	USER_NOTFOUND(1007, "User not found.", HttpStatus.NOT_FOUND),
	USER_EXISTED(1008, "Username has been used.", HttpStatus.NOT_ACCEPTABLE),
	EMAIL_EXISTED(1009, "Email has been used.", HttpStatus.NOT_ACCEPTABLE),
	USERNAME_NOTVALID(1010, "Username is not valid.", HttpStatus.BAD_REQUEST),
	FIELD_NOTBLANK(1011, "Field must not blank.", HttpStatus.BAD_REQUEST),
	CONFIRM_PASSWORDINVALID(1012, "Confirm password does not match the password.", HttpStatus.BAD_REQUEST),
	PASSWORD_NOTVALID(1013, "Password is not valid.", HttpStatus.BAD_REQUEST),
	EMAIL_NOTVALID(1015, "Email is not valid.", HttpStatus.BAD_REQUEST),
	USERTYPE_NOTVALID(1017, "You only chooses Freelancer or Client", HttpStatus.BAD_REQUEST);
	Integer code;
	String messages;
	HttpStatus httpStatus;
}
