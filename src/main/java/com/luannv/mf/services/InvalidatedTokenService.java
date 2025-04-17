package com.luannv.mf.services;

public interface InvalidatedTokenService {
	Boolean isInvalidatedToken(String jwtID);

	String persistInvalidatedToken(String token);
}
