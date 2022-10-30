package com.ravel.backend.appAuth.controller;

import com.ravel.backend.appAuth.dto.PermissionGetDto;
import com.ravel.backend.appAuth.dto.PermissionRoleGetDto;
import com.ravel.backend.appAuth.service.AppPermissionService;
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

@RequestMapping(path = "api/v1/appauth/permissions")
@RestController
@Tag(
	name = "Application Roles & Permissions",
	description = "Separate Authorization module for Unity application. Creating roles, permissions and assigning these to users"
)
@SecurityRequirement(name = "bearerAuth")
public class AppPermissionController {

	private final AppPermissionService appPermissionService;

	@Autowired
	public AppPermissionController(AppPermissionService appPermissionService) {
		this.appPermissionService = appPermissionService;
	}

	@Operation(
		summary = "Get application permission with name",
		description = "Get application permission details with a given name"
	)
	@ApiResponse(responseCode = "200", description = "Permission found")
	@GetMapping(value = "/{permissionName}", produces = "application/json")
	public ResponseEntity<PermissionGetDto> getAppPermissionByName(
		@Parameter(
			description = "App Permission Name",
			example = "spawn-testplayer"
		) @PathVariable("permissionName") String permissionName
	) {
		return ResponseEntity
			.ok()
			.body(appPermissionService.getAppPermission(permissionName));
	}

	@GetMapping(produces = "application/json")
	@Operation(
		summary = "Get all application permissions",
		description = "Returns all application permissions"
	)
	@ApiResponse(responseCode = "200", description = "Permissions found")
	public ResponseEntity<List<PermissionGetDto>> getAllPermissions() {
		return ResponseEntity.ok().body(appPermissionService.getAllAppPermissions());
	}

	@GetMapping(value = "/roles", produces = "application/json")
	@Operation(
		summary = "Get all application permissions with roles relations",
		description = "Returns all application permissions and the application roles they are related to"
	)
	@ApiResponse(responseCode = "200", description = "Permissions with roles found")
	public ResponseEntity<List<PermissionRoleGetDto>> getAllAppPermissionsWithPermissions() {
		return ResponseEntity
			.ok()
			.body(appPermissionService.getAllAppPermissionsWithPermissions());
	}
}
