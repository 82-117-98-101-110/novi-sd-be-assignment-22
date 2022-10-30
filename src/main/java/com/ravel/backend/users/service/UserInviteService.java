package com.ravel.backend.users.service;

import com.ravel.backend.email.EmailService;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.shared.EnvironmentProperties;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.model.UserInvite;
import com.ravel.backend.users.repository.UserInviteRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@AllArgsConstructor
public class UserInviteService {

	private final UserInviteRepository userInviteRepository;

	private final OrganizationUserRoleService organizationUserRoleService;

	private EmailService emailService;

	private EnvironmentProperties environmentProperties;

	public List<UserInvite> userInviteList() {
		return userInviteRepository.findAll();
	}

	public String newUserInviteForOrganization(
		User user,
		UUID organizationId,
		String organizationName
	) {
		String token = UUID.randomUUID().toString();
		UserInvite newUserInvite = new UserInvite(
			token,
			user.getUserUUID(),
			organizationId,
			LocalDateTime.now(),
			LocalDateTime.now().plusHours(336),
			user.getEmail()
		);
		userInviteRepository.save(newUserInvite);
		String activationLink =
			environmentProperties.getFrontendWebURL() + "/create-account/" + token;
		emailService.inviteNewUserToOrganizationEmail(
			user.getEmail(),
			organizationName,
			activationLink
		);
		return token;
	}

	protected UUID confirmToken(String token) {
		UserInvite userInvite = userInviteRepository
			.findByToken(token)
			.orElseThrow(() -> new NotFoundException("Token not found"));
		if (userInvite.getConfirmedAt() != null) {
			throw new NotFoundException("Invite already confirmed");
		}
		//		LocalDateTime expiredAt = userInvite.getExpiresAt();
		//		if (expiredAt.isBefore(LocalDateTime.now())) {
		//			throw new NotFoundException("Token expired");
		//		}
		setConfirmedAt(token);
		organizationUserRoleService.setNewInvitedUserActive(
			userInvite.getOrganizationUUID(),
			userInvite.getUserUUID()
		);
		return userInvite.getUserUUID();
	}

	private int setConfirmedAt(String token) {
		return userInviteRepository.updateConfirmedAt(token, LocalDateTime.now());
	}
}
