package com.ravel.backend.spacePro.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SpaceProUserId implements Serializable {

	@Column(name = "space_pro_id")
	private Long spaceProId;

	@Column(name = "user_uuid")
	private UUID userUuid;

	public SpaceProUserId() {}

	public SpaceProUserId(Long spaceProId, UUID userUuid) {
		this.spaceProId = spaceProId;
		this.userUuid = userUuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SpaceProUserId that = (SpaceProUserId) o;
		return (
			Objects.equals(spaceProId, that.spaceProId) &&
			Objects.equals(userUuid, that.userUuid)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(spaceProId, userUuid);
	}

	public Long getSpaceProId() {
		return spaceProId;
	}

	public void setSpaceProId(Long spaceProId) {
		this.spaceProId = spaceProId;
	}

	public UUID getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(UUID userUuid) {
		this.userUuid = userUuid;
	}
}
