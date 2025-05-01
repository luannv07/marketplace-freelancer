package com.luannv.mf.utils;

import com.luannv.mf.exceptions.ErrorCode;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ThrowBindingResult {
	public static Map<String, String> baseBindingResult(BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		if (bindingResult.hasErrors())
			bindingResult
							.getFieldErrors()
							.forEach(fieldError ->
											errors.put(fieldError.getField(), ErrorCode
															.valueOf(fieldError.getDefaultMessage())
															.getMessages()));
		return errors;
	}
}
