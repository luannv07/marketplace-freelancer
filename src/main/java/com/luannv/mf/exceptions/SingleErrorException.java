package com.luannv.mf.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SingleErrorException extends  RuntimeException{
	ErrorCode errorCode;

	@Autowired
	public SingleErrorException(ErrorCode errorCode) {
		super(errorCode.getMessages());
		this.errorCode = errorCode;
	}
}
