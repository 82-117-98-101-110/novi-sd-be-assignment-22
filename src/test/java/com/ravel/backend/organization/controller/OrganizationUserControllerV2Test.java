package com.ravel.backend.organization.controller;

import com.ravel.backend.organization.service.OrganizationUserRoleService;
import java.util.UUID;
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

@ContextConfiguration(classes = { OrganizationUserControllerV2.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class OrganizationUserControllerV2Test {

	@Autowired
	private OrganizationUserControllerV2 organizationUserControllerV2;

	@MockBean
	private OrganizationUserRoleService organizationUserRoleService;

	@Test
	void testGetDetailedOrganizationsForUser() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/organizationsdetailed/{userUuid}",
			UUID.randomUUID()
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationUserControllerV2)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testRemoveUserFromOrganization() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
			"/{organizationName}/userUuid/{userUuid}",
			"Organization Name",
			UUID.randomUUID()
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationUserControllerV2)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateRole() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.put(
				"/users/roles/{organizationName}/usersEmail/{userUuid}",
				"Organization Name",
				UUID.randomUUID()
			)
			.param("organizationRole", "foo");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.organizationUserControllerV2)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
