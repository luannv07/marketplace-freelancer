package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.LogoutRequest;
import com.luannv.mf.dto.request.TokenRequest;
import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.request.UserLoginRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
	AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserCreationRequest userCreationRequest,
																							BindingResult bindingResult) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(authenticationService.addUser(userCreationRequest, bindingResult))
						.build());
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(authenticationService.userLogin(userLoginRequest))
						.build());
	}

	@PostMapping("/check-valid-token")
	public ResponseEntity<ApiResponse> checkValidToken(@RequestBody TokenRequest tokenRequest) throws ParseException, JOSEException {
		Boolean isValid = authenticationService.authCheckValidToken(tokenRequest);
		int statusCode = 400;
		if (isValid)
			statusCode = 200;
		return ResponseEntity.status(statusCode).body(ApiResponse.<Void, Boolean>builder()
						.timestamp(System.currentTimeMillis())
						.result(isValid)
						.build());
	}

	@GetMapping("/logout")
	public ResponseEntity<ApiResponse> logoutUser(@RequestBody LogoutRequest logoutRequest) {
		String usernameLogout = authenticationService.logout(logoutRequest);
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(usernameLogout)
						.build());
	}
}
