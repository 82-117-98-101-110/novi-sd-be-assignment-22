package com.ravel.backend.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ravel.backend.security.jwt.JwtAccessDeniedHandler;
import com.ravel.backend.security.jwt.JwtAuthenticationEntryPoint;
import com.ravel.backend.security.jwt.JwtAuthorizationFilter;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultiHttpSecurityConfig {

	@Configuration
	@Order(1)
	public static class PhotonWebHookSecurityConfig extends WebSecurityConfigurerAdapter {

		@Value("API-KEY")
		private String principalRequestHeader;

		@Value("key")
		private String principalRequestValue;

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
			filter.setAuthenticationManager(new AuthenticationManager() {

				@Override
				public Authentication authenticate(Authentication authentication) throws AuthenticationException {
					String principal = (String) authentication.getPrincipal();
					if (!principalRequestValue.equals(principal))
					{
						throw new JWTVerificationException("Invalid API Key");
					}
					authentication.setAuthenticated(true);
					return authentication;
				}
			});
			httpSecurity.
					antMatcher("/webhook/photon/**").
					csrf().disable().
					sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
					and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
		}
	}

	@Configuration
	@Order(2)
	public static class GeneralWebHookSecurityConfig extends WebSecurityConfigurerAdapter {

		@Value("X-API-KEY")
		private String principalRequestHeader;

		@Value("key")
		private String principalRequestValue;

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
			filter.setAuthenticationManager(new AuthenticationManager() {

				@Override
				public Authentication authenticate(Authentication authentication) throws AuthenticationException {
					String principal = (String) authentication.getPrincipal();
					if (!principalRequestValue.equals(principal))
					{
						throw new JWTVerificationException("Invalid API Key");
					}
					authentication.setAuthenticated(true);
					return authentication;
				}
			});
			httpSecurity.
					antMatcher("/webhook/general/**").
					csrf().disable().
					sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
					and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
		}
	}



	@Configuration
	public class SecurityConfig extends WebSecurityConfigurerAdapter {

		private JwtAuthorizationFilter jwtAuthorizationFilter;
		private JwtAccessDeniedHandler jwtAccessDeniedHandler;
		private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		private UserDetailsService userDetailsService;
		private BCryptPasswordEncoder bCryptPasswordEncoder;

		//TODO security: validate issuer jwt token
		@Value("${security.constant.jwt.issuer}")
		private String issuer;

		//TODO security: validate audience jwt token
		@Value("${security.constant.jwt.audience}")
		private String audience;

		@Autowired
		public SecurityConfig(
				JwtAuthorizationFilter jwtAuthorizationFilter,
				JwtAccessDeniedHandler jwtAccessDeniedHandler,
				JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
				@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
				BCryptPasswordEncoder bCryptPasswordEncoder
		) {
			this.jwtAuthorizationFilter = jwtAuthorizationFilter;
			this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
			this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
			this.userDetailsService = userDetailsService;
			this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
					.userDetailsService(userDetailsService)
					.passwordEncoder(bCryptPasswordEncoder);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
					.csrf()
					.disable()
					.cors()
					.and()
					.exceptionHandling()
					.accessDeniedHandler(jwtAccessDeniedHandler)
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.and()
					.sessionManagement()
					.sessionCreationPolicy(STATELESS)
					.and()
					.authorizeRequests()
					.mvcMatchers(HttpMethod.OPTIONS)
					.permitAll()
					.mvcMatchers(
							"/api/v1/module/**",
							//                        "/**",
							"/actuator/*",
							"/actuator",
							"/v1/auth/login",
							"/v1/auth/passwordless/request",
							"/v1/auth/passwordless/authenticate",
							"/api/v1/auth/test/public",
							"/api/v1/users/signup",
							"/api/v1/users/resetpassword/**",
							"/api/v2/users/resetPasswordRequest",
							"/api/v1/users/invites/signup",
							// -- Swagger UI v2
							"/v2/api-docs",
							"/swagger-resources",
							"/swagger-resources/**",
							"/configuration/ui",
							"/configuration/security",
							"/swagger-ui.html",
							"/webjars/**",
							// -- Swagger UI v3 (OpenAPI)
							"/v3/api-docs/**",
							"/swagger-ui/**"
							,
							"/swagger-config.json"
					)
					.permitAll()
					.mvcMatchers("/api/v1/auth/test/private", "/v1/auth/passwordless/verify")
					.authenticated()
					.mvcMatchers("/api/v1/auth/test/private-scoped")
					.hasAuthority("admin:read")
					.anyRequest()
					.authenticated();
			http.addFilterBefore(
					jwtAuthorizationFilter,
					UsernamePasswordAuthenticationFilter.class
			);
		}

		@Bean
		ForwardedHeaderFilter forwardedHeaderFilter() {
			return new ForwardedHeaderFilter();
		}

		@Bean
		public CorsFilter corsFilter() {
			UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			corsConfiguration.setAllowCredentials(true);
			corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
//		corsConfiguration.setAllowedOriginPatterns(
//			List.of(
//				"http://localhost:8080/swagger-ui.html",
//				"http://localhost:3000",
//				"http://localhost:3001",
//				"https://dashboard.ravel.systems/",
//				"http://localhost:8080",
//				"https://dev.ravel.systems",
//				"https://test.ravel.systems",
//				"https://live.ravel.systems",
//				"https://app.ravel.world",
//				"https://test.ravel.world",
//				"https://dev.ravel.world",
//						"https://ravel-frontend-01.vercel.app/"
//			)
//		);
			corsConfiguration.setAllowedHeaders(
					Arrays.asList(
							"Origin",
							"Access-Control-Allow-Origin",
							"Content-Type",
							"Accept",
							"Jwt-Token",
							"Authorization",
							"Origin, Accept",
							"X-Requested-With",
							"Access-Control-Request-Method",
							"Access-Control-Request-Headers",
							"*"
					)
			);
			corsConfiguration.setExposedHeaders(
					Arrays.asList(
							"Origin",
							"Content-Type",
							"Accept",
							"Jwt-Token",
							"Authorization",
							"Access-Control-Allow-Origin",
							"Access-Control-Allow-Origin",
							"Access-Control-Allow-Credentials",
							"*"
					)
			);
			corsConfiguration.setAllowedMethods(
					Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
			);
			urlBasedCorsConfigurationSource.registerCorsConfiguration(
					"/**",
					corsConfiguration
			);
			return new CorsFilter(urlBasedCorsConfigurationSource);
		}
	}
}