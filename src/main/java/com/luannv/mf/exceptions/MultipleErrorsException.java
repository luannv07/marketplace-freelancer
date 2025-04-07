package com.luannv.mf.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MultipleErrorsException extends RuntimeException{
	List<ErrorCode> errorCodes;

	public MultipleErrorsException(List<ErrorCode> errorCodes) {
		super();
		this.errorCodes = errorCodes;
	}
}
