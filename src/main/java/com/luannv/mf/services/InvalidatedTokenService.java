package com.luannv.mf.services;

import com.luannv.mf.models.InvalidatedToken;
import com.luannv.mf.repositories.InvalidatedTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvalidatedTokenService {
	InvalidatedTokenRepository invalidatedTokenRepository;
	public Boolean isInvalidatedToken(String jwtID) {
		return invalidatedTokenRepository.existsById(jwtID);
	}
	public void persistInvalidatedToken(String jwtID, Instant expiryTime) {
		invalidatedTokenRepository
						.save(InvalidatedToken.builder()
										.id(jwtID)
										.expiryTime(expiryTime)
										.build());
	}
}
