package com.ravel.backend.spacePro.dto;

import com.ravel.backend.spacePro.enumeration.SpaceType;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpaceProGetDto implements Serializable {

	private final OffsetDateTime created_at;
	private final OffsetDateTime updatedAt;
	private final boolean isActive;
	private final UUID spaceUuid;

	@NotNull
	private final String spaceName;

	private final String description;
	private final boolean codeProtected;
	private final SpaceType spaceType;
}
