package com.ravel.backend.security.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.users.model.User;
import com.ravel.backend.users.repository.UserRepository;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
	classes = { UserDetailsServiceImpl.class, LoginAttemptService.class }
)
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	private UserRepository userRepository;

	@Test
	void testLoadUserByUsername() throws BadCredentialsException {
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
		when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
		when(this.userRepository.existsByEmailIgnoreCase((String) any()))
			.thenReturn(true);

		// Act
		UserDetails actualLoadUserByUsernameResult =
			this.userDetailsServiceImpl.loadUserByUsername("jane.doe@example.org");

		// Assert
		assertTrue(actualLoadUserByUsernameResult.isEnabled());
		assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
		verify(this.userRepository).existsByEmailIgnoreCase((String) any());
		verify(this.userRepository).findUserByEmail((String) any());
	}

	@Test
	void testLoadUserByUsername2() throws BadCredentialsException {
		// Arrange
		when(this.userRepository.findUserByEmail((String) any()))
			.thenThrow(new BadCredentialsException("Msg"));
		when(this.userRepository.existsByEmailIgnoreCase((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			BadCredentialsException.class,
			() -> this.userDetailsServiceImpl.loadUserByUsername("jane.doe@example.org")
		);
		verify(this.userRepository).existsByEmailIgnoreCase((String) any());
		verify(this.userRepository).findUserByEmail((String) any());
	}

	@Test
	void testLoadUserByUsername3() throws BadCredentialsException {
		// Arrange
		User user = new User();
		user.setActive((Boolean) false);
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
		when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
		when(this.userRepository.existsByEmailIgnoreCase((String) any()))
			.thenReturn(true);

		// Act
		UserDetails actualLoadUserByUsernameResult =
			this.userDetailsServiceImpl.loadUserByUsername("jane.doe@example.org");

		// Assert
		assertTrue(actualLoadUserByUsernameResult.isEnabled());
		assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
		verify(this.userRepository).existsByEmailIgnoreCase((String) any());
		verify(this.userRepository).findUserByEmail((String) any());
	}

	@Test
	void testLoadUserByUsername4() throws BadCredentialsException {
		// Arrange
		User user = new User();
		user.setActive((Boolean) true);
		user.setActive(true);
		user.setAuthorities(new String[] { "JaneDoe" });
		user.setAvatarUrl("https://example.org/example");
		user.setEmail("Email");
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
		when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
		when(this.userRepository.existsByEmailIgnoreCase((String) any()))
			.thenReturn(true);

		// Act
		UserDetails actualLoadUserByUsernameResult =
			this.userDetailsServiceImpl.loadUserByUsername("jane.doe@example.org");

		// Assert
		assertTrue(actualLoadUserByUsernameResult.isEnabled());
		assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
		verify(this.userRepository).existsByEmailIgnoreCase((String) any());
		verify(this.userRepository).findUserByEmail((String) any());
	}

	@Test
	void testLoadUserByUsername5() throws BadCredentialsException {
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
		user.setNotLocked(false);
		user.setPassword("iloveyou");
		user.setProfileImageUrl("https://example.org/example");
		user.setReceivers(new HashSet<>());
		user.setRole("Role");
		user.setSenders(new HashSet<>());
		user.setUserUUID(UUID.randomUUID());
		when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
		when(this.userRepository.existsByEmailIgnoreCase((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertTrue(
			this.userDetailsServiceImpl.loadUserByUsername("jane.doe@example.org")
				.isEnabled()
		);
		verify(this.userRepository).existsByEmailIgnoreCase((String) any());
		verify(this.userRepository).findUserByEmail((String) any());
	}

	@Test
	void testLoadUserByUsername6() throws BadCredentialsException {
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
		when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
		when(this.userRepository.existsByEmailIgnoreCase((String) any()))
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			BadCredentialsException.class,
			() -> this.userDetailsServiceImpl.loadUserByUsername("jane.doe@example.org")
		);
		verify(this.userRepository).existsByEmailIgnoreCase((String) any());
	}
}
