package com.luannv.mf.services;

import com.luannv.mf.constants.ValuedConstant;
import com.luannv.mf.dto.request.TokenRequest;
import com.luannv.mf.dto.request.UserCreationRequest;
import com.luannv.mf.dto.request.UserLoginRequest;
import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.MultipleErrorsException;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.UserCreationMapper;
import com.luannv.mf.models.InvalidatedToken;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.RoleRepository;
import com.luannv.mf.repositories.UserRepository;
import com.luannv.mf.utils.ItemUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.luannv.mf.utils.JwtUtils.generateToken;
import static com.luannv.mf.utils.JwtUtils.isValidToken;

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
			return generateToken(user, valuedConstant.jwtSecretKey);
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
	}

	public Boolean authCheckValidToken(TokenRequest tokenRequest) throws ParseException, JOSEException {
		return isValidToken(tokenRequest.getToken(), valuedConstant.jwtSecretKey);
	}

	public String logout() throws ParseException {
		return null;
	}

}
