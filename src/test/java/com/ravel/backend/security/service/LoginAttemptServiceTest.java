package com.ravel.backend.security.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { LoginAttemptService.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class LoginAttemptServiceTest {

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Test
	void testEvictUserFromLoginAttemptCache() {
		// TODO: This test is incomplete.
		//   Reason: R004 No meaningful assertions found.
		//   Diffblue Cover was unable to create an assertion.
		//   Make sure that fields modified by evictUserFromLoginAttemptCache(String)
		//   have package-private, protected, or public getters.
		//   See https://diff.blue/R004 to resolve this issue.

		// Arrange and Act
		this.loginAttemptService.evictUserFromLoginAttemptCache("janedoe");
	}

	@Test
	void testAddUserToLoginAttemptCache() {
		// TODO: This test is incomplete.
		//   Reason: R004 No meaningful assertions found.
		//   Diffblue Cover was unable to create an assertion.
		//   Make sure that fields modified by addUserToLoginAttemptCache(String)
		//   have package-private, protected, or public getters.
		//   See https://diff.blue/R004 to resolve this issue.

		// Arrange and Act
		this.loginAttemptService.addUserToLoginAttemptCache("janedoe");
	}

	@Test
	void testAddUserToLoginAttemptCache2() {
		// TODO: This test is incomplete.
		//   Reason: R004 No meaningful assertions found.
		//   Diffblue Cover was unable to create an assertion.
		//   Make sure that fields modified by addUserToLoginAttemptCache(String)
		//   have package-private, protected, or public getters.
		//   See https://diff.blue/R004 to resolve this issue.

		// Arrange and Act
		this.loginAttemptService.addUserToLoginAttemptCache("424242");
	}

	@Test
	void testHasExceededMaxAttempts() {
		// Arrange, Act and Assert
		assertFalse(this.loginAttemptService.hasExceededMaxAttempts("janedoe"));
		assertFalse(this.loginAttemptService.hasExceededMaxAttempts("424242"));
	}
}
