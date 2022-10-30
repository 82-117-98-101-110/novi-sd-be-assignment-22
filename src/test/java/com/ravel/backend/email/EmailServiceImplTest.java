package com.ravel.backend.email;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.shared.exception.EmailNotSendException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.spring5.SpringTemplateEngine;

@ContextConfiguration(classes = { EmailServiceImpl.class, SpringTemplateEngine.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class EmailServiceImplTest {

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@MockBean
	private JavaMailSender javaMailSender;

	@Test
	void testInviteNewUserToOrganizationEmail() {
		// Arrange
		when(this.javaMailSender.createMimeMessage())
			.thenReturn(new MimeMessage((Session) null));

		// Act and Assert
		assertThrows(
			EmailNotSendException.class,
			() ->
				this.emailServiceImpl.inviteNewUserToOrganizationEmail(
						"42 Main St",
						"Organization Name",
						"Link"
					)
		);
		verify(this.javaMailSender).createMimeMessage();
	}

	@Test
	void testInviteNewUserToOrganizationEmail2() {
		// Arrange
		when(this.javaMailSender.createMimeMessage())
			.thenThrow(new EmailNotSendException("An error occurred"));

		// Act and Assert
		assertThrows(
			EmailNotSendException.class,
			() ->
				this.emailServiceImpl.inviteNewUserToOrganizationEmail(
						"42 Main St",
						"Organization Name",
						"Link"
					)
		);
		verify(this.javaMailSender).createMimeMessage();
	}

	@Test
	void testInviteUserToOrganizationEmail() {
		// Arrange
		when(this.javaMailSender.createMimeMessage())
			.thenReturn(new MimeMessage((Session) null));

		// Act and Assert
		assertThrows(
			EmailNotSendException.class,
			() ->
				this.emailServiceImpl.inviteUserToOrganizationEmail(
						"42 Main St",
						"Organization Name",
						"Link"
					)
		);
		verify(this.javaMailSender).createMimeMessage();
	}

	@Test
	void testInviteUserToOrganizationEmail2() {
		// Arrange
		when(this.javaMailSender.createMimeMessage())
			.thenThrow(new EmailNotSendException("An error occurred"));

		// Act and Assert
		assertThrows(
			EmailNotSendException.class,
			() ->
				this.emailServiceImpl.inviteUserToOrganizationEmail(
						"42 Main St",
						"Organization Name",
						"Link"
					)
		);
		verify(this.javaMailSender).createMimeMessage();
	}

	@Test
	void testInviteUserToOrganizationEmail3() throws MailException {
		// Arrange
		doNothing().when(this.javaMailSender).send((MimeMessage) any());
		when(this.javaMailSender.createMimeMessage())
			.thenReturn(new MimeMessage((Session) null));

		// Act
		this.emailServiceImpl.inviteUserToOrganizationEmail(
				"jane.doe@example.org",
				"Organization Name",
				"Link"
			);

		// Assert
		verify(this.javaMailSender).createMimeMessage();
		verify(this.javaMailSender).send((MimeMessage) any());
	}

	@Test
	void testInviteUserToOrganizationEmail4() throws MailException {
		// Arrange
		doThrow(new EmailNotSendException("An error occurred"))
			.when(this.javaMailSender)
			.send((MimeMessage) any());
		when(this.javaMailSender.createMimeMessage())
			.thenReturn(new MimeMessage((Session) null));

		// Act and Assert
		assertThrows(
			EmailNotSendException.class,
			() ->
				this.emailServiceImpl.inviteUserToOrganizationEmail(
						"jane.doe@example.org",
						"Organization Name",
						"Link"
					)
		);
		verify(this.javaMailSender).createMimeMessage();
		verify(this.javaMailSender).send((MimeMessage) any());
	}

	@Test
	void testResetPasswordEmail() {
		// Arrange
		when(this.javaMailSender.createMimeMessage())
			.thenReturn(new MimeMessage((Session) null));

		// Act and Assert
		assertThrows(
			EmailNotSendException.class,
			() -> this.emailServiceImpl.resetPasswordEmail("42 Main St", "Jane", "Link")
		);
		verify(this.javaMailSender).createMimeMessage();
	}

	@Test
	void testResetPasswordEmail2() {
		// Arrange
		when(this.javaMailSender.createMimeMessage())
			.thenThrow(new EmailNotSendException("An error occurred"));

		// Act and Assert
		assertThrows(
			EmailNotSendException.class,
			() -> this.emailServiceImpl.resetPasswordEmail("42 Main St", "Jane", "Link")
		);
		verify(this.javaMailSender).createMimeMessage();
	}

	@Test
	void testResetPasswordEmail3() throws MailException {
		// Arrange
		doNothing().when(this.javaMailSender).send((MimeMessage) any());
		when(this.javaMailSender.createMimeMessage())
			.thenReturn(new MimeMessage((Session) null));

		// Act
		this.emailServiceImpl.resetPasswordEmail("jane.doe@example.org", "Jane", "Link");

		// Assert
		verify(this.javaMailSender).createMimeMessage();
		verify(this.javaMailSender).send((MimeMessage) any());
	}

	@Test
	void testResetPasswordEmail4() throws MailException {
		// Arrange
		doThrow(new EmailNotSendException("An error occurred"))
			.when(this.javaMailSender)
			.send((MimeMessage) any());
		when(this.javaMailSender.createMimeMessage())
			.thenReturn(new MimeMessage((Session) null));

		// Act and Assert
		assertThrows(
			EmailNotSendException.class,
			() ->
				this.emailServiceImpl.resetPasswordEmail(
						"jane.doe@example.org",
						"Jane",
						"Link"
					)
		);
		verify(this.javaMailSender).createMimeMessage();
		verify(this.javaMailSender).send((MimeMessage) any());
	}
}
