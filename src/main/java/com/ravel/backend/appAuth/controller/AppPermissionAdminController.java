package com.ravel.backend.appAuth.controller;

import com.ravel.backend.appAuth.dto.PermissionPostDto;
import com.ravel.backend.appAuth.model.AppPermission;
import com.ravel.backend.appAuth.service.AppPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/v1/admin/appauth/permission")
@RestController
@Tag(
	name = "Application Roles & Permissions Admin",
	description = "Separate Authorization module for Unity application focussing on permissions for Organizations, Spaces(Rooms), Groups. Creating roles, permissions and assigning these to users"
)
@SecurityRequirement(name = "bearerAuth")
public class AppPermissionAdminController {

	private final AppPermissionService appPermissionService;

	@Autowired
	public AppPermissionAdminController(AppPermissionService appPermissionService) {
		this.appPermissionService = appPermissionService;
	}

	@PreAuthorize("hasAuthority('dev:access')")
	@PostMapping(value = "", consumes = "application/json", produces = "application/json")
	@Operation(
		summary = "Create new permission",
		description = "Create a new application permission. These permissions can be assigned to a role with the: add permission to role \n \n **For this endpoint the user needs the dev:access authority**"
	)
	@ApiResponse(
		responseCode = "201",
		description = "New permission created",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = PermissionPostDto.class)
			),
		}
	)
	public ResponseEntity<Void> createNewPermission(
		@RequestBody PermissionPostDto permissionPostDto
	) {
		appPermissionService.createNewAppPermission(permissionPostDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(
		summary = "Add application Permission to an application Role",
		description = "This service wil add a application permission to an application role \n \n **For this endpoint the user needs the dev:access authority**"
	)
	@ApiResponse(responseCode = "202", description = "Permission added to Role")
	@PreAuthorize("hasAuthority('dev:access')")
	@PostMapping(
		value = "/{appRoleName}/permissions/{appPermissionName}",
		produces = "application/json"
	)
	public ResponseEntity<AppPermission> addPermissionToRole(
		@Parameter(
			description = "Role Name",
			example = "Presentor"
		) @PathVariable String appRoleName,
		@Parameter(
			description = "Name of Permission",
			example = "spawn-testplayer"
		) @PathVariable String appPermissionName
	) {
		return ResponseEntity
			.accepted()
			.body(
				appPermissionService.addRoleToPermission(appPermissionName, appRoleName)
			);
	}

	@Operation(
		summary = "Update an existing application permission",
		description = "Please provide the permission name of an existing permission as the path. Then give the permission a new name and description in de body  \n \n **For this endpoint the user needs the dev:access authority**"
	)
	@PreAuthorize("hasAuthority('dev:access')")
	@ApiResponse(responseCode = "201", description = "Permission updated")
	@PutMapping(
		value = "/{appPermissionName}",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Void> updateAppPermission(
		@Parameter(
			description = "Permission Name",
			example = "spawn-testplayer"
		) @PathVariable(name = "appPermissionName") String appPermissionName,
		@Valid @RequestBody PermissionPostDto permissionPostDto
	) {
		appPermissionService.updatePermission(appPermissionName, permissionPostDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
