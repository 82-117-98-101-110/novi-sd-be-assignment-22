package com.ravel.backend.email;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

	@Async
	void inviteNewUserToOrganizationEmail(
		String toEmailAddress,
		String organizationName,
		String link
	);

	@Async
	void inviteUserToOrganizationEmail(
		String toEmailAddress,
		String organizationName,
		String link
	);

	@Async
	void resetPasswordEmail(String toEmailAddress, String userFirstname, String link);
}
