package com.luannv.mf.services;

import com.luannv.mf.constants.ValuedConstant;
import com.luannv.mf.dto.request.LogoutRequest;
import com.luannv.mf.dto.request.TokenRequest;
import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.request.UserLoginRequest;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.MultipleErrorsException;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.UserCreationMapper;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.RoleRepository;
import com.luannv.mf.repositories.UserRepository;
import com.luannv.mf.utils.ItemUtils;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.luannv.mf.utils.JwtUtils.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
	UserRepository userRepository;
	UserCreationMapper userCreationMapper;
	RoleRepository roleRepository;
	PasswordEncoder passwordEncoder;
	ValuedConstant valuedConstant;
	InvalidatedTokenService invalidatedTokenService;

	public UserResponse addUser(UserCreationRequest userCreationRequest, BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		if (bindingResult.hasErrors())
			bindingResult
							.getFieldErrors()
							.forEach(fieldError ->
											errors.put(fieldError.getField(), ErrorCode.valueOf(fieldError.getDefaultMessage())
															.getMessages()));
		if (!userRepository.findByUsername(userCreationRequest.getUsername()).isEmpty())
			errors.put("username", ErrorCode.USER_EXISTED.getMessages());
		if (!userRepository.findByEmail(userCreationRequest.getEmail()).isEmpty())
			errors.put("email", ErrorCode.EMAIL_EXISTED.getMessages());
		boolean isValidEnum = ItemUtils.isItemOfEnum(userCreationRequest.getUserType(), RoleEnum.class);
		if (!isValidEnum)
			errors.put("userType", ErrorCode.USERTYPE_NOTVALID.getMessages());
		if (!errors.isEmpty())
			throw new MultipleErrorsException(errors);
		User user = userCreationMapper.toEntity(userCreationRequest);
		userRepository.save(user);
		return userCreationMapper.toResponse(user);
	}

	public String userLogin(UserLoginRequest userLoginRequest) {
		if (userLoginRequest.getUsername() == null || userLoginRequest.getPassword() == null)
			throw new SingleErrorException(ErrorCode.LOGIN_FAILED);
		User user = userRepository.findByUsername(userLoginRequest.getUsername())
						.orElseThrow(() -> new SingleErrorException(ErrorCode.LOGIN_FAILED));
		if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
			throw new SingleErrorException(ErrorCode.LOGIN_FAILED);
		try {
			return generateToken(user, valuedConstant.JWT_SECRET_KEY);
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
	}

	public Boolean authCheckValidToken(TokenRequest tokenRequest) throws ParseException, JOSEException {
		if (invalidatedTokenService.isInvalidatedToken(parseToken(tokenRequest.getToken()).getJWTID()))
			return false;
		return isValidToken(tokenRequest.getToken(), valuedConstant.JWT_SECRET_KEY);
	}

	public String logout(LogoutRequest logoutRequest) {
		String token = logoutRequest.getToken();
		String username = invalidatedTokenService.persistInvalidatedToken(token);
		return username;
	}
}
