package com.luannv.mf.utils;

import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.models.Role;
import com.luannv.mf.models.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class JwtUtils {

	public static Boolean isValidToken(String token, String secretKey) throws JOSEException, ParseException {
		System.out.println("Chay vao day");
		SignedJWT signedJWT = SignedJWT.parse(token);
		JWSVerifier jwsVerifier = new MACVerifier(secretKey);
		return signedJWT.verify(jwsVerifier) && new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
	}

	public static String convertRole(Set<Role> roles) {
		StringJoiner str = new StringJoiner(" ");
		Set<String> set = new HashSet<>();

		roles.forEach(role -> {
			set.add(role.getName());
			role.getPermissions().forEach(permission -> {
				set.add(permission.getName());
			});
		});
		set.forEach(item -> str.add(item));
		return str.toString();
	}

	public static String generateToken(User user, String jwtSecretKey) throws JOSEException {
		JWSSigner jwsSigner = new MACSigner(jwtSecretKey);
		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
						.jwtID(UUID.randomUUID().toString())
						.subject(user.getId())
						.expirationTime(new Date(Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()))
						.issueTime(new Date())
						.claim("scope", convertRole(user.getRoles()))
						.claim("username", user.getUsername())
						.build();
		SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
		signedJWT.sign(jwsSigner);
		return signedJWT.serialize();
	}

	public static JWTClaimsSet parseToken(String token) {
		try {
			return SignedJWT.parse(token).getJWTClaimsSet();
		} catch (ParseException e) {
			throw new SingleErrorException(ErrorCode.INVALID_TOKEN);
		}
	}
}
