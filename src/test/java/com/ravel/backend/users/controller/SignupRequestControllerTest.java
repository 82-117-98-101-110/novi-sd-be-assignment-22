package com.ravel.backend.users.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.backend.users.dtos.SignupPostDto;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.service.UserService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = { SignupRequestController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class SignupRequestControllerTest {

	@Autowired
	private SignupRequestController signupRequestController;

	@MockBean
	private UserService userService;

	@Test
	void testConfirmRavelOrgInvite() throws Exception {
		// Arrange
		when(
			this.userService.acceptNewUserInviteOrg((String) any(), (SignupPostDto) any())
		)
			.thenReturn("Accept New User Invite Org");

		SignupPostDto signupPostDto = new SignupPostDto();
		signupPostDto.setEmail("jane.doe@example.org");
		signupPostDto.setFirstName("Jane");
		signupPostDto.setLastName("Doe");
		signupPostDto.setPassword("iloveyou");
		signupPostDto.setPasswordValidate("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(signupPostDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/api/v1/users/invites/signup")
			.param("token", "foo")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act and Assert
		MockMvcBuilders
			.standaloneSetup(this.signupRequestController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
			.andExpect(MockMvcResultMatchers.content().string("\"OK\""));
	}

	@Test
	void testRegisterUser() {
		// Arrange
		UserDetailsGetDto userDetailsGetDto = new UserDetailsGetDto();
		userDetailsGetDto.setAvatarUrl("https://example.org/example");
		userDetailsGetDto.setEmail("jane.doe@example.org");
		userDetailsGetDto.setFirstName("Jane");
		userDetailsGetDto.setLastName("Doe");
		userDetailsGetDto.setProfileImageUrl("https://example.org/example");
		userDetailsGetDto.setUserUUID(UUID.randomUUID());
		UserService userService = mock(UserService.class);
		when(userService.signupUser((SignupPostDto) any())).thenReturn(userDetailsGetDto);
		SignupRequestController signupRequestController = new SignupRequestController(
			userService
		);

		SignupPostDto signupPostDto = new SignupPostDto();
		signupPostDto.setEmail("jane.doe@example.org");
		signupPostDto.setFirstName("Jane");
		signupPostDto.setLastName("Doe");
		signupPostDto.setPassword("iloveyou");
		signupPostDto.setPasswordValidate("2020-03-01");

		// Act
		ResponseEntity<UserDetailsGetDto> actualRegisterUserResult = signupRequestController.registerUser(
			signupPostDto
		);

		// Assert
		assertTrue(actualRegisterUserResult.getHeaders().isEmpty());
		assertTrue(actualRegisterUserResult.hasBody());
		assertEquals(HttpStatus.OK, actualRegisterUserResult.getStatusCode());
		verify(userService).signupUser((SignupPostDto) any());
	}
}
