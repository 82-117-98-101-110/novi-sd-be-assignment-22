package com.ravel.backend.appAuth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.backend.appAuth.dto.PermissionPostDto;
import com.ravel.backend.appAuth.service.AppPermissionService;
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

@ContextConfiguration(classes = { AppPermissionAdminController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppPermissionAdminControllerTest {

	@Autowired
	private AppPermissionAdminController appPermissionAdminController;

	@MockBean
	private AppPermissionService appPermissionService;

	@Test
	void testAddPermissionToRole() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
			"/{appRoleName}/permissions/{appPermissionName}",
			"App Role Name",
			"App Permission Name"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appPermissionAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testCreateNewPermission() throws Exception {
		// Arrange
		PermissionPostDto permissionPostDto = new PermissionPostDto();
		permissionPostDto.setAppPermissionName("App Permission Name");
		permissionPostDto.setDescription("The characteristics of someone or something");
		String content = (new ObjectMapper()).writeValueAsString(permissionPostDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appPermissionAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateAppPermission() throws Exception {
		// Arrange
		PermissionPostDto permissionPostDto = new PermissionPostDto();
		permissionPostDto.setAppPermissionName("App Permission Name");
		permissionPostDto.setDescription("The characteristics of someone or something");
		String content = (new ObjectMapper()).writeValueAsString(permissionPostDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.put("/{appPermissionName}", "App Permission Name")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appPermissionAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
