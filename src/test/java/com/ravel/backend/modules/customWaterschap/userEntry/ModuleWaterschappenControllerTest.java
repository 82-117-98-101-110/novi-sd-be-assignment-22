package com.ravel.backend.modules.customWaterschap.userEntry;

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

@ContextConfiguration(classes = { ModuleWaterschappenController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class ModuleWaterschappenControllerTest {

	@Autowired
	private ModuleWaterschappenController moduleWaterschappenController;

	@MockBean
	private ModuleWaterschappenService moduleWaterschappenService;

	@Test
	void testCreateNewEntry() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/")
			.param("email", "foo")
			.param("kernProcesName", "foo")
			.param("waterschappenName", "foo");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.moduleWaterschappenController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testCreateNewEntryUuid() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders
			.post("/uuid/")
			.param("kernProcesName", "foo");
		MockHttpServletRequestBuilder requestBuilder = paramResult
			.param("userUuid", String.valueOf(UUID.randomUUID()))
			.param("waterschappenName", "foo");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.moduleWaterschappenController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetEntries() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.moduleWaterschappenController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetEntryByUserUuid() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/uuid/{userUuid}",
			UUID.randomUUID()
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.moduleWaterschappenController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetEntryByUsername() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/{email}",
			"jane.doe@example.org"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.moduleWaterschappenController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
