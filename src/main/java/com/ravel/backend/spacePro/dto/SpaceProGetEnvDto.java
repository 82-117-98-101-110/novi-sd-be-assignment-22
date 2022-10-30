package com.ravel.backend.spacePro.dto;

import com.ravel.backend.persistence.dto.PhotonRoomUserDto;
import com.ravel.backend.spacePro.enumeration.SpaceType;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SpaceProGetEnvDto implements Serializable {

	private OffsetDateTime created_at;
	private  OffsetDateTime updatedAt;
	private boolean isActive;
	private UUID spaceUuid;

	@NotNull
	private final String spaceName;

	private String description;
	private boolean codeProtected;
	private SpaceType spaceType;
	private  EnvironmentProGetDto environmentPro;
	private  Set<SpaceRoleGetDto> defaultSpaceRoles;
	private String spaceUrl;
	private boolean roomIsOnline;
	private String photonRoomId;
	private String sessionSpaceId;

	private List<PhotonRoomUserDto> photonRoomUserDtoList;

	public OffsetDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(OffsetDateTime created_at) {
		this.created_at = created_at;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public UUID getSpaceUuid() {
		return spaceUuid;
	}

	public void setSpaceUuid(UUID spaceUuid) {
		this.spaceUuid = spaceUuid;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCodeProtected() {
		return codeProtected;
	}

	public void setCodeProtected(boolean codeProtected) {
		this.codeProtected = codeProtected;
	}

	public SpaceType getSpaceType() {
		return spaceType;
	}

	public void setSpaceType(SpaceType spaceType) {
		this.spaceType = spaceType;
	}

	public EnvironmentProGetDto getEnvironmentPro() {
		return environmentPro;
	}

	public void setEnvironmentPro(EnvironmentProGetDto environmentPro) {
		this.environmentPro = environmentPro;
	}

	public Set<SpaceRoleGetDto> getDefaultSpaceRoles() {
		return defaultSpaceRoles;
	}

	public void setDefaultSpaceRoles(Set<SpaceRoleGetDto> defaultSpaceRoles) {
		this.defaultSpaceRoles = defaultSpaceRoles;
	}

	public String getSpaceUrl() {
		return spaceUrl;
	}

	public void setSpaceUrl(String spaceUrl) {
		this.spaceUrl = spaceUrl;
	}

	public boolean isRoomIsOnline() {
		return roomIsOnline;
	}

	public void setRoomIsOnline(boolean roomIsOnline) {
		this.roomIsOnline = roomIsOnline;
	}

	public String getPhotonRoomId() {
		return photonRoomId;
	}

	public void setPhotonRoomId(String photonRoomId) {
		this.photonRoomId = photonRoomId;
	}

	public SpaceProGetEnvDto(OffsetDateTime created_at, OffsetDateTime updatedAt, boolean isActive, UUID spaceUuid, String spaceName, String description, boolean codeProtected, SpaceType spaceType, EnvironmentProGetDto environmentPro, Set<SpaceRoleGetDto> defaultSpaceRoles, String spaceUrl, boolean roomIsOnline, String photonRoomId) {
		this.created_at = created_at;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
		this.spaceUuid = spaceUuid;
		this.spaceName = spaceName;
		this.description = description;
		this.codeProtected = codeProtected;
		this.spaceType = spaceType;
		this.environmentPro = environmentPro;
		this.defaultSpaceRoles = defaultSpaceRoles;
		this.spaceUrl = spaceUrl;
		this.roomIsOnline = roomIsOnline;
		this.photonRoomId = photonRoomId;
	}

	@Override
	public String toString() {
		return "SpaceProGetEnvDto{" +
				"created_at=" + created_at +
				", updatedAt=" + updatedAt +
				", isActive=" + isActive +
				", spaceUuid=" + spaceUuid +
				", spaceName='" + spaceName + '\'' +
				", description='" + description + '\'' +
				", codeProtected=" + codeProtected +
				", spaceType=" + spaceType +
				", environmentPro=" + environmentPro +
				", defaultSpaceRoles=" + defaultSpaceRoles +
				", spaceUrl='" + spaceUrl + '\'' +
				", roomIsOnline=" + roomIsOnline +
				", photonRoomId='" + photonRoomId + '\'' +
				'}';
	}
}
