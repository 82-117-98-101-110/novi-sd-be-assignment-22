package com.ravel.backend.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ravel.backend.users.model.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UserDetailsImplTest {

	@Test
	void testConstructor() {
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

		// Act
		UserDetailsImpl actualUserDetailsImpl = new UserDetailsImpl(user);

		// Assert
		assertTrue(actualUserDetailsImpl.isAccountNonExpired());
		assertTrue(actualUserDetailsImpl.isCredentialsNonExpired());
	}

	@Test
	void testGetAuthorities() {
		// Arrange
		User user = new User();
		user.setAuthorities(new String[] { "JaneDoe" });

		// Act
		Collection<? extends GrantedAuthority> actualAuthorities =
			(new UserDetailsImpl(user)).getAuthorities();

		// Assert
		assertEquals(1, actualAuthorities.size());
		assertEquals(
			"JaneDoe",
			((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority()
		);
	}

	@Test
	void testGetPassword() {
		// Arrange, Act and Assert
		assertNull((new UserDetailsImpl(new User())).getPassword());
	}

	@Test
	void testGetUsername() {
		// Arrange, Act and Assert
		assertNull((new UserDetailsImpl(new User())).getUsername());
	}

	@Test
	void testIsAccountNonLocked() {
		// Arrange, Act and Assert
		assertFalse((new UserDetailsImpl(new User())).isAccountNonLocked());
	}

	@Test
	void testIsAccountNonLocked2() {
		// Arrange
		User user = new User();
		user.setNotLocked(true);

		// Act and Assert
		assertTrue((new UserDetailsImpl(user)).isAccountNonLocked());
	}

	@Test
	void testIsEnabled() {
		// Arrange, Act and Assert
		assertFalse((new UserDetailsImpl(new User())).isEnabled());
	}

	@Test
	void testIsEnabled2() {
		// Arrange
		User user = new User();
		user.setActive(true);

		// Act and Assert
		assertTrue((new UserDetailsImpl(user)).isEnabled());
	}

	@Test
	void testGetUserUUID() {
		// Arrange, Act and Assert
		assertNull((new UserDetailsImpl(new User())).getUserUUID());
	}
}
