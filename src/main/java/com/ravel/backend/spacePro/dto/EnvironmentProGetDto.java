package com.ravel.backend.spacePro.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class EnvironmentProGetDto implements Serializable {

	private final OffsetDateTime created_at;
	private final OffsetDateTime updatedAt;
	private final boolean isActive;
	private final UUID environmentUuid;

	@NotNull
	private final String name;

	private final String description;

	@URL
	private final String imageUrl;

	private final boolean isPublicEnvironment;
	private final String unitySceneName;
	private String assetBundleUrl;
}
