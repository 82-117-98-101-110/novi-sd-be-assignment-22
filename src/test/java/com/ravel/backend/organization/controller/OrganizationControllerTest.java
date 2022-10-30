package com.ravel.backend.organization.controller;

import com.ravel.backend.organization.service.OrganizationService;
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

@ContextConfiguration(classes = { OrganizationController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class OrganizationControllerTest {

	@Autowired
	private OrganizationController organizationController;

	@MockBean
	private OrganizationService organizationService;

	@Test
	void testGetActiveOrganizations() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/active"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetOrganizationByName() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/active/{organizationName}",
			"Organization Name"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
