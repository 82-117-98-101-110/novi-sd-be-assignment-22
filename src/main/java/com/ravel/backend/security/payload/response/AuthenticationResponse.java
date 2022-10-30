package com.ravel.backend.security.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
public class AuthenticationResponse {

	private UUID userUUID;
	private String firstName;
	private String lastName;
	private String email;
	private String avatarUrl;
	private String avatarUrlFullBody;
	private String profileImageUrl;
	private String systemRole;
	private String[] systemAuthorities;
	private String accessToken;

	public AuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public AuthenticationResponse(
		UUID userUUID,
		String firstName,
		String lastName,
		String email,
		String avatarUrl,
		String avatarUrlFullBody,
		String profileImageUrl,
		String systemRole,
		String[] systemAuthorities,
		String accessToken
	) {
		this.userUUID = userUUID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.avatarUrl = avatarUrl;
		this.avatarUrlFullBody = avatarUrlFullBody;
		this.profileImageUrl = profileImageUrl;
		this.systemRole = systemRole;
		this.systemAuthorities = systemAuthorities;
		this.accessToken = accessToken;
	}
}
