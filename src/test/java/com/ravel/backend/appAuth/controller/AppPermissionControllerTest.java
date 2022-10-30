package com.ravel.backend.appAuth.controller;

import com.ravel.backend.appAuth.service.AppPermissionService;
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

@ContextConfiguration(classes = { AppPermissionController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppPermissionControllerTest {

	@Autowired
	private AppPermissionController appPermissionController;

	@MockBean
	private AppPermissionService appPermissionService;

	@Test
	void testGetAllAppPermissionsWithPermissions() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/roles"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appPermissionController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetAllPermissions() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appPermissionController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetAppPermissionByName() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/{permissionName}",
			"Permission Name"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appPermissionController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
