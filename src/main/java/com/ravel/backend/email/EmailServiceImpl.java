package com.ravel.backend.email;

import com.ravel.backend.shared.exception.EmailNotSendException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	private JavaMailSender mailSender;
	private SpringTemplateEngine templateEngine;

	@Override
	@Async
	public void inviteNewUserToOrganizationEmail(
		String toEmailAddress,
		String organizationName,
		String link
	) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(
				mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name()
			);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("organization", organizationName);
			model.put("link", link);

			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process("create-account-org-invite", context);
			helper.setText(html, true);
			helper.setTo(toEmailAddress);
			helper.setSubject("You're invited to sign-up for Ravel");
			helper.setFrom("Ravel");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.error("failed to send email", e);
			throw new EmailNotSendException("failed to send email");
		}
	}

	@Override
	@Async
	public void inviteUserToOrganizationEmail(
		String toEmailAddress,
		String organizationName,
		String link
	) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(
				mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name()
			);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("organization", organizationName);
			model.put("link", link);

			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process("accept-org-invite", context);
			helper.setText(html, true);
			helper.setTo(toEmailAddress);
			helper.setSubject("You joined the organization:  " + organizationName);
			helper.setFrom("Ravel");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.error("failed to send email", e);
			throw new EmailNotSendException("failed to send email");
		}
	}

	@Override
	@Async
	public void resetPasswordEmail(
		String toEmailAddress,
		String userFirstname,
		String link
	) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(
				mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name()
			);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("firstname", userFirstname);
			model.put("link", link);

			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process("reset-password", context);
			helper.setText(html, true);
			helper.setTo(toEmailAddress);
			helper.setSubject("Password Reset");
			helper.setFrom("Ravel");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.error("failed to send email", e);
			throw new EmailNotSendException("failed to send email");
		}
	}
}
