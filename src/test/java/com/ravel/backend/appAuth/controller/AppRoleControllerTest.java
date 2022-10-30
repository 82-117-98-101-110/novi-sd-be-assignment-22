package com.ravel.backend.appAuth.controller;

import com.ravel.backend.appAuth.service.AppRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = { AppRoleController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppRoleControllerTest {

	@Autowired
	private AppRoleController appRoleController;

	@MockBean
	private AppRoleService appRoleService;

	@Test
	void testGetAllAppRolesWithPermissions() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/permissions"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appRoleController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetAllRoles() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appRoleController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetAppRoleByName() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/{appRoleName}",
			"App Role Name"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appRoleController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
