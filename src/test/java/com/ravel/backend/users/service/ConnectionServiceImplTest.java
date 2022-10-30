package com.ravel.backend.users.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.exception.ConnectionException;
import com.ravel.backend.users.mapper.ConnectionMapper;
import com.ravel.backend.users.mapper.UserMapper;
import com.ravel.backend.users.repository.ConnectionRepository;
import com.ravel.backend.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { ConnectionServiceImpl.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class ConnectionServiceImplTest {

	@MockBean
	private ConnectionMapper connectionMapper;

	@MockBean
	private ConnectionRepository connectionRepository;

	@Autowired
	private ConnectionServiceImpl connectionServiceImpl;

	@MockBean
	private IAuthenticationFacade iAuthenticationFacade;

	@MockBean
	private UserMapper userMapper;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserService userService;

	@Test
	void testSendConnectionInvite() {
		// Arrange
		when(this.iAuthenticationFacade.getAuthentication())
			.thenThrow(new ConnectionException("An error occurred"));

		// Act and Assert
		assertThrows(
			ConnectionException.class,
			() -> this.connectionServiceImpl.sendConnectionInvite("jane.doe@example.org")
		);
		verify(this.iAuthenticationFacade).getAuthentication();
	}

	@Test
	void testGetConnections() {
		// Arrange
		when(this.iAuthenticationFacade.getAuthentication())
			.thenThrow(new ConnectionException("An error occurred"));

		// Act and Assert
		assertThrows(
			ConnectionException.class,
			() -> this.connectionServiceImpl.getConnections()
		);
		verify(this.iAuthenticationFacade).getAuthentication();
	}

	@Test
	void testGetConnectionInvites() {
		// Arrange
		when(this.iAuthenticationFacade.getAuthentication())
			.thenThrow(new ConnectionException("An error occurred"));

		// Act and Assert
		assertThrows(
			ConnectionException.class,
			() -> this.connectionServiceImpl.getConnectionInvites()
		);
		verify(this.iAuthenticationFacade).getAuthentication();
	}

	@Test
	void testAcceptConnectionInvite() {
		// Arrange
		when(this.iAuthenticationFacade.getAuthentication())
			.thenThrow(new ConnectionException("An error occurred"));

		// Act and Assert
		assertThrows(
			ConnectionException.class,
			() ->
				this.connectionServiceImpl.acceptConnectionInvite("jane.doe@example.org")
		);
		verify(this.iAuthenticationFacade).getAuthentication();
	}

	@Test
	void testDeclineConnectionInvite() {
		// Arrange
		when(this.iAuthenticationFacade.getAuthentication())
			.thenThrow(new ConnectionException("An error occurred"));

		// Act and Assert
		assertThrows(
			ConnectionException.class,
			() ->
				this.connectionServiceImpl.declineConnectionInvite("jane.doe@example.org")
		);
		verify(this.iAuthenticationFacade).getAuthentication();
	}

	@Test
	void testRemoveConnection() {
		// Arrange
		when(this.iAuthenticationFacade.getAuthentication())
			.thenThrow(new ConnectionException("An error occurred"));

		// Act and Assert
		assertThrows(
			ConnectionException.class,
			() -> this.connectionServiceImpl.removeConnection("jane.doe@example.org")
		);
		verify(this.iAuthenticationFacade).getAuthentication();
	}
}
