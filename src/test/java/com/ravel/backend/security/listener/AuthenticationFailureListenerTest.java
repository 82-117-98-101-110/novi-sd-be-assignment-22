package com.ravel.backend.security.listener;

import com.ravel.backend.security.service.LoginAttemptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
	classes = { AuthenticationFailureListener.class, LoginAttemptService.class }
)
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AuthenticationFailureListenerTest {

	@Autowired
	private AuthenticationFailureListener authenticationFailureListener;

	@Test
	void testOnAuthenticationFailure() {
		// TODO: This test is incomplete.
		//   Reason: R004 No meaningful assertions found.
		//   Diffblue Cover was unable to create an assertion.
		//   Make sure that fields modified by onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent)
		//   have package-private, protected, or public getters.
		//   See https://diff.blue/R004 to resolve this issue.

		// Arrange
		BearerTokenAuthenticationToken authentication = new BearerTokenAuthenticationToken(
			"ABC123"
		);

		// Act
		this.authenticationFailureListener.onAuthenticationFailure(
				new AuthenticationFailureBadCredentialsEvent(
					authentication,
					new AccountExpiredException("Msg")
				)
			);
	}

	@Test
	void testOnAuthenticationFailure2() {
		// TODO: This test is incomplete.
		//   Reason: R004 No meaningful assertions found.
		//   Diffblue Cover was unable to create an assertion.
		//   Make sure that fields modified by onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent)
		//   have package-private, protected, or public getters.
		//   See https://diff.blue/R004 to resolve this issue.

		// Arrange
		BearerTokenAuthenticationToken authentication = new BearerTokenAuthenticationToken(
			"Token"
		);

		// Act
		this.authenticationFailureListener.onAuthenticationFailure(
				new AuthenticationFailureBadCredentialsEvent(
					authentication,
					new AccountExpiredException("Msg")
				)
			);
	}
}
