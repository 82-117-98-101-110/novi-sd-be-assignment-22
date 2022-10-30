package com.ravel.backend.users.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ravel.backend.users.model.UserInvite;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "h2" })
@DataJpaTest
class UserInviteRepositoryTest {

	@Autowired
	private UserInviteRepository userInviteRepository;

	@Test
	void testFindByToken() {
		// Arrange
		UserInvite userInvite = new UserInvite();
		userInvite.setOrganizationUUID(UUID.randomUUID());
		userInvite.setEmail("jane.doe@example.org");
		userInvite.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite.setToken("ABC123");
		userInvite.setUserUUID(UUID.randomUUID());

		UserInvite userInvite1 = new UserInvite();
		userInvite1.setOrganizationUUID(UUID.randomUUID());
		userInvite1.setEmail("jane.doe@example.org");
		userInvite1.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite1.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite1.setToken("ABC123");
		userInvite1.setUserUUID(UUID.randomUUID());
		this.userInviteRepository.save(userInvite);
		this.userInviteRepository.save(userInvite1);

		// Act and Assert
		assertFalse(this.userInviteRepository.findByToken("foo").isPresent());
	}

	@Test
	void testUpdateConfirmedAt() {
		// Arrange
		UserInvite userInvite = new UserInvite();
		userInvite.setOrganizationUUID(UUID.randomUUID());
		userInvite.setEmail("jane.doe@example.org");
		userInvite.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite.setToken("ABC123");
		userInvite.setUserUUID(UUID.randomUUID());

		UserInvite userInvite1 = new UserInvite();
		userInvite1.setOrganizationUUID(UUID.randomUUID());
		userInvite1.setEmail("jane.doe@example.org");
		userInvite1.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite1.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
		userInvite1.setToken("ABC123");
		userInvite1.setUserUUID(UUID.randomUUID());
		this.userInviteRepository.save(userInvite);
		this.userInviteRepository.save(userInvite1);

		// Act and Assert
		assertEquals(
			0,
			this.userInviteRepository.updateConfirmedAt(
					"foo",
					LocalDateTime.of(1, 1, 1, 1, 1)
				)
		);
	}
}
