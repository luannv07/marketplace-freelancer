package com.luannv.mf.configurations;

import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.repositories.InvalidatedTokenRepository;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.luannv.mf.utils.JwtUtils.parseToken;
import static com.luannv.mf.utils.ServletUtils.convertJsonResponse;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PreventInvalidatedTokenFilter extends OncePerRequestFilter {
	InvalidatedTokenRepository invalidatedTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
																	HttpServletResponse response,
																	FilterChain filterChain) throws ServletException, IOException {
		String token = extractToken(request);
		if (token != null) {
			JWTClaimsSet claimsSet = parseToken(token);
			String jwtID = claimsSet.getJWTID();
			if (invalidatedTokenRepository.existsById(jwtID)) {
				convertJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCode.UNAUTHENTICATED.getMessages());
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String rawToken = request.getHeader("Authorization");
		if (rawToken == null || rawToken.isEmpty() || !rawToken.startsWith("Bearer ")) return null;
		return rawToken.substring(7);
	}
}
