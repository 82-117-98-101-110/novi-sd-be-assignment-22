package com.ravel.backend.appAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "RolePostDto")
public class RolePostDto {

	@Schema(
		type = "string",
		description = "Application Role name",
		example = "ORGANIZATION_GUEST",
		required = true
	)
	private String appRoleName;

	@Schema(
		type = "string",
		description = "Application Role Description",
		example = "This role is for users that are a Guest of an organization",
		required = true
	)
	private String description;

	@Schema(
		type = "string",
		description = "Application Role name",
		example = "ORGANIZATION",
		required = true
	)
	private String purpose;
}
