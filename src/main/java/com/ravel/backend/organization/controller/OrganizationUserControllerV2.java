package com.ravel.backend.organization.controller;

import com.ravel.backend.organization.dtos.OrganizationUsersRoleGetDto;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "Organizations",
	description = "Manage organizations & assigning users to organizations"
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v2/organizations")
@RestController
public class OrganizationUserControllerV2 {

	private OrganizationUserRoleService organizationUserRoleService;

	@Autowired
	public OrganizationUserControllerV2(
		OrganizationUserRoleService organizationUserRoleService
	) {
		this.organizationUserRoleService = organizationUserRoleService;
	}

	@Operation(
		summary = "Get organizations of user with organization details",
		description = "By providing the userUuid, it wil return a list of organizations for the user including the role and joinedAt for each organization"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Organizations found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = OrganizationUsersRoleGetDto.class)
			),
		}
	)
	@GetMapping(
		value = "/organizationsdetailed/{userUuid}",
		produces = "application/json"
	)
	public ResponseEntity<List<OrganizationUsersRoleGetDto>> getDetailedOrganizationsForUser(
		@Parameter(description = "UserUuid") @PathVariable("userUuid") UUID userUuid
	) {
		return ResponseEntity
			.ok()
			.body(
				organizationUserRoleService.getDetailedOrganizationsForUserWithUuid(
					userUuid
				)
			);
	}

	@Operation(
		summary = "Update the role of an user for the given organization",
		description = "By providing the organization name, user email address and their role, the roles of the user will be updated \n The role has to be a application role with purpose Organization. \n\n Predefined roles that have the purpose Organization are: \n\n ORGANIZATION_ADMIN,\n ORGANIZATION_OWNER\nORGANIZATION_USER,\nORGANIZATION_GUEST, \n \n **Only users that have the application role ORGANIZATION_OWNER or ORGANIZATION_ADMIN can invite other user to the organization**"
	)
	@ApiResponse(responseCode = "202", description = "Role has been updated")
	@PutMapping(
		value = "/users/roles/{organizationName}/usersUuid/{userUuid}",
		produces = "application/json"
	)
	public ResponseEntity<Object> updateRole(
		@Parameter(description = "Organization name", example = "Ravel") @PathVariable(
			"organizationName"
		) String organizationName,
		@Parameter(description = "UserUuid") @PathVariable("userUuid") UUID userUuid,
		@Parameter(
			description = "Role for user",
			example = "ORGANIZATION_USER"
		) @RequestParam("organizationRole") String organizationRole
	) {
		organizationUserRoleService.updateRoleWithUuid(
			organizationName,
			userUuid,
			organizationRole
		);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
		summary = "Remove user from a organization",
		description = "By providing the organization name and user email address, the user can be removed from a organization  \n \n **Only users that have the application role ORGANIZATION_OWNER or ORGANIZATION_ADMIN can invite other user to the organization**"
	)
	@ApiResponse(
		responseCode = "204",
		description = "User has been removed from organization"
	)
	@DeleteMapping(
		value = "/{organizationName}/userUuid/{userUuid}",
		produces = "application/json"
	)
	public ResponseEntity<Object> removeUserFromOrganization(
		@Parameter(description = "Organization name", example = "Ravel") @PathVariable(
			"organizationName"
		) String organizationName,
		@Parameter(description = "UserUuid") @PathVariable("userUuid") UUID userUuid
	) {
		organizationUserRoleService.removeUserFromOrganizationWithUuid(
			organizationName,
			userUuid
		);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
