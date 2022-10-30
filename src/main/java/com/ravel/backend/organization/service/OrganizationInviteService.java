package com.ravel.backend.organization.service;

import com.ravel.backend.email.EmailService;
import com.ravel.backend.organization.model.OrganizationInvite;
import com.ravel.backend.organization.model.OrganizationUserRoleId;
import com.ravel.backend.organization.repository.OrganizationInviteRepository;
import com.ravel.backend.shared.EnvironmentProperties;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.InviteException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationInviteService {

	private final OrganizationInviteRepository organizationInviteRepository;
	private EmailService emailService;
	private EnvironmentProperties environmentProperties;

	public void newOrganizationInvite(
		String userEmail,
		String organizationName,
		UUID organizationUUID,
		UUID userUUID
	) {
//		String token = UUID.randomUUID().toString();
//		OrganizationInvite newOrganizationInvite = new OrganizationInvite(
//			token,
//			userUUID,
//			organizationUUID,
//			OffsetDateTime.now(),
//			OffsetDateTime.now().plusHours(48)
//		);
//		organizationInviteRepository.save(newOrganizationInvite);
		String signInLink = environmentProperties.getFrontendWebURL();
		emailService.inviteUserToOrganizationEmail(
			userEmail,
			organizationName,
			signInLink
		);
	}

	//TODO what if user was part of the organization before, gets added again and invite is resent. Then we have double values of user UUID and organization UUID and repository will return multible values.
	public String resendOrganizationInviteExistingUser(
		String userEmail,
		String organizationName,
		UUID organizationUUID,
		UUID userUUID
	) {
		OrganizationInvite currentInvite = organizationInviteRepository
			.findByUserUUIDAndOrganizationUUID(userUUID, organizationUUID)
			.orElseThrow(() -> new NotFoundException("Invite not found"));
		if (currentInvite.getConfirmedAt() != null) {
			throw new AlreadyExistException(
				"User already accepted the invite, please remove user from organization"
			);
		}
		String token = UUID.randomUUID().toString();
		currentInvite.setExpiresAt(OffsetDateTime.now().plusHours(336));
		currentInvite.setToken(token);
		organizationInviteRepository.save(currentInvite);
		String activationLink =
			environmentProperties.getFrontendWebURL() + "/join-organisation/" + token;
		emailService.inviteUserToOrganizationEmail(
			userEmail,
			organizationName,
			activationLink
		);
		return token;
	}

	public OrganizationUserRoleId confirmToken(String token) {
		OrganizationInvite organizationInvite = organizationInviteRepository
			.findByToken(token)
			.orElseThrow(() -> new NotFoundException("Token not found"));
		if (organizationInvite.getConfirmedAt() != null) {
			throw new InviteException("Invite already confirmed");
		}

		//		OffsetDateTime expiredAt = organizationInvite.getExpiresAt();
		//		if (expiredAt.isBefore(OffsetDateTime.now())) {
		//			throw new InviteException("Token expired");
		//		}
		setConfirmedAt(token);

		OrganizationUserRoleId id = new OrganizationUserRoleId(
			organizationInvite.getOrganizationUUID(),
			organizationInvite.getUserUUID()
		);
		return id;
	}

	private int setConfirmedAt(String token) {
		return organizationInviteRepository.updateConfirmedAt(
			token,
			OffsetDateTime.now()
		);
	}
}
