package com.ravel.backend.users.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class ResetPassword {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 36)
	private String token;

	private UUID userUUID;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime expiresAt;

	private LocalDateTime confirmedAt;

	@Column(length = 100)
	@Email
	private String email;

	public ResetPassword(
		String token,
		UUID userUUID,
		LocalDateTime createdAt,
		LocalDateTime expiresAt
	) {
		this.token = token;
		this.userUUID = userUUID;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
}
