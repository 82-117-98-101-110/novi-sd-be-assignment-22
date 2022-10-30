package com.ravel.backend.appAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "PermissionRoleGetDto")
public class PermissionRoleGetDto {

	private String appPermissionName;
	private String description;
	private Set<RoleGetDto> appRoles;
}
