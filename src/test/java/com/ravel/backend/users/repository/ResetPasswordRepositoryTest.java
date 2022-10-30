package com.ravel.backend.users.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ravel.backend.users.model.ResetPassword;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "h2" })
@DataJpaTest
class ResetPasswordRepositoryTest {

	@Autowired
	private ResetPasswordRepository resetPasswordRepository;

	@Test
	void testFindByToken() {
		// Arrange
		ResetPassword resetPassword = new ResetPassword();
		resetPassword.setEmail("jane.doe@example.org");
		resetPassword.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword.setToken("ABC123");
		resetPassword.setUserUUID(UUID.randomUUID());

		ResetPassword resetPassword1 = new ResetPassword();
		resetPassword1.setEmail("jane.doe@example.org");
		resetPassword1.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword1.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword1.setToken("ABC123");
		resetPassword1.setUserUUID(UUID.randomUUID());
		this.resetPasswordRepository.save(resetPassword);
		this.resetPasswordRepository.save(resetPassword1);

		// Act and Assert
		assertFalse(this.resetPasswordRepository.findByToken("foo").isPresent());
	}

	@Test
	void testUpdateConfirmedAt() {
		// Arrange
		ResetPassword resetPassword = new ResetPassword();
		resetPassword.setEmail("jane.doe@example.org");
		resetPassword.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword.setToken("ABC123");
		resetPassword.setUserUUID(UUID.randomUUID());

		ResetPassword resetPassword1 = new ResetPassword();
		resetPassword1.setEmail("jane.doe@example.org");
		resetPassword1.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword1.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
		resetPassword1.setToken("ABC123");
		resetPassword1.setUserUUID(UUID.randomUUID());
		this.resetPasswordRepository.save(resetPassword);
		this.resetPasswordRepository.save(resetPassword1);

		// Act and Assert
		assertEquals(
			0,
			this.resetPasswordRepository.updateConfirmedAt(
					"foo",
					LocalDateTime.of(1, 1, 1, 1, 1)
				)
		);
	}
}
