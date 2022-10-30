package com.ravel.backend.modules.controller;

import com.ravel.backend.modules.service.AppModuleUserService;
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

@ContextConfiguration(classes = { AppModuleUserController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppModuleUserControllerTest {

	@Autowired
	private AppModuleUserController appModuleUserController;

	@MockBean
	private AppModuleUserService appModuleUserService;

	@Test
	void testCreateNewEntry() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/")
			.param("moduleName", "foo")
			.param("userEmail", "foo");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appModuleUserController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testCreateNewEntryWithUuid() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders
			.post("/uuid")
			.param("moduleName", "foo");
		MockHttpServletRequestBuilder requestBuilder = paramResult.param(
			"userUuid",
			String.valueOf(UUID.randomUUID())
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appModuleUserController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetAllModuleUsers() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/all");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appModuleUserController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetModulesForUser() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/{userEmail}",
			"jane.doe@example.org"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appModuleUserController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetModulesForUserUuid() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/uuid/{userUuid}",
			UUID.randomUUID()
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.appModuleUserController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
