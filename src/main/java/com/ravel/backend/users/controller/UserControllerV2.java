package com.ravel.backend.users.controller;

import com.ravel.backend.users.dtos.*;
import com.ravel.backend.users.service.UserService;
import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "Users & Accounts",
	description = "For retrieving user information or updating user accounts"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v2/users/")
public class UserControllerV2 {

	private UserService userService;

	@Autowired
	public UserControllerV2(UserService userService) {
		this.userService = userService;
	}

	@Operation(
		summary = "Update Avatar",
		description = "This method will update the avatar of the User, please provide the UUID of the user"
	)
	@ApiResponse(
		responseCode = "202",
		description = "Avatar updated",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = AvatarPostDto.class)
			),
		}
	)
	@PutMapping(
		value = "/avatar/{userUuid}",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Void> updateAvatar(
		@Parameter(description = "userUuid") @PathVariable(
			name = "userUuid"
		) UUID userUuid,
		@Valid @RequestBody AvatarPostDto avatarPostDto
	) {
		userService.updateAvatarWithUuid(userUuid, avatarPostDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
			summary = "Update Avatar Full Body",
			description = "This method will update the full body avatar of the User, please provide the UUID of the user"
	)
	@ApiResponse(
			responseCode = "202",
			description = "Avatar updated",
			content = {
					@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = AvatarFullBodyPostDto.class)
					),
			}
	)
	@PutMapping(
			value = "/avatar/fullBody/{userUuid}",
			consumes = "application/json",
			produces = "application/json"
	)
	public ResponseEntity<Void> updateAvatarFullBody(
			@Parameter(description = "userUuid") @PathVariable(
					name = "userUuid"
			) UUID userUuid,
			@Valid @RequestBody AvatarFullBodyPostDto avatarFullBodyPostDto
	) {
		userService.updateAvatarFullBodyWithUuid(userUuid, avatarFullBodyPostDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
		summary = "Send Reset Password email",
		description = "By providing a email, a reset password email will be send if a user_account with that email exists "
	)
	@ApiResponse(responseCode = "200", description = "Password has been updated")
	@PostMapping(value = "/resetPasswordRequest")
	public ResponseEntity<Object> sendResetPasswordRequest(
		@RequestBody ResetPasswordRequest resetPasswordRequest
	) throws MessagingException {
		userService.sendResetPassword(resetPasswordRequest.getEmail());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
