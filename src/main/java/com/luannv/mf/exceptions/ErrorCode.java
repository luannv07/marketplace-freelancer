package com.luannv.mf.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public enum ErrorCode {
	UNCATEGORIED_ERROR(9999, "Uncategorized error.", HttpStatus.INTERNAL_SERVER_ERROR),
	PERMISSION_EXISTED(1001, "Permission already exists.", HttpStatus.BAD_REQUEST),
	PERMISSION_NOTFOUND(1002, "Permission not found.", HttpStatus.NOT_FOUND),
	PERMISSION_INVALID(1003, "Permission is not valid.", HttpStatus.BAD_REQUEST),
	ROLE_EXISTED(1004, "Role already exists.", HttpStatus.BAD_REQUEST),
	ROLE_NOTFOUND(1005, "Role not found.", HttpStatus.NOT_FOUND),
	ROLE_INVALID(1006, "Role is not valid.", HttpStatus.BAD_REQUEST),
	USER_NOTFOUND(1007, "User not found.", HttpStatus.NOT_FOUND),
	USER_EXISTED(1008, "Username is already in use.", HttpStatus.BAD_REQUEST),
	EMAIL_EXISTED(1009, "Email is already in use.", HttpStatus.BAD_REQUEST),
	USERNAME_NOTVALID(1010, "Username is not valid.", HttpStatus.BAD_REQUEST),
	FIELD_NOTBLANK(1011, "Field must not be blank.", HttpStatus.BAD_REQUEST),
	CONFIRM_PASSWORDINVALID(1012, "Confirm password does not match the password.", HttpStatus.BAD_REQUEST),
	PASSWORD_NOTVALID(1013, "Password is not valid.", HttpStatus.BAD_REQUEST),
	EMAIL_NOTVALID(1015, "Email is not valid.", HttpStatus.BAD_REQUEST),
	USERTYPE_NOTVALID(1017, "You can only choose Freelancer or Client.", HttpStatus.BAD_REQUEST),
	LOGIN_FAILED(1018, "Username or password is not valid.", HttpStatus.UNAUTHORIZED),
	BODY_REQUIRED(1019, "Form body cannot be null.", HttpStatus.BAD_REQUEST),
	INVALID_TOKEN(1020, "Invalid token.", HttpStatus.UNAUTHORIZED),
	UNAUTHENTICATED(1021, "Please login to access this resource.", HttpStatus.UNAUTHORIZED),
	FORBIDDEN(1022, "You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
	INVALID_VALUE(1023, "Invalid value.", HttpStatus.BAD_REQUEST),
	SPAM_CLIENT_DETAIL_FIELD(1024, "You cannot set this information again.", HttpStatus.BAD_REQUEST),
	SPAM_FREELANCER_DETAIL_FIELD(1025, "You cannot set this information again.", HttpStatus.BAD_REQUEST),
	SKILL_EXISTED(1026, "Skill already exists.", HttpStatus.BAD_REQUEST),
	SKILL_INVALID(1027, "Skill is invalid.", HttpStatus.BAD_REQUEST),
	SKILL_NOTFOUND(1028, "Skill name not found.", HttpStatus.NOT_FOUND);

	Integer code;
	String messages;
	HttpStatus httpStatus;
}
