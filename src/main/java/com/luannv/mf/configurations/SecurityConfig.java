package com.luannv.mf.configurations;

import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.utils.ItemUtils;
import com.nimbusds.jose.Algorithm;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import javax.print.attribute.standard.JobKOctets;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {
	@Value("${jwt.jwtSecretKey}")
	private String secretKey;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/roles/**").hasAuthority("VIEW_PROFILE")
						.requestMatchers("api/permissions/**").hasRole("ADMIN")
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
		return NimbusJwtDecoder.withSecretKey(spec)
						.macAlgorithm(MacAlgorithm.HS512)
						.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
