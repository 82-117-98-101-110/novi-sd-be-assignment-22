package com.ravel.backend.organization.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "OrganizationPostDto")
public class OrganizationPostDto {

	@Schema(
		type = "string",
		description = "Organization name",
		example = "Ravel",
		required = true
	)
	@Size(min = 1, max = 50)
	@NotBlank
	private String organizationName;
}
