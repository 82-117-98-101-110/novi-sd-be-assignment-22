package com.ravel.backend.users.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.ravel.backend.users.model.User;
import com.ravel.backend.users.service.UserService;
import java.util.HashSet;
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

@ContextConfiguration(classes = { UserManagementControllerV2.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class UserManagementControllerV2Test {

	@Autowired
	private UserManagementControllerV2 userManagementControllerV2;

	@MockBean
	private UserService userService;

	@Test
	void testDeactivateUser() throws Exception {
		// Arrange
		User user = new User();
		user.setActive((Boolean) true);
		user.setActive(true);
		user.setAuthorities(new String[] { "JaneDoe" });
		user.setAvatarUrl("https://example.org/example");
		user.setEmail("jane.doe@example.org");
		user.setFirstName("Jane");
		user.setLastName("Doe");
		user.setNotLocked((Boolean) true);
		user.setNotLocked(true);
		user.setPassword("iloveyou");
		user.setProfileImageUrl("https://example.org/example");
		user.setReceivers(new HashSet<>());
		user.setRole("Role");
		user.setSenders(new HashSet<>());
		user.setUserUUID(UUID.randomUUID());
		when(this.userService.deactivateUserWithUuid((UUID) any(), (Boolean) any()))
			.thenReturn(user);
		MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put(
			"/api/v2/admin/users/deactivate/{userUuid}",
			UUID.randomUUID()
		);
		MockHttpServletRequestBuilder requestBuilder = putResult.param(
			"bool",
			String.valueOf(true)
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.userManagementControllerV2)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	void testLockUser() throws Exception {
		// Arrange
		User user = new User();
		user.setActive((Boolean) true);
		user.setActive(true);
		user.setAuthorities(new String[] { "JaneDoe" });
		user.setAvatarUrl("https://example.org/example");
		user.setEmail("jane.doe@example.org");
		user.setFirstName("Jane");
		user.setLastName("Doe");
		user.setNotLocked((Boolean) true);
		user.setNotLocked(true);
		user.setPassword("iloveyou");
		user.setProfileImageUrl("https://example.org/example");
		user.setReceivers(new HashSet<>());
		user.setRole("Role");
		user.setSenders(new HashSet<>());
		user.setUserUUID(UUID.randomUUID());
		when(this.userService.lockUserWithUuid((UUID) any(), (Boolean) any()))
			.thenReturn(user);
		MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put(
			"/api/v2/admin/users/lock/{userUuid}",
			UUID.randomUUID()
		);
		MockHttpServletRequestBuilder requestBuilder = putResult.param(
			"bool",
			String.valueOf(true)
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.userManagementControllerV2)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	void testUpdateUserSystemRole() throws Exception {
		// Arrange
		doNothing()
			.when(this.userService)
			.updateUserSystemRoleWithUuid((String) any(), (UUID) any());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.put("/api/v2/admin/users/updaterole/{userUuid}", UUID.randomUUID())
			.param("role", "foo");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.userManagementControllerV2)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
}
