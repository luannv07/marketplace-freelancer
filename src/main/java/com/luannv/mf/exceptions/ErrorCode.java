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
	PERMISSION_INVALID(1003, "Permission is not valid", HttpStatus.NOT_ACCEPTABLE)

	;
	Integer code;
	String messages;
	HttpStatus httpStatus;
}
