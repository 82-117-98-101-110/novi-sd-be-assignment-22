package com.ravel.backend.appAuth.controller;

import com.ravel.backend.appAuth.dto.RoleGetDto;
import com.ravel.backend.appAuth.dto.RolePermissionGetDto;
import com.ravel.backend.appAuth.service.AppRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "api/v1/appauth/roles")
@RestController
@Tag(
	name = "Application Roles & Permissions",
	description = "Separate Authorization module for Unity. Creating roles, permissions and assigning these to users"
)
@SecurityRequirement(name = "bearerAuth")
public class AppRoleController {

	private AppRoleService appRoleService;

	@Autowired
	public AppRoleController(AppRoleService appRoleService) {
		this.appRoleService = appRoleService;
	}

	@Operation(
		summary = "Get all application roles",
		description = "This endpoint wil return all application roles stored in the database"
	)
	@ApiResponse(responseCode = "200", description = "Roles found")
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<RoleGetDto>> getAllRoles() {
		return ResponseEntity.ok().body(appRoleService.getAllAppRole());
	}

	@Operation(
		summary = "Get all application roles with permissions",
		description = "Get all application roles with their application permissions"
	)
	@ApiResponse(responseCode = "200", description = "Roles found")
	@GetMapping(value = "/permissions", produces = "application/json")
	public ResponseEntity<List<RolePermissionGetDto>> getAllAppRolesWithPermissions() {
		return ResponseEntity.ok().body(appRoleService.getAllAppRolesWithPermissions());
	}

	@Operation(
		summary = "Get details for given role by application name",
		description = "This endpoint wil return the details for a given application role name\n Following data will be included:\n <br/>  \n * Role Name\n * Role Description\n * Permissions assigned to role\n * Description of the permission"
	)
	@ApiResponse(responseCode = "200", description = "Roles with details found")
	@GetMapping(value = "/{appRoleName}", produces = "application/json")
	public ResponseEntity<RoleGetDto> getAppRoleByName(
		@Parameter(description = "App Role Name", example = "Presenter") @PathVariable(
			"appRoleName"
		) String appRoleName
	) {
		return ResponseEntity.ok().body(appRoleService.getAppRole(appRoleName));
	}
}
