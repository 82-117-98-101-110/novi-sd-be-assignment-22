package com.ravel.backend.modules.customWaterschap.kernproces;

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

@ContextConfiguration(classes = { KernprocesController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class KernprocesControllerTest {

	@Autowired
	private KernprocesController kernprocesController;

	@MockBean
	private KernprocesService kernprocesService;

	@Test
	void testCreateNewKernproces() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/")
			.param("kernProcessImageUrl", "foo")
			.param("kernProcessName", "foo");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.kernprocesController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetAllKernproces() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.kernprocesController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
