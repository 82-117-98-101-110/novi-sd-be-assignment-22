package com.ravel.backend.users.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ravel.backend.users.model.Connection;
import com.ravel.backend.users.model.ConnectionId;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.service.ConnectionService;
import java.util.ArrayList;
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

@ContextConfiguration(classes = { ConnectionController.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class ConnectionControllerTest {

	@Autowired
	private ConnectionController connectionController;

	@MockBean
	private ConnectionService connectionService;

	@Test
	void testGetConnectionInvites() throws Exception {
		// Arrange
		when(this.connectionService.getConnectionInvites()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/api/v1/connections/invites"
		);

		// Act and Assert
		MockMvcBuilders
			.standaloneSetup(this.connectionController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
			.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void testGetConnectionInvites2() throws Exception {
		// Arrange
		when(this.connectionService.getConnectionInvites()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get(
			"/api/v1/connections/invites"
		);
		getResult.characterEncoding("Encoding");

		// Act and Assert
		MockMvcBuilders
			.standaloneSetup(this.connectionController)
			.build()
			.perform(getResult)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
			.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void testGetConnections() throws Exception {
		// Arrange
		when(this.connectionService.getConnections()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
			"/api/v1/connections"
		);

		// Act and Assert
		MockMvcBuilders
			.standaloneSetup(this.connectionController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
			.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void testGetConnections2() throws Exception {
		// Arrange
		when(this.connectionService.getConnections()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get(
			"/api/v1/connections"
		);
		getResult.characterEncoding("Encoding");

		// Act and Assert
		MockMvcBuilders
			.standaloneSetup(this.connectionController)
			.build()
			.perform(getResult)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
			.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void testSendConnectionInvite() throws Exception {
		// Arrange
		ConnectionId connectionId = new ConnectionId();
		connectionId.setReceiverUuid(UUID.randomUUID());
		connectionId.setSenderUuid(UUID.randomUUID());

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

		User user1 = new User();
		user1.setActive((Boolean) true);
		user1.setActive(true);
		user1.setAuthorities(new String[] { "JaneDoe" });
		user1.setAvatarUrl("https://example.org/example");
		user1.setEmail("jane.doe@example.org");
		user1.setFirstName("Jane");
		user1.setLastName("Doe");
		user1.setNotLocked((Boolean) true);
		user1.setNotLocked(true);
		user1.setPassword("iloveyou");
		user1.setProfileImageUrl("https://example.org/example");
		user1.setReceivers(new HashSet<>());
		user1.setRole("Role");
		user1.setSenders(new HashSet<>());
		user1.setUserUUID(UUID.randomUUID());

		Connection connection = new Connection();
		connection.setAcceptedAt(null);
		connection.setDeclinedAt(null);
		connection.setId(connectionId);
		connection.setInviteOpen(true);
		connection.setInvitedAt(null);
		connection.setReceiver(user);
		connection.setSender(user1);
		when(this.connectionService.sendConnectionInvite((String) any()))
			.thenReturn(connection);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
			"/api/v1/connections/sendinvites/{userEmail}",
			"jane.doe@example.org"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.connectionController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void testAcceptConnectionInvite() throws Exception {
		// Arrange
		ConnectionId connectionId = new ConnectionId();
		connectionId.setReceiverUuid(UUID.randomUUID());
		connectionId.setSenderUuid(UUID.randomUUID());

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

		User user1 = new User();
		user1.setActive((Boolean) true);
		user1.setActive(true);
		user1.setAuthorities(new String[] { "JaneDoe" });
		user1.setAvatarUrl("https://example.org/example");
		user1.setEmail("jane.doe@example.org");
		user1.setFirstName("Jane");
		user1.setLastName("Doe");
		user1.setNotLocked((Boolean) true);
		user1.setNotLocked(true);
		user1.setPassword("iloveyou");
		user1.setProfileImageUrl("https://example.org/example");
		user1.setReceivers(new HashSet<>());
		user1.setRole("Role");
		user1.setSenders(new HashSet<>());
		user1.setUserUUID(UUID.randomUUID());

		Connection connection = new Connection();
		connection.setAcceptedAt(null);
		connection.setDeclinedAt(null);
		connection.setId(connectionId);
		connection.setInviteOpen(true);
		connection.setInvitedAt(null);
		connection.setReceiver(user);
		connection.setSender(user1);
		when(this.connectionService.acceptConnectionInvite((String) any()))
			.thenReturn(connection);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(
			"/api/v1/connections/accepts/{emailFrom}",
			"jane.doe@example.org"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.connectionController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	void testDeclineConnectionInvite() throws Exception {
		// Arrange
		ConnectionId connectionId = new ConnectionId();
		connectionId.setReceiverUuid(UUID.randomUUID());
		connectionId.setSenderUuid(UUID.randomUUID());

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

		User user1 = new User();
		user1.setActive((Boolean) true);
		user1.setActive(true);
		user1.setAuthorities(new String[] { "JaneDoe" });
		user1.setAvatarUrl("https://example.org/example");
		user1.setEmail("jane.doe@example.org");
		user1.setFirstName("Jane");
		user1.setLastName("Doe");
		user1.setNotLocked((Boolean) true);
		user1.setNotLocked(true);
		user1.setPassword("iloveyou");
		user1.setProfileImageUrl("https://example.org/example");
		user1.setReceivers(new HashSet<>());
		user1.setRole("Role");
		user1.setSenders(new HashSet<>());
		user1.setUserUUID(UUID.randomUUID());

		Connection connection = new Connection();
		connection.setAcceptedAt(null);
		connection.setDeclinedAt(null);
		connection.setId(connectionId);
		connection.setInviteOpen(true);
		connection.setInvitedAt(null);
		connection.setReceiver(user);
		connection.setSender(user1);
		when(this.connectionService.declineConnectionInvite((String) any()))
			.thenReturn(connection);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(
			"/api/v1/connections/{emailFrom}",
			"jane.doe@example.org"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.connectionController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void testRemoveConnection() throws Exception {
		// Arrange
		ConnectionId connectionId = new ConnectionId();
		connectionId.setReceiverUuid(UUID.randomUUID());
		connectionId.setSenderUuid(UUID.randomUUID());

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

		User user1 = new User();
		user1.setActive((Boolean) true);
		user1.setActive(true);
		user1.setAuthorities(new String[] { "JaneDoe" });
		user1.setAvatarUrl("https://example.org/example");
		user1.setEmail("jane.doe@example.org");
		user1.setFirstName("Jane");
		user1.setLastName("Doe");
		user1.setNotLocked((Boolean) true);
		user1.setNotLocked(true);
		user1.setPassword("iloveyou");
		user1.setProfileImageUrl("https://example.org/example");
		user1.setReceivers(new HashSet<>());
		user1.setRole("Role");
		user1.setSenders(new HashSet<>());
		user1.setUserUUID(UUID.randomUUID());

		Connection connection = new Connection();
		connection.setAcceptedAt(null);
		connection.setDeclinedAt(null);
		connection.setId(connectionId);
		connection.setInviteOpen(true);
		connection.setInvitedAt(null);
		connection.setReceiver(user);
		connection.setSender(user1);
		when(this.connectionService.removeConnection((String) any()))
			.thenReturn(connection);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
			"/api/v1/connections/{emailFrom}",
			"jane.doe@example.org"
		);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders
			.standaloneSetup(this.connectionController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
