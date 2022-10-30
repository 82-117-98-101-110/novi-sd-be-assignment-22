package com.ravel.backend.appAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "RolePermissionGetDto")
public class RolePermissionGetDto {

	private String appRoleName;
	private String description;
	private String purpose;
	private Set<PermissionGetDto> appPermissions;
}
