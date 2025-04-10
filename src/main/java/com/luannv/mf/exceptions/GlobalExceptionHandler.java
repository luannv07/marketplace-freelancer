package com.luannv.mf.exceptions;

import com.fasterxml.jackson.databind.util.EnumValues;
import com.luannv.mf.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Map;

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

	@ExceptionHandler
	public ResponseEntity<ApiResponse> handleMultipleExceptions(MultipleErrorsException multipleErrorsException) {
		Map<String, String> errors = multipleErrorsException.getErrorCodes();
		return ResponseEntity.badRequest().body(ApiResponse.<Map<String, String>, Void>builder()
						.timestamp(System.currentTimeMillis())
						.message(errors)
						.build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleMethodArg(MethodArgumentNotValidException exception) {
		String defaultMessage = exception.getFieldError().getDefaultMessage();
		ErrorCode errorCode = ErrorCode.UNCATEGORIED_ERROR;
		try {
			errorCode = ErrorCode.valueOf(defaultMessage);
		} catch (Exception e) {
			System.out.println("loi");
		}
		return ResponseEntity.badRequest().body(ApiResponse.<String, Void>builder()
						.message(errorCode.getMessages())
						.timestamp(System.currentTimeMillis())
						.build());
	}
}
