package com.luannv.mf.services;

import com.luannv.mf.dto.request.LogoutRequest;
import com.luannv.mf.dto.request.TokenRequest;
import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.response.UserResponse;
import com.nimbusds.jose.JOSEException;
import org.springframework.validation.BindingResult;

import java.text.ParseException;

public interface AuthenticationService {
	UserResponse addUser(UserCreationRequest userCreationRequest, BindingResult bindingResult);

	Boolean authCheckValidToken(TokenRequest tokenRequest) throws ParseException, JOSEException;

	String logout(LogoutRequest logoutRequest);
}
