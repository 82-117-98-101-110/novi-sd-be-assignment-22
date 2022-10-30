package com.ravel.backend.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.backend.security.payload.VerifyCode;
import com.ravel.backend.security.payload.request.AuthenticationRequest;
import com.ravel.backend.security.payload.request.AuthenticationRequestVR;
import com.ravel.backend.security.payload.request.RequestCodeRequest;
import com.ravel.backend.security.service.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = { AuthenticationController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {

	@Autowired
	private AuthenticationController authenticationController;

	@MockBean
	private AuthorizationService authorizationService;

	@Test
	void testAuthenticateUser() throws Exception {
		// Arrange
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setEmail("jane.doe@example.org");
		authenticationRequest.setPassword("iloveyou");
		String content = (new ObjectMapper()).writeValueAsString(authenticationRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.authenticationController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testAuthenticateVrClient() throws Exception {
		// Arrange
		AuthenticationRequestVR authenticationRequestVR = new AuthenticationRequestVR();
		authenticationRequestVR.setClientId("42");
		authenticationRequestVR.setCode("Code");
		String content = (new ObjectMapper()).writeValueAsString(authenticationRequestVR);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/passwordless/authenticate")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.authenticationController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testRequestCodeVrClient() throws Exception {
		// Arrange
		RequestCodeRequest requestCodeRequest = new RequestCodeRequest();
		requestCodeRequest.setClientId("42");
		String content = (new ObjectMapper()).writeValueAsString(requestCodeRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/passwordless/request")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.authenticationController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testVerifyCodeVrClient() throws Exception {
		// Arrange
		VerifyCode verifyCode = new VerifyCode();
		verifyCode.setClientId("42");
		verifyCode.setCode("Code");
		String content = (new ObjectMapper()).writeValueAsString(verifyCode);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/passwordless/verify")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.authenticationController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
