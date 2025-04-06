package com.luannv.mf.exceptions;

import com.luannv.mf.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(SingleErrorException.class)
	public ResponseEntity<ApiResponse> handleSingleException(SingleErrorException singleErrorException) {
		ErrorCode errorCode = singleErrorException.getErrorCode();
		return ResponseEntity.status(errorCode.getHttpStatus().value()).body(ApiResponse.<String, Void>builder()
						.message(errorCode.getMessages())
						.timestamp(System.currentTimeMillis())
						.build());
	}
}
