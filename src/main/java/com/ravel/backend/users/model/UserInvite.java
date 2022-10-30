package com.ravel.backend.users.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class UserInvite {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 36)
	private String token;

	private UUID userUUID;

	private UUID organizationUUID;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime expiresAt;

	private LocalDateTime confirmedAt;

	@Column(length = 100)
	@Email
	private String email;

	public UserInvite(
		String token,
		UUID userUUID,
		UUID organizationUUID,
		LocalDateTime createdAt,
		LocalDateTime expiresAt,
		String email
	) {
		this.token = token;
		this.userUUID = userUUID;
		this.organizationUUID = organizationUUID;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.email = email;
	}
}
