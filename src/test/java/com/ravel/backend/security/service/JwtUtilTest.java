package com.ravel.backend.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

class JwtUtilTest {

	@Test
	void testGetAuthentication() {
		// Arrange
		JwtUtil jwtUtil = new JwtUtil();
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();

		// Act
		Authentication actualAuthentication = jwtUtil.getAuthentication(
			"janedoe",
			authorities,
			new MockHttpServletRequest()
		);

		// Assert
		assertTrue(actualAuthentication.getAuthorities().isEmpty());
		assertEquals(
			"UsernamePasswordAuthenticationToken [Principal=janedoe, Credentials=[PROTECTED], Authenticated=true," +
			" Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=null], Granted Authorities" +
			"=[]]",
			actualAuthentication.toString()
		);
		assertTrue(actualAuthentication.isAuthenticated());
		assertEquals("janedoe", actualAuthentication.getPrincipal());
		assertNull(actualAuthentication.getCredentials());
		assertEquals(
			"127.0.0.1",
			(
				(WebAuthenticationDetails) actualAuthentication.getDetails()
			).getRemoteAddress()
		);
		assertNull(
			((WebAuthenticationDetails) actualAuthentication.getDetails()).getSessionId()
		);
	}

	@Test
	void testGetAuthentication2() {
		// Arrange
		JwtUtil jwtUtil = new JwtUtil();
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();

		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setSession(new MockHttpSession());

		// Act
		Authentication actualAuthentication = jwtUtil.getAuthentication(
			"janedoe",
			authorities,
			mockHttpServletRequest
		);

		// Assert
		assertTrue(actualAuthentication.getAuthorities().isEmpty());
		assertTrue(actualAuthentication.isAuthenticated());
		assertEquals("janedoe", actualAuthentication.getPrincipal());
		Object details = actualAuthentication.getDetails();
		assertTrue(details instanceof WebAuthenticationDetails);
		assertNull(actualAuthentication.getCredentials());
		assertEquals(
			"127.0.0.1",
			((WebAuthenticationDetails) details).getRemoteAddress()
		);
	}
}
