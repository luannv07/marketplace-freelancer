package com.luannv.mf.utils;

import com.luannv.mf.models.Role;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.PermissionRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class JwtUtils {

	public static Boolean isValidToken(String token, String secretKey) throws JOSEException, ParseException {
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
						.jwtID(user.getId())
						.subject(user.getUsername())
						.expirationTime(new Date(Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()))
						.claim("scope", convertRole(user.getRoles()))
						.build();
		SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
		signedJWT.sign(jwsSigner);
		return signedJWT.serialize();
	}
}
