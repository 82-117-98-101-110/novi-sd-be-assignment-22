package com.ravel.backend.users.service;

import com.ravel.backend.email.EmailService;
import com.ravel.backend.shared.EnvironmentProperties;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.model.ResetPassword;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.repository.ResetPasswordRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
@AllArgsConstructor
public class ResetPasswordService {

	@Autowired
	private final ResetPasswordRepository resetPasswordRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private EnvironmentProperties environmentProperties;

	public String newResetPasswordEmail(User user) {
		String token = UUID.randomUUID().toString();
		ResetPassword newResetPassword = new ResetPassword(
			token,
			user.getUserUUID(),
			LocalDateTime.now(),
			LocalDateTime.now().plusMinutes(5)
		);
		resetPasswordRepository.save(newResetPassword);
		String resetPasswordLink =
			environmentProperties.getFrontendWebURL() + "/reset-password/" + token;
		emailService.resetPasswordEmail(
			user.getEmail(),
			user.getFirstName(),
			resetPasswordLink
		);
		return token;
	}

	protected UUID confirmToken(String token) {
		ResetPassword resetPassword = resetPasswordRepository
			.findByToken(token)
			.orElseThrow(() -> new NotFoundException("Token not found"));
		if (resetPassword.getConfirmedAt() != null) {
			throw new NotFoundException("Token already used");
		}
		LocalDateTime expiredAt = resetPassword.getExpiresAt();
		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new NotFoundException("Token expired");
		}
		setConfirmedAt(token);
		return resetPassword.getUserUUID();
	}

	private int setConfirmedAt(String token) {
		return resetPasswordRepository.updateConfirmedAt(token, LocalDateTime.now());
	}
}
