package com.ravel.backend.organization.model;

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
public class OrganizationInvite {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false, length = 36)
	private String token;

	private UUID userUUID;

	private UUID organizationUUID;

	@Column(nullable = false)
	private OffsetDateTime createdAt;

	@Column(nullable = false)
	private OffsetDateTime expiresAt;

	private OffsetDateTime confirmedAt;

	public OrganizationInvite(
		String token,
		UUID userUUID,
		UUID organizationUUID,
		OffsetDateTime createdAt,
		OffsetDateTime expiresAt
	) {
		this.token = token;
		this.userUUID = userUUID;
		this.organizationUUID = organizationUUID;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
}
