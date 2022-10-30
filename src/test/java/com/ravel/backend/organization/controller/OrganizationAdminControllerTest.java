package com.ravel.backend.organization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.backend.organization.dtos.OrganizationPostDto;
import com.ravel.backend.organization.service.OrganizationService;
import java.util.UUID;
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

@ContextConfiguration(classes = { OrganizationAdminController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class OrganizationAdminControllerTest {

	@Autowired
	private OrganizationAdminController organizationAdminController;

	@MockBean
	private OrganizationService organizationService;

	@Test
	void testCreateOrganization() throws Exception {
		// Arrange
		OrganizationPostDto organizationPostDto = new OrganizationPostDto();
		organizationPostDto.setOrganizationName("Organization Name");
		String content = (new ObjectMapper()).writeValueAsString(organizationPostDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testFindOrganizationsDto() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetOrganizationById() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/active/ids/{organizationId}",
			UUID.randomUUID()
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testOrganizationActivation() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put(
			"/activation/{organizationName}",
			"Organization Name"
		);
		MockHttpServletRequestBuilder requestBuilder = putResult.param(
			"bool",
			String.valueOf(true)
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateOrganization() throws Exception {
		// Arrange
		OrganizationPostDto organizationPostDto = new OrganizationPostDto();
		organizationPostDto.setOrganizationName("Organization Name");
		String content = (new ObjectMapper()).writeValueAsString(organizationPostDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.put("/{organizationName}", "Organization Name")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
