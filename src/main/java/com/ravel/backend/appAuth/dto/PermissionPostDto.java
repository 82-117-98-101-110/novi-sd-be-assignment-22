package com.ravel.backend.appAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "PermissionPostDto")
public class PermissionPostDto {

	@Schema(
		type = "string",
		description = "Permission name",
		example = "spawn-testplayer",
		required = true
	)
	@Size(min = 1, max = 50)
	@NotBlank
	private String appPermissionName;

	@Schema(
		type = "string",
		description = "Short description for the permission",
		example = "This permissions allows to spawn testplayers in the game"
	)
	private String description;
}
