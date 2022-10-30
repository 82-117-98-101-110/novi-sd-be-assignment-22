package com.ravel.backend.spacePro.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnvironmentProPostDto implements Serializable {

	@NotNull
	private final String name;

	private final String description;
	private final boolean isPublicEnvironment;
	private final String unitySceneName;
}
