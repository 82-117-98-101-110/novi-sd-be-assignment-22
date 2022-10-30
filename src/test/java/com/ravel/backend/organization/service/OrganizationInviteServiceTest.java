package com.ravel.backend.organization.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.email.EmailService;
import com.ravel.backend.organization.model.OrganizationInvite;
import com.ravel.backend.organization.repository.OrganizationInviteRepository;
import com.ravel.backend.shared.EnvironmentProperties;
import com.ravel.backend.shared.exception.NotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
	classes = { OrganizationInviteService.class, EnvironmentProperties.class }
)
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class OrganizationInviteServiceTest {

	@MockBean
	private EmailService emailService;

	@MockBean
	private OrganizationInviteRepository organizationInviteRepository;

	@Autowired
	private OrganizationInviteService organizationInviteService;

	@Test
	void testResendOrganizationInviteExistingUser() {
		// Arrange
		OrganizationInvite organizationInvite = new OrganizationInvite();
		organizationInvite.setConfirmedAt(null);
		organizationInvite.setCreatedAt(null);
		organizationInvite.setExpiresAt(null);
		organizationInvite.setOrganizationUUID(UUID.randomUUID());
		organizationInvite.setToken("ABC123");
		organizationInvite.setUserUUID(UUID.randomUUID());
		Optional<OrganizationInvite> ofResult = Optional.of(organizationInvite);

		OrganizationInvite organizationInvite1 = new OrganizationInvite();
		organizationInvite1.setConfirmedAt(null);
		organizationInvite1.setCreatedAt(null);
		organizationInvite1.setExpiresAt(null);
		organizationInvite1.setOrganizationUUID(UUID.randomUUID());
		organizationInvite1.setToken("ABC123");
		organizationInvite1.setUserUUID(UUID.randomUUID());
		when(this.organizationInviteRepository.save((OrganizationInvite) any()))
			.thenReturn(organizationInvite1);
		when(
			this.organizationInviteRepository.findByUserUUIDAndOrganizationUUID(
					(UUID) any(),
					(UUID) any()
				)
		)
			.thenReturn(ofResult);
		doNothing()
			.when(this.emailService)
			.inviteUserToOrganizationEmail(
				(String) any(),
				(String) any(),
				(String) any()
			);
		UUID organizationUUID = UUID.randomUUID();

		// Act
		this.organizationInviteService.resendOrganizationInviteExistingUser(
				"jane.doe@example.org",
				"Organization Name",
				organizationUUID,
				UUID.randomUUID()
			);

		// Assert
		verify(this.organizationInviteRepository)
			.findByUserUUIDAndOrganizationUUID((UUID) any(), (UUID) any());
		verify(this.organizationInviteRepository).save((OrganizationInvite) any());
		verify(this.emailService)
			.inviteUserToOrganizationEmail(
				(String) any(),
				(String) any(),
				(String) any()
			);
	}

	@Test
	void testResendOrganizationInviteExistingUser2() {
		// Arrange
		OrganizationInvite organizationInvite = new OrganizationInvite();
		organizationInvite.setConfirmedAt(null);
		organizationInvite.setCreatedAt(null);
		organizationInvite.setExpiresAt(null);
		organizationInvite.setOrganizationUUID(UUID.randomUUID());
		organizationInvite.setToken("ABC123");
		organizationInvite.setUserUUID(UUID.randomUUID());
		when(this.organizationInviteRepository.save((OrganizationInvite) any()))
			.thenReturn(organizationInvite);
		when(
			this.organizationInviteRepository.findByUserUUIDAndOrganizationUUID(
					(UUID) any(),
					(UUID) any()
				)
		)
			.thenReturn(Optional.empty());
		doNothing()
			.when(this.emailService)
			.inviteUserToOrganizationEmail(
				(String) any(),
				(String) any(),
				(String) any()
			);
		UUID organizationUUID = UUID.randomUUID();

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.organizationInviteService.resendOrganizationInviteExistingUser(
						"jane.doe@example.org",
						"Organization Name",
						organizationUUID,
						UUID.randomUUID()
					)
		);
		verify(this.organizationInviteRepository)
			.findByUserUUIDAndOrganizationUUID((UUID) any(), (UUID) any());
	}

	@Test
	void testConfirmToken() {
		// Arrange
		when(this.organizationInviteRepository.findByToken((String) any()))
			.thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.organizationInviteService.confirmToken("ABC123")
		);
		verify(this.organizationInviteRepository).findByToken((String) any());
	}
}
