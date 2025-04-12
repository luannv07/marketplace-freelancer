package com.luannv.mf.configurations;

import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.services.InvalidatedTokenService;
import com.luannv.mf.utils.ItemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {
	@Value("${jwt.jwtSecretKey}")
	private String secretKey;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, InvalidatedTokenService invalidatedTokenService) throws Exception {
		http.authorizeHttpRequests(auth -> auth
//						.requestMatchers("/api/roles/**").hasAuthority("VIEW_PROFILE")
//						.requestMatchers("api/permissions/**").hasRole("ADMIN")
						.anyRequest().permitAll());
		http.csrf(c -> c.disable());
		http.oauth2ResourceServer(oauth -> oauth
						.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
										.jwtAuthenticationConverter(jwtAuthenticationConverter())
						));
		return http.build();
	}

	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			String[] scopeList = jwt.getClaimAsString("scope").split(" ");
			if (scopeList == null || scopeList.length == 0) return Collections.emptyList();
			return Arrays.stream(scopeList)
							.map(item ->
											!ItemUtils.isItemOfEnum(item, RoleEnum.class)
															? new SimpleGrantedAuthority(item)
															: new SimpleGrantedAuthority("ROLE_" + item))
							.collect(Collectors.toSet());
		});
		return jwtAuthenticationConverter;
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec spec = new SecretKeySpec(secretKey.getBytes(), MacAlgorithm.HS512.getName());
		NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(spec)
						.macAlgorithm(MacAlgorithm.HS512)
						.build();
		return nimbusJwtDecoder;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
