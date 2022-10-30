package com.ravel.backend.organization.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "OrganizationUserRoleOnlyGetDto")
public class OrganizationUserRoleOnlyGetDto {

	private String organizationRole;
}
