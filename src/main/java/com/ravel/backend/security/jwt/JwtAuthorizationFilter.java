package com.ravel.backend.security.jwt;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import com.ravel.backend.security.service.JwtUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	//TODO implement Token Signature invalid exception!!!!!
	private JwtUtil jwtUtil;
	private String tokenPrefix = "Bearer ";

	public JwtAuthorizationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		if (request.getMethod().equalsIgnoreCase("OPTIONS")) { //TODO check OPTIONS implementation?
			response.setStatus(OK.value());
		} else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (
				authorizationHeader == null ||
				!authorizationHeader.startsWith(tokenPrefix)
			) {
				filterChain.doFilter(request, response);
				return;
			}
			String token = authorizationHeader.substring(tokenPrefix.length());
			String userUuid = jwtUtil.getSubject(token);
			if (
				jwtUtil.isTokenValid(userUuid, token) &&
				SecurityContextHolder.getContext().getAuthentication() == null
			) {
				List<GrantedAuthority> authorities = jwtUtil.getAuthorities(token);
				Authentication authentication = jwtUtil.getAuthentication(
					userUuid,
					authorities,
					request
				);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);
	}
}
