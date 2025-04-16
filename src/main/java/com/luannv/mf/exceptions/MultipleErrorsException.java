package com.luannv.mf.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MultipleErrorsException extends RuntimeException {
	Map<String, String> errorCodes;

	public MultipleErrorsException(Map<String, String> errorCodes) {
		super();
		this.errorCodes = errorCodes;
	}
}
