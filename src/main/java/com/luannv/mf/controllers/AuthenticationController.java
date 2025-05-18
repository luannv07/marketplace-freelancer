package com.luannv.mf.controllers;

import com.luannv.mf.dto.request.LogoutRequest;
import com.luannv.mf.dto.request.TokenRequest;
import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.request.UserLoginRequest;
import com.luannv.mf.dto.response.ApiResponse;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Controller", description = "Manage registration, login, logout, and token validation")
public class AuthenticationController {
	AuthenticationService authenticationService;

	@PostMapping("/register")
	@Operation(summary = "Register a new user", description = "Create a new user account with the given information")
	public ResponseEntity<ApiResponse> register(
					@Parameter(description = "User registration data", required = true)
					@Valid @RequestBody UserCreationRequest userCreationRequest,
					BindingResult bindingResult) {
		return ResponseEntity.ok().body(ApiResponse.<Void, UserResponse>builder()
						.timestamp(System.currentTimeMillis())
						.result(authenticationService.addUser(userCreationRequest, bindingResult))
						.build());
	}

	@PostMapping("/login")
	@Operation(summary = "User login", description = "Authenticate user and return JWT token if successful")
	public ResponseEntity<ApiResponse> login(
					@Parameter(description = "User login credentials", required = true)
					@RequestBody UserLoginRequest userLoginRequest) {
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(authenticationService.userLogin(userLoginRequest))
						.build());
	}

	@PostMapping("/check-valid-token")
	@Operation(summary = "Check token validity", description = "Verify whether a given JWT token is valid")
	public ResponseEntity<ApiResponse> checkValidToken(
					@Parameter(description = "JWT token data to validate", required = true)
					@RequestBody TokenRequest tokenRequest) throws ParseException, JOSEException {
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
	@Operation(summary = "User logout", description = "Invalidate the token and log out the user")
	public ResponseEntity<ApiResponse> logoutUser(
					@Parameter(description = "Token information for logout", required = true)
					@RequestBody LogoutRequest logoutRequest) {
		String usernameLogout = authenticationService.logout(logoutRequest);
		return ResponseEntity.ok().body(ApiResponse.<Void, String>builder()
						.timestamp(System.currentTimeMillis())
						.result(usernameLogout)
						.build());
	}
}
