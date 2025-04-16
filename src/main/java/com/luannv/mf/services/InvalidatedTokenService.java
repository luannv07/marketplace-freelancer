package com.luannv.mf.services;

import com.luannv.mf.models.InvalidatedToken;
import com.luannv.mf.repositories.InvalidatedTokenRepository;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

import static com.luannv.mf.utils.JwtUtils.parseToken;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvalidatedTokenService {
	InvalidatedTokenRepository invalidatedTokenRepository;

	public Boolean isInvalidatedToken(String jwtID) {
		return invalidatedTokenRepository.existsById(jwtID);
	}

	public String persistInvalidatedToken(String token) {
		JWTClaimsSet claims = parseToken(token);
		String jwtID = claims.getJWTID();
		Date expiryTime = claims.getExpirationTime();

		invalidatedTokenRepository.save(InvalidatedToken.builder()
						.id(jwtID)
						.expiryTime(expiryTime)
						.build());
		return claims.getSubject();
	}
}
