package com.ravel.backend.users.controller;

import com.ravel.backend.users.dtos.ConnectionInvitesGetDto;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.service.ConnectionService;
import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "Connections",
	description = "Invite, accecpt, decline and remove connections"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/connections")
public class ConnectionController {

	private ConnectionService connectionService;

	@Autowired
	public ConnectionController(ConnectionService connectionService) {
		this.connectionService = connectionService;
	}

	@Operation(
		summary = "Get list of connection invites",
		description = "Returns a list of open connection invites that the user recieved"
	)
	@ApiResponse(responseCode = "200", description = "Connections found")
	@GetMapping(value = "/invites")
	public ResponseEntity<List<ConnectionInvitesGetDto>> getConnectionInvites() {
		return ResponseEntity.ok().body(connectionService.getConnectionInvites());
	}

	@Operation(
		summary = "Get list of connections ",
		description = "Returns a list of users that are connections of the user"
	)
	@ApiResponse(responseCode = "200", description = "Connections found")
	@GetMapping
	public ResponseEntity<List<UserDetailsGetDto>> getConnections() {
		return ResponseEntity.ok().body(connectionService.getConnections());
	}

	@Operation(
		summary = "Invite a user to connect",
		description = "Will invite a other user by sending a connection invite"
	)
	@ApiResponse(responseCode = "201", description = "User invited")
	@PostMapping(value = "/sendinvites/{userEmail}")
	public ResponseEntity<Object> sendConnectionInvite(
		@Parameter(
			description = "User email to",
			example = "FirstName001@ravel.world"
		) @PathVariable("userEmail") String userEmail
	) {
		connectionService.sendConnectionInvite(userEmail);
		Sentry.captureMessage("User email in api-request URL");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(
		summary = "Accept a invite that is received",
		description = "Will accept a connection invite send by another user"
	)
	@ApiResponse(responseCode = "202", description = "Invitation accepted")
	@PutMapping(value = "/accepts/{emailFrom}")
	public ResponseEntity<Object> acceptConnectionInvite(
		@Parameter(
			description = "User email from sender",
			example = "developer@ravel.world"
		) @PathVariable("emailFrom") String emailFrom
	) {
		connectionService.acceptConnectionInvite(emailFrom);
		Sentry.captureMessage("User email in api-request URL");
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Operation(
		summary = "Decline a invite that is received",
		description = "Will decline and remote a connection invite send by another user"
	)
	@ApiResponse(responseCode = "204", description = "Invitation declined")
	@PutMapping(value = "/{emailFrom}")
	public ResponseEntity<Object> declineConnectionInvite(
		@Parameter(
			description = "Decline invite from sender",
			example = "developer@ravel.world"
		) @PathVariable("emailFrom") String emailFrom
	) {
		connectionService.declineConnectionInvite(emailFrom);
		Sentry.captureMessage("User email in api-request URL");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(
		summary = "Remove a connection",
		description = "Will remove a connection with another user, de-connect"
	)
	@ApiResponse(responseCode = "204", description = "Connection removed")
	@DeleteMapping(value = "/{emailFrom}")
	public ResponseEntity<Object> removeConnection(
		@Parameter(
			description = "removes connection with",
			example = "developer@ravel.world"
		) @PathVariable("emailFrom") String emailFrom
	) {
		connectionService.removeConnection(emailFrom);
		Sentry.captureMessage("User email in api-request URL");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
