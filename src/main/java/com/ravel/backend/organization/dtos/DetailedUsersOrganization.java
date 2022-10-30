package com.ravel.backend.organization.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Data
@Schema(name = "DetailedUsersOrganization")
public class DetailedUsersOrganization {

	//User
	private UUID userUUID;
	private String firstName;
	private String lastName;
	private String email;
	private String profileImageUrl;

	//OrganizationUser
	private OffsetDateTime joinedAt;
	private String organizationRole;
	private boolean isActiveUser;

	public DetailedUsersOrganization(
		UUID userUUID,
		String firstName,
		String lastName,
		String email,
		String profileImageUrl,
		OffsetDateTime joinedAt,
		String organizationRole,
		boolean isActiveUser
	) {
		this.userUUID = userUUID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.joinedAt = joinedAt;
		this.organizationRole = organizationRole;
		this.isActiveUser = isActiveUser;
	}

	public UUID getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(UUID userUUID) {
		this.userUUID = userUUID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public OffsetDateTime getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(OffsetDateTime joinedAt) {
		this.joinedAt = joinedAt;
	}

	public String getOrganizationRole() {
		return organizationRole;
	}

	public void setOrganizationRole(String organizationRole) {
		this.organizationRole = organizationRole;
	}

	public boolean getIsActiveUser() {
		return isActiveUser;
	}

	public void setActiveUser(boolean activeUser) {
		isActiveUser = activeUser;
	}

	@Override
	public String toString() {
		return (
			"DetailedUsersOrganization{" +
			"userUUID=" +
			userUUID +
			", firstName='" +
			firstName +
			'\'' +
			", lastName='" +
			lastName +
			'\'' +
			", email='" +
			email +
			'\'' +
			", profileImageUrl='" +
			profileImageUrl +
			'\'' +
			", joinedAt=" +
			joinedAt +
			", organizationRole='" +
			organizationRole +
			'\'' +
			", isActiveUser=" +
			isActiveUser +
			'}'
		);
	}
}
