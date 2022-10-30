package com.ravel.backend.organization.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrganizationUserRoleId implements Serializable {

	@Column(name = "organization_id")
	private UUID organizationId;

	@Column(name = "user_uuid")
	private UUID userUuid;

	public OrganizationUserRoleId() {}

	public OrganizationUserRoleId(UUID organizationId, UUID userUuid) {
		this.organizationId = organizationId;
		this.userUuid = userUuid;
	}

	public UUID getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(UUID organizationId) {
		this.organizationId = organizationId;
	}

	public UUID getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(UUID userUuid) {
		this.userUuid = userUuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrganizationUserRoleId that = (OrganizationUserRoleId) o;
		return (
			Objects.equals(organizationId, that.organizationId) &&
			Objects.equals(userUuid, that.userUuid)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(organizationId, userUuid);
	}
}
