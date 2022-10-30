package com.ravel.backend.security.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class AuthCode {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false, length = 36)
	private String code;

	@Column(nullable = false)
	private OffsetDateTime createdAt;

	@Column(nullable = false)
	private OffsetDateTime expiresAt;

	private OffsetDateTime verifiedAt;
	private boolean isVerified;
	private boolean isActive;
	private UUID userUUID;
	private String ipRequest;
	private String ipAuthenticate;

	public AuthCode(String code, OffsetDateTime createdAt, OffsetDateTime expiresAt) {
		this.code = code;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
}
