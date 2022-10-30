package com.ravel.backend.users.controller;

import com.ravel.backend.users.dtos.SignupPostDto;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "Sign-up and invite",
	description = "Let users sign-up for Ravel or send an invite to an new user"
)
@RestController
@RequestMapping("/api/v1/users")
public class SignupRequestController {

	private UserService userService;

	@Autowired
	public SignupRequestController(UserService userService) {
		this.userService = userService;
	}

	@Operation(
		summary = "Signup a user with a Organization Invite email",
		description = "Sign-up for Organization invite, updates user account with details provided in body \n\n Please provide an firstname, lastname, password and confirmPassword. Email is not needed for this endpoint, email is already registered in the system  \n\n User will be assigned with a role: ROLE_USER. Account will be active and not locked, this means the user can login with the provided credentials and is also part of the organization it has been invited for"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Invite accepted and account updated"
	)
	@PostMapping(
		value = "/invites/signup",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Object> confirmRavelOrgInvite(
		@RequestParam("token") String token,
		@Valid @RequestBody SignupPostDto signupPostDto
	) {
		String url = userService.acceptNewUserInviteOrg(token, signupPostDto);
		return ResponseEntity.ok().body(HttpStatus.OK);
	}

	@Operation(
		summary = "Signup a new user without Organization Invite email",
		description = "Creates a new account for a user without sending an invite email. \n\n Please provide an firstname, lastname, email, password and confirmPassword \n\n User will be assigned with a role: ROLE_USER. Account will be active and not locked, this means the user can login with the provided credentials without activating or unlocking its account.  "
	)
	@ApiResponse(
		responseCode = "200",
		description = "New user account created",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserDetailsGetDto.class)
			),
		}
	)
	@PreAuthorize("hasAuthority('dev:access')")
	@PostMapping(
		value = "/signup",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<UserDetailsGetDto> registerUser(
		@Valid @RequestBody SignupPostDto signupPostDto
	) {
		return ResponseEntity.ok().body(userService.signupUser(signupPostDto));
	}
}
