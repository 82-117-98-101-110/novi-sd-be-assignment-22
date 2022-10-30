package com.ravel.backend.users.controller;

import com.ravel.backend.users.service.UserInviteService;
import com.ravel.backend.users.service.UserService;
import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
	name = "Users & Accounts for System Admins",
	description = "Lock accounts, de-activate accounts or add user with specific system role"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v2/admin/users/")
@AllArgsConstructor
public class UserManagementControllerV2 {

	private UserService userService;

	@Operation(
		summary = "Activate / Deactivate User as system admin",
		description = "This endpoint will deactivate or activate an user account. \n \n **For this endpoint the user needs the admin:access authority**"
	)
	@PreAuthorize("hasAuthority('admin:access')")
	@PutMapping(value = "/deactivate/{userUuid}")
	public ResponseEntity<Void> deactivateUser(
		@Parameter(description = "User UUID") @PathVariable(
			name = "userUuid"
		) UUID userUuid,
		Boolean bool
	) {
		userService.deactivateUserWithUuid(userUuid, bool);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Operation(
		summary = "Lock / Unlock User as system admin",
		description = "This endpoint will deactivate or activate an user account.\n \n **For this endpoint the user needs the dev:access authority**"
	)
	@PreAuthorize("hasAuthority('dev:access')")
	@PutMapping(value = "/lock/{userUuid}")
	public ResponseEntity<Void> lockUser(
		@Parameter(description = "User UUID") @PathVariable(
			name = "userUuid"
		) UUID userUuid,
		Boolean bool
	) {
		userService.lockUserWithUuid(userUuid, bool);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Operation(
		summary = "Update user System role as system admin",
		description = "This endpoint will update the system role for a user \tROLE_USER,\n" +
		"\tROLE_USER_ADMIN,\n" +
		"\tROLE_DEV,\n" +
		"\tROLE_ADMIN,,\n" +
		"\tROLE_SUPER_ADMIN,\n" +
		"\tROLE_ADMIN_USER, \n \n **For this endpoint the user needs the super_admin:access authority**"
	)
	@PreAuthorize("hasAuthority('super_admin:access')")
	@PutMapping(value = "/updaterole/{userUuid}")
	public ResponseEntity<Void> updateUserSystemRole(
		@Parameter(description = "User UUID") @PathVariable(
			name = "userUuid"
		) UUID userUuid,
		String role
	) {
		userService.updateUserSystemRoleWithUuid(role, userUuid);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
