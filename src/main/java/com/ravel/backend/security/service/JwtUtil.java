package com.ravel.backend.security.service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	@Value("${security.constant.jwt.secret}")
	private String jwtSecret;

	@Value("${security.constant.jwt.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${security.constant.jwt.issuer}")
	private String issuer;

	@Value("${security.constant.jwt.audience}")
	private String audience;

	public String generateJwtToken(UserDetailsImpl userDetailsImpl) {
		String[] claims = getClaimsFromUser(userDetailsImpl);
		return JWT
			.create()
			.withIssuer(issuer)
			.withIssuedAt(new Date())
			.withSubject(String.valueOf(userDetailsImpl.getUserUUID()))
			.withArrayClaim("authorities", claims)
			.withClaim("userEmail", userDetailsImpl.getUsername())
			.withAudience(audience)
			.withExpiresAt(new Date((new Date()).getTime() + jwtExpirationMs))
			.sign(Algorithm.HMAC512(jwtSecret.getBytes()));
	}

	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = getClaimsFromToken(token);
		return stream(claims)
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}

	public Authentication getAuthentication(
		String username,
		List<GrantedAuthority> authorities,
		HttpServletRequest request
	) {
		UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(
			username,
			null,
			authorities
		);
		userPasswordAuthToken.setDetails(
			new WebAuthenticationDetailsSource().buildDetails(request)
		);
		return userPasswordAuthToken;
	}

	public boolean isTokenValid(String userUuid, String token) {
		JWTVerifier verifier = getJWTVerifier();
		return (StringUtils.isNotEmpty(userUuid) && !isTokenExpired(verifier, token));
	}

	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getSubject();
	}

	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim("authorities").asArray(String.class);
	}

	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			Algorithm algorithm = HMAC512(jwtSecret);
			verifier = JWT.require(algorithm).withIssuer(issuer).build();
		} catch (JWTVerificationException exception) {
			throw new JWTVerificationException("Token cannot be verified");
		}
		return verifier;
	}

	private String[] getClaimsFromUser(UserDetailsImpl user) {
		List<String> authorities = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}
}
