package com.luannv.mf.configurations;

import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.repositories.InvalidatedTokenRepository;
import com.luannv.mf.services.InvalidatedTokenService;
import com.luannv.mf.utils.ItemUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.luannv.mf.utils.ServletUtils.convertJsonResponse;

@Configuration
public class SecurityConfig {
	@Value("${jwt.jwtSecretKey}")
	private String secretKey;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
																								 InvalidatedTokenService invalidatedTokenService,
																								 InvalidatedTokenRepository invalidatedTokenRepository) throws Exception {
		http.authorizeHttpRequests(auth -> auth
						.requestMatchers(
										"/v3/api-docs/**",
										"/swagger-ui/**",
										"/swagger-ui.html",
										"/swagger-resources/**",
										"/configuration/**",
										"/webjars/**",
										"/api/users/test",
										"/api/auth/**",
										"/"
						).permitAll()
						.requestMatchers("/api/permissions/**", "/api/roles/**").hasRole(RoleEnum.ADMIN.name())
						.anyRequest().authenticated());
		http.csrf(c -> c.disable());
		http.cors(Customizer.withDefaults());
		http.addFilterBefore(new PreventInvalidatedTokenFilter(invalidatedTokenRepository),
						UsernamePasswordAuthenticationFilter.class);

		http.oauth2ResourceServer(oauth -> oauth
						.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
										.jwtAuthenticationConverter(jwtAuthenticationConverter()))
						.authenticationEntryPoint((request, response, authException) -> convertJsonResponse(response,
										HttpServletResponse.SC_UNAUTHORIZED, ErrorCode.UNAUTHENTICATED.getMessages()))
						.accessDeniedHandler((request, response, accessDeniedException) -> convertJsonResponse(response,
										HttpServletResponse.SC_FORBIDDEN, ErrorCode.FORBIDDEN.getMessages())));
		http
						.exceptionHandling(exp -> exp
										.authenticationEntryPoint((request, response, authException) -> convertJsonResponse(response,
														HttpServletResponse.SC_UNAUTHORIZED, ErrorCode.UNAUTHENTICATED.getMessages()))
										.accessDeniedHandler((request, response, accessDeniedException) -> convertJsonResponse(response,
														HttpServletResponse.SC_FORBIDDEN, ErrorCode.FORBIDDEN.getMessages())));
		return http.build();
	}

	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setPrincipalClaimName("username");
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			String[] scopeList = jwt.getClaimAsString("scope").split(" ");
			if (scopeList == null || scopeList.length == 0)
				return Collections.emptyList();
			return Arrays.stream(scopeList)
							.map(item -> !ItemUtils.isItemOfEnum(item, RoleEnum.class)
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

	private SecurityScheme securityScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
						.bearerFormat("JWT")
						.scheme("bearer");
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
						.info(new Info()
										.title("Marketplace Freelancer API")
										.version("1.0")
										.description("API with JWT Bearer Authentication - LuanNV"))
						.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
						.components(new Components()
										.addSecuritySchemes("Bearer Authentication", new SecurityScheme()
														.name("Authorization")
														.type(SecurityScheme.Type.HTTP)
														.scheme("bearer")
														.bearerFormat("JWT")));
	}

}
