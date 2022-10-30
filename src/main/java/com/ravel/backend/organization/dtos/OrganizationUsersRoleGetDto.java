package com.ravel.backend.organization.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "OrganizationUsersRoleGetDto")
public class OrganizationUsersRoleGetDto {

	private OffsetDateTime joinedAt;
	private boolean isActiveUser;
	private String organizationRole;
	private GetOrganizationsForUserDto organization;
}
