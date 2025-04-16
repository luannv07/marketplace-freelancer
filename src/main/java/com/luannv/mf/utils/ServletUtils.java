package com.luannv.mf.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.exceptions.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletUtils {
	public static void convertJsonResponse(HttpServletResponse response, int status, String message) {
		response.setContentType("application/json");
		response.setStatus(status);
		try {
			String jsonResponse = new ObjectMapper()
							.writeValueAsString(ApiResponse.<String, Void>builder()
											.message(message)
											.timestamp(System.currentTimeMillis())
											.build());
			response.getWriter().write(jsonResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
