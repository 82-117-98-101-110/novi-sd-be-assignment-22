package com.ravel.backend.users.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.backend.users.dtos.AvatarPostDto;
import com.ravel.backend.users.dtos.ResetPasswordRequest;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.service.UserService;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = { UserControllerV2.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class UserControllerV2Test {

	@Autowired
	private UserControllerV2 userControllerV2;

	@MockBean
	private UserService userService;

	@Test
	void testSendResetPasswordRequest() throws Exception {
		// Arrange
		User user = new User();
		user.setActive((Boolean) true);
		user.setActive(true);
		user.setAuthorities(new String[] { "JaneDoe" });
		user.setAvatarUrl("https://example.org/example");
		user.setEmail("jane.doe@example.org");
		user.setFirstName("Jane");
		user.setLastName("Doe");
		user.setNotLocked((Boolean) true);
		user.setNotLocked(true);
		user.setPassword("iloveyou");
		user.setProfileImageUrl("https://example.org/example");
		user.setReceivers(new HashSet<>());
		user.setRole("Role");
		user.setSenders(new HashSet<>());
		user.setUserUUID(UUID.randomUUID());
		when(this.userService.sendResetPassword((String) any())).thenReturn(user);

		ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
		resetPasswordRequest.setEmail("jane.doe@example.org");
		String content = (new ObjectMapper()).writeValueAsString(resetPasswordRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/api/v2/users/resetPasswordRequest")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act and Assert
		MockMvcBuilders
			.standaloneSetup(this.userControllerV2)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testUpdateAvatar() throws Exception {
		// Arrange
		UserDetailsGetDto userDetailsGetDto = new UserDetailsGetDto();
		userDetailsGetDto.setAvatarUrl("https://example.org/example");
		userDetailsGetDto.setEmail("jane.doe@example.org");
		userDetailsGetDto.setFirstName("Jane");
		userDetailsGetDto.setLastName("Doe");
		userDetailsGetDto.setProfileImageUrl("https://example.org/example");
		userDetailsGetDto.setUserUUID(UUID.randomUUID());
		when(this.userService.updateAvatarWithUuid((UUID) any(), (AvatarPostDto) any()))
			.thenReturn(userDetailsGetDto);

		AvatarPostDto avatarPostDto = new AvatarPostDto();
		avatarPostDto.setAvatarUrl("https://example.org/example");
		String content = (new ObjectMapper()).writeValueAsString(avatarPostDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.put("/api/v2/users/avatar/{userUuid}", UUID.randomUUID())
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act and Assert
		MockMvcBuilders
			.standaloneSetup(this.userControllerV2)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
