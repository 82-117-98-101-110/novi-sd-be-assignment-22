package com.ravel.backend.organization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OrganizationUserRole")
@Table(name = "organization_user_Role")
public class OrganizationUserRole {

	@EmbeddedId
	@JsonIgnore
	private OrganizationUserRoleId id;

	@ManyToOne
	@MapsId("organizationId")
	@JoinColumn(name = "organization_organization_id")
	private Organization organization;

	private UUID userUUID;

	@Column(length = 100)
	private String email;

	@Column(name = "joined_at")
	private OffsetDateTime joinedAt;

	@JsonIgnore
	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "organization_role", length = 50)
	private String organizationRole;

	@Column(name = "is_active_user")
	private boolean isActiveUser;

	public void setActiveUser(boolean activeUser) {
		isActiveUser = activeUser;
	}

	public boolean getIsActiveUser() {
		return isActiveUser;
	}

	public OrganizationUserRoleId getId() {
		return id;
	}

	public void setId(OrganizationUserRoleId id) {
		this.id = id;
	}

	public UUID getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(UUID userUUID) {
		this.userUUID = userUUID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public OffsetDateTime getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(OffsetDateTime joinedAt) {
		this.joinedAt = joinedAt;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getOrganizationRole() {
		return organizationRole;
	}

	public void setOrganizationRole(String organizationRole) {
		this.organizationRole = organizationRole;
	}

	public boolean isActiveUser() {
		return isActiveUser;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
