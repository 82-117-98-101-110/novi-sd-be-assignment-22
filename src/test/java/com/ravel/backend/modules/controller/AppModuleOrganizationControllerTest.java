package com.ravel.backend.modules.controller;

import com.ravel.backend.modules.service.AppModuleOrganizationService;
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

@ContextConfiguration(classes = { AppModuleOrganizationController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppModuleOrganizationControllerTest {

	@Autowired
	private AppModuleOrganizationController appModuleOrganizationController;

	@MockBean
	private AppModuleOrganizationService appModuleOrganizationService;

	@Test
	void testCreateNewEntry() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/")
			.param("moduleName", "foo")
			.param("organizationName", "foo");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appModuleOrganizationController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetModulesForOrganization() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/{organizationName}",
			"Organization Name"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appModuleOrganizationController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
