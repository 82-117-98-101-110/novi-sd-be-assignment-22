package com.ravel.backend.appAuth.controller;

import com.ravel.backend.appAuth.dto.RolePostDto;
import com.ravel.backend.appAuth.service.AppRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/v1/admin/appauth/approle")
@RestController
@Tag(
	name = "Application Roles & Permissions Admin",
	description = "Separate Authorization module for Unity. Creating roles, permissions and assigning these to users"
)
@SecurityRequirement(name = "bearerAuth")
public class AppRoleAdminController {

	private AppRoleService appRoleService;

	@Autowired
	public AppRoleAdminController(AppRoleService appRoleService) {
		this.appRoleService = appRoleService;
	}

	@Operation(
		summary = "Create a new application role",
		description = "This endpoint will create a new application role, please provide a valid purpose choosing between ORGANIZATION, ROOM or GROUP  \n \n **For this endpoint the user needs the dev:access authority**"
	)
	@ApiResponse(responseCode = "201", description = "Role created")
	@PreAuthorize("hasAuthority('super_admin:access')")
	@PostMapping(value = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Void> createNewRole(@RequestBody RolePostDto rolePostDto) {
		appRoleService.createNewAppRole(rolePostDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(
		summary = "Update an existing application role",
		description = "Please provide the application role name of an existing application role as the path."
	)
	@ApiResponse(responseCode = "202", description = "Role updated")
	@PreAuthorize("hasAuthority('super_admin:access')")
	@PutMapping(
		value = "/{appRoleName}",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Void> updateAppRole(
		@Parameter(
			description = "Application Role Name",
			example = "Presenter"
		) @PathVariable(name = "appRoleName") String appRoleName,
		@Valid @RequestBody RolePostDto rolePostDto
	) {
		appRoleService.updateRole(appRoleName, rolePostDto);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
