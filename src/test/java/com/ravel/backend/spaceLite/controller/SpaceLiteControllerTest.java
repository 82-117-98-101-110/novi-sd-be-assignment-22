package com.ravel.backend.spaceLite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.backend.spaceLite.dto.SpaceLitePostDto;
import com.ravel.backend.spaceLite.service.SpaceLiteOrganizationService;
import com.ravel.backend.spaceLite.service.SpaceLiteService;
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

@ContextConfiguration(classes = { SpaceLiteController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class SpaceLiteControllerTest {

	@Autowired
	private SpaceLiteController spaceLiteController;

	@MockBean
	private SpaceLiteOrganizationService spaceLiteOrganizationService;

	@MockBean
	private SpaceLiteService spaceLiteService;

	@Test
	void testAddLiteSpaceToOrganization() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
			"/organization/*/space/*"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.spaceLiteController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testCreateSpaceLite() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
			.get("/")
			.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(
			objectMapper.writeValueAsString(
				new SpaceLitePostDto(
					"Name",
					"Src",
					"Room Code",
					"Invite Link",
					"Embedded Code"
				)
			)
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.spaceLiteController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetActiveSpaces() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.spaceLiteController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetActiveSpacesForUser() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/user"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.spaceLiteController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetSpaceLite() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/*");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.spaceLiteController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateSpaceLite() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
			.put("/*")
			.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(
			objectMapper.writeValueAsString(
				new SpaceLitePostDto(
					"Name",
					"Src",
					"Room Code",
					"Invite Link",
					"Embedded Code"
				)
			)
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.spaceLiteController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
