package com.ravel.backend.appAuth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.backend.appAuth.dto.RolePostDto;
import com.ravel.backend.appAuth.service.AppRoleService;
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

@ContextConfiguration(classes = { AppRoleAdminController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppRoleAdminControllerTest {

	@Autowired
	private AppRoleAdminController appRoleAdminController;

	@MockBean
	private AppRoleService appRoleService;

	@Test
	void testCreateNewRole() throws Exception {
		// Arrange
		RolePostDto rolePostDto = new RolePostDto();
		rolePostDto.setAppRoleName("App Role Name");
		rolePostDto.setDescription("The characteristics of someone or something");
		rolePostDto.setPurpose("Purpose");
		String content = (new ObjectMapper()).writeValueAsString(rolePostDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appRoleAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateAppRole() throws Exception {
		// Arrange
		RolePostDto rolePostDto = new RolePostDto();
		rolePostDto.setAppRoleName("App Role Name");
		rolePostDto.setDescription("The characteristics of someone or something");
		rolePostDto.setPurpose("Purpose");
		String content = (new ObjectMapper()).writeValueAsString(rolePostDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.put("/{appRoleName}", "App Role Name")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appRoleAdminController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
