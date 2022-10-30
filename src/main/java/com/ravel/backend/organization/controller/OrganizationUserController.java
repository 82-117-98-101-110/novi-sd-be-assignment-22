package com.ravel.backend.organization.controller;

import com.ravel.backend.organization.dtos.*;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
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
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "Organizations",
	description = "Manage organizations & assigning users to organizations"
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v1/organizations")
@RestController
public class OrganizationUserController {

	private OrganizationUserRoleService organizationUserRoleService;

	@Autowired
	public OrganizationUserController(
		OrganizationUserRoleService organizationUserRoleService
	) {
		this.organizationUserRoleService = organizationUserRoleService;
	}

	//	@Operation(
	//		summary = "Get organizations for a user",
	//		description = "By providing the email address of a user, the API will return the organizations that the user is part of"
	//	)
	//	@ApiResponse(
	//		responseCode = "200",
	//		description = "Organizations found",
	//		content = {
	//			@Content(
	//				mediaType = "application/json",
	//				schema = @Schema(implementation = GetOrganizationsForUserDto.class)
	//			),
	//		}
	//	)
	//	@GetMapping(value = "/users/{userEmail}", produces = "application/json")
	//	public ResponseEntity<List<GetOrganizationsForUserDto>> getOrganizationsForUser(
	//		@Parameter(
	//			description = "user e-mail",
	//			example = "developer@ravel.world"
	//		) @PathVariable("userEmail") String userEmail
	//	) {
	//		Sentry.captureMessage("User email in api-request URL");
	//		return ResponseEntity
	//			.ok()
	//			.body(organizationUserRoleService.getOrganizationsForUser(userEmail));
	//	}

	@Operation(
		summary = "Get organizations for a user wit UUID",
		description = "By providing the email address of a user, the API will return the organizations that the user is part of"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Organizations found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = GetOrganizationsForUserDto.class)
			),
		}
	)
	@GetMapping(value = "/users/uuid/{userUuid}", produces = "application/json")
	public ResponseEntity<List<GetOrganizationsForUserDto>> getOrganizationsForUserWithUuid(
		@Parameter(
			description = "userUuid",
			example = "a1fee381-f096-4d5c-96f4-fd7c209f8988"
		) @PathVariable("userUuid") UUID userUuid
	) {
		return ResponseEntity
			.ok()
			.body(organizationUserRoleService.getOrganizationsForUserUuid(userUuid));
	}

	//	@Operation(
	//		summary = "Get organizations of user with organization details",
	//		description = "By providing the email address of a user, it wil return a list of organizations for the user including the role and joinedAt for each organization"
	//	)
	//	@ApiResponse(
	//		responseCode = "200",
	//		description = "Organizations found",
	//		content = {
	//			@Content(
	//				mediaType = "application/json",
	//				schema = @Schema(implementation = OrganizationUsersRoleGetDto.class)
	//			),
	//		}
	//	)
	//	@GetMapping(
	//		value = "/organizationsdetailed/{userEmail}",
	//		produces = "application/json"
	//	)
	//	public ResponseEntity<List<OrganizationUsersRoleGetDto>> getDetailedOrganizationsForUser(
	//		@Parameter(
	//			description = "user e-mail",
	//			example = "developer@ravel.world"
	//		) @PathVariable String userEmail
	//	) {
	//		Sentry.captureMessage("User email in api-request URL");
	//		return ResponseEntity
	//			.ok()
	//			.body(organizationUserRoleService.getDetailedOrganizationsForUser(userEmail));
	//	}

	@Operation(
		summary = "User Organization Contacts",
		description = "By providing the UUID of a user, this service will check which organizations the user belongs, then it retrieves a list of all these users that are in the same organization, filters out the duplicates and will provide a list of UserDetails,  "
	)
	@ApiResponse(
		responseCode = "200",
		description = "User Organization Contacts found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = OrganizationUsersRoleGetDto.class)
			),
		}
	)
	@GetMapping(value = "/users/contacts/{userUuid}", produces = "application/json")
	public ResponseEntity<List<UserDetailsGetDto>> getUsersFromOrganizationsForUser(
		@Parameter(
			description = "user UUID",
			example = "49da3920-037d-454a-87e8-fda6f970f544"
		) @PathVariable UUID userUuid
	) {
		return ResponseEntity
			.ok()
			.body(organizationUserRoleService.getUsersFromOrganizationsForUser(userUuid));
	}

		@Operation(
			summary = "Get the role of an user for the given organization",
			description = "By providing the organization name, user uuid the endpoint will return the role. "
		)
		@ApiResponse(responseCode = "200", description = "User and role has been found")
		@GetMapping(
			value = "/users/roles/{organizationName}/usersuuid/{userUuid}",
			produces = "application/json"
		)
		public ResponseEntity<OrganizationUserRoleOnlyGetDto> getRoleForUserForOrganization(
			@Parameter(description = "Organization name", example = "Ravel") @PathVariable(
				"organizationName"
			) String organizationName,
			@Parameter(
				description = "User UUID",
				example = "8df9e42f-695a-4aae-a6b5-87a5f0d54324"
			) @PathVariable("userUuid") UUID userUuid
		) {
			return ResponseEntity
				.ok()
				.body(
					organizationUserRoleService.getRoleForUserForOrganization(
						organizationName,
						userUuid
					)
				);
		}

	@Operation(
		summary = "Get users for a organization",
		description = "Will return the users for a given organization name"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Organizations found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = DetailedUsersOrganization.class)
			),
		}
	)
	@GetMapping(
		value = "/organizations/{organizationName}",
		produces = "application/json"
	)
	public ResponseEntity<List<DetailedUsersOrganization>> getUsersForOrganization(
		@Parameter(description = "Organization name", example = "Ravel") @PathVariable(
			"organizationName"
		) String organizationName
	) {
		return ResponseEntity
			.ok()
			.body(organizationUserRoleService.getUsersForOrganization(organizationName));
	}

	@Operation(
		summary = "Add a user to an organization",
		description = "By providing the organization name, user email address and their role, the roles of the user will be updated \n The role has to be a application role with purpose Organization. \n\n Predefined roles that have the purpose Organization are: \n\n ORGANIZATION_ADMIN,\n ORGANIZATION_OWNER\nORGANIZATION_USER,\nORGANIZATION_GUEST,  \n \n **Only users that have the application role ORGANIZATION_OWNER or ORGANIZATION_ADMIN can invite other user to the organization** "
	)
	@ApiResponse(responseCode = "202", description = "User added to organization")
	@PostMapping(
		value = "/{organizationName}/usersEmail/{userEmail}",
		produces = "application/json"
	)
	public ResponseEntity<Object> addUserOrganizationEmail(
		@Parameter(description = "Organization name", example = "Ravel") @PathVariable(
			"organizationName"
		) String organizationName,
		@Parameter(
			description = "User email",
			example = "developer@ravel.world"
		) @PathVariable("userEmail") String userEmail,
		@Parameter(
			description = "Role for user",
			example = "ORGANIZATION_USER"
		) @RequestParam("organizationRole") String organizationRole
	) throws MessagingException {
		organizationUserRoleService.addUserEmailToOrganizationWithInviteEmail(
			organizationName,
			userEmail,
			organizationRole
		);
		Sentry.captureMessage("User email in api-request URL");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
		summary = "Add a user to an organization",
		description = "By providing the organization name, user email address and their role, the roles of the user will be updated \n The role has to be a application role with purpose Organization. \n\n Predefined roles that have the purpose Organization are: \n\n ORGANIZATION_ADMIN,\n ORGANIZATION_OWNER\nORGANIZATION_USER,\nORGANIZATION_GUEST,  \n \n **Only users that have the application role ORGANIZATION_OWNER or ORGANIZATION_ADMIN can invite other user to the organization** "
	)
	@ApiResponse(responseCode = "202", description = "User added to organization")
	@PostMapping(value = "/users", produces = "application/json")
	public ResponseEntity<Object> addUserOrganization(
		@RequestBody AddUserToOrg addUserToOrg
	) throws MessagingException {
		organizationUserRoleService.addUserEmailToOrganizationWithInviteEmail(
			addUserToOrg.getOrganizationName(),
			addUserToOrg.getUserEmail(),
			addUserToOrg.getOrganizationRole()
		);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Operation(
		summary = "Update the role of an user for the given organization",
		description = "By providing the organization name, user email address and their role, the roles of the user will be updated \n The role has to be a application role with purpose Organization. \n\n Predefined roles that have the purpose Organization are: \n\n ORGANIZATION_ADMIN,\n ORGANIZATION_OWNER\nORGANIZATION_USER,\nORGANIZATION_GUEST, \n \n **Only users that have the application role ORGANIZATION_OWNER or ORGANIZATION_ADMIN can invite other user to the organization**"
	)
	@ApiResponse(responseCode = "202", description = "Role has been updated")
	@PutMapping(
		value = "/users/roles/{organizationName}/usersEmail/{userEmail}",
		produces = "application/json"
	)
	public ResponseEntity<Object> updateRole(
		@Parameter(description = "Organization name", example = "Ravel") @PathVariable(
			"organizationName"
		) String organizationName,
		@Parameter(
			description = "User email",
			example = "developer@ravel.world"
		) @PathVariable("userEmail") String userEmail,
		@Parameter(
			description = "Role for user",
			example = "ORGANIZATION_USER"
		) @RequestParam("organizationRole") String organizationRole
	) {
		organizationUserRoleService.updateRole(
			organizationName,
			userEmail,
			organizationRole
		);
		Sentry.captureMessage("User email in api-request URL");
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
		value = "/{organizationName}/usersEmail/{userEmail}",
		produces = "application/json"
	)
	public ResponseEntity<Object> removeUserFromOrganization(
		@Parameter(description = "Organization name", example = "Ravel") @PathVariable(
			"organizationName"
		) String organizationName,
		@Parameter(
			description = "User email",
			example = "developer@ravel.world"
		) @PathVariable("userEmail") String userEmail
	) {
		organizationUserRoleService.removeUserFromOrganization(
			organizationName,
			userEmail
		);
		Sentry.captureMessage("User email in api-request URL");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(
		summary = "Accept Organization Invite",
		description = "User will receive an email with a link that holds a token. User can click link to accept organization invite. Link will redirect to webpage, token from link can be used to call this endpoint. User will now be part of the organization\n \n **This endpoint will be public**"
	)
	@ApiResponse(responseCode = "202", description = "Invite has been accepted")
	@GetMapping(value = "/invites/confirm")
	public ResponseEntity<Object> confirmOrganizationInvite(
		@RequestParam("token") String token
	) {
		organizationUserRoleService.acceptOrganizationInvite(token);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
