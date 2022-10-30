package com.ravel.backend.organization.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "OrganizationActiveGetDto")
public class OrganizationActiveGetDto {

	private UUID organizationId;
	private String organizationName;
	private boolean isActive;
}
