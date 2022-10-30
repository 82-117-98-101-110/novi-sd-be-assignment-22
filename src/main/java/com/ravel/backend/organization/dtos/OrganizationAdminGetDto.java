package com.ravel.backend.organization.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "OrganizationAdminGetDto")
public class OrganizationAdminGetDto {

	private UUID organizationId;
	private String organizationName;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private boolean isActive;
}
