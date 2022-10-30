package com.ravel.backend.users.controller;

import com.ravel.backend.users.dtos.CreateUserPostDto;
import com.ravel.backend.users.dtos.UserDetailsGetAdminDto;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.model.UserInvite;
import com.ravel.backend.users.service.UserInviteService;
import com.ravel.backend.users.service.UserService;
import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "Users & Accounts for System Admins",
	description = "Lock accounts, de-activate accounts or add user with specific system role"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/admin/users/")
public class UserManagementController {

	private UserService userService;

	private UserInviteService userInviteService;

	@Autowired
	public UserManagementController(
		UserService userService,
		UserInviteService userInviteService
	) {
		this.userService = userService;
		this.userInviteService = userInviteService;
	}

	@GetMapping(value = "", produces = "application/json")
	@Operation(
		summary = "Get all users from the database",
		description = "This method will return all users with their details. This method should not be used as it exposes all users that are in the database."
	)
	@ApiResponse(
		responseCode = "200",
		description = "Request Successful",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserDetailsGetDto.class)
			),
		}
	)
	@PreAuthorize("hasAuthority('dev:access')")
	public ResponseEntity<List<UserDetailsGetAdminDto>> getAllUsersAdmin() {
		return ResponseEntity.ok().body(userService.getAllUsersAdmin());
	}

	@GetMapping(value = "/invites", produces = "application/json")
	@Operation(summary = "Get all user invites", description = "")
	@ApiResponse(
		responseCode = "200",
		description = "Request Successful",
		content = { @Content(mediaType = "application/json") }
	)
	@PreAuthorize("hasAuthority('dev:access')")
	public ResponseEntity<List<UserInvite>> getUserInvites() {
		return ResponseEntity.ok().body(userInviteService.userInviteList());
	}

	@Operation(
		summary = "Add user as system admin",
		description = "Add a new user to the system without sending a invite. For this endpoint a system role hase to be specified \n \n **For this endpoint the user needs the user_admin:access authority**"
	)
	@ApiResponse(
		responseCode = "200",
		description = "User account created",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserDetailsGetDto.class)
			),
		}
	)
	@PreAuthorize("hasAuthority('super_admin:access')")
	@PostMapping(
		value = "/add",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<UserDetailsGetDto> addUser(
		@Valid @RequestBody CreateUserPostDto createUserPostDto
	) {
		return ResponseEntity.ok().body(userService.addUser(createUserPostDto));
	}

	@Operation(
		deprecated = true,
		summary = "Activate / Deactivate User as system admin",
		description = "This endpoint will deactivate or activate an user account. \n \n **For this endpoint the user needs the admin:access authority**"
	)
	@PreAuthorize("hasAuthority('admin:access')")
	@PutMapping(value = "/deactivate/{userEmail}")
	public ResponseEntity<Void> deactivateUser(
		@Parameter(
			description = "User Email",
			example = "developer@ravel.world"
		) @PathVariable(name = "userEmail") String userEmail,
		Boolean bool
	) {
		Sentry.captureMessage("User email in api-request URL");
		userService.deactivateUser(userEmail, bool);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Operation(
		deprecated = true,
		summary = "Lock / Unlock User as system admin",
		description = "This endpoint will deactivate or activate an user account.\n \n **For this endpoint the user needs the dev:access authority**"
	)
	@PreAuthorize("hasAuthority('dev:access')")
	@PutMapping(value = "/lock/{userEmail}")
	public ResponseEntity<Void> lockUser(
		@Parameter(
			description = "User Email",
			example = "developer@ravel.world"
		) @PathVariable(name = "userEmail") String userEmail,
		Boolean bool
	) {
		Sentry.captureMessage("User email in api-request URL");
		userService.lockUser(userEmail, bool);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Operation(
		deprecated = true,
		summary = "Update user System role as system admin",
		description = "This endpoint will update the system role for a user \tROLE_USER,\n" +
		"\tROLE_USER_ADMIN,\n" +
		"\tROLE_DEV,\n" +
		"\tROLE_ADMIN,,\n" +
		"\tROLE_SUPER_ADMIN,\n" +
		"\tROLE_ADMIN_USER, \n \n **For this endpoint the user needs the super_admin:access authority**"
	)
	@PreAuthorize("hasAuthority('super_admin:access')")
	@PutMapping(value = "/updaterole/{userEmail}")
	public ResponseEntity<Void> updateUserSystemRole(
		@Parameter(
			description = "User Email",
			example = "developer@ravel.world"
		) @PathVariable(name = "userEmail") String userEmail,
		String role
	) {
		Sentry.captureMessage("User email in api-request URL");
		userService.updateUserSystemRole(role, userEmail);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
