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
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(
	name = "Users & Accounts",
	description = "For retrieving user information or updating user accounts"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/email/{userEmail}", produces = "application/json")
	@Operation(
		deprecated = true,
		summary = "Get a active user by Email",
		description = "This method will get the user details by email, please provide a email of a active user. If the user is not active, it will not return the user but it could exist in the database"
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
	public ResponseEntity<UserDetailsGetDto> getUserByEmail(
		@Parameter(
			description = "User Email",
			example = "developer@ravel.world"
		) @PathVariable(name = "userEmail") String userEmail
	) {
		Sentry.captureMessage("User email in api-request URL");
		return ResponseEntity
			.ok()
			.body(userService.getUserDetailsDtoByEmailAndIsActive(userEmail));
	}

	@Operation(
		summary = "Get user details",
		description = "By calling this endpoint with a access token, the endpoint returns the user details that belong to the provided access token. \n \n When this endpoints returns an account is not active or is locked exception, the user is not allowed to use the application anymore and needs to be logged-out "
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
	@GetMapping(value = "/self", produces = "application/json")
	public ResponseEntity<GetSelfDto> getSelf() {
		return ResponseEntity.ok().body(userService.getSelf());
	}

	@GetMapping(value = "/uuid/{userUuid}", produces = "application/json")
	@Operation(
		summary = "Get user by UUID",
		description = "This method will get the user details by UUID, please provide a UUID of a user. "
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
	public ResponseEntity<UserDetailsGetDto> getUserByUuid(
		@Parameter(
			description = "User UUID",
			example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
		) @PathVariable(name = "userUuid") UUID userUuid
	) {
		return ResponseEntity.ok().body(userService.getUserByUuid(userUuid));
	}

	@Operation(summary = "Upload userProfileImage", description = "")
	@PutMapping(
		value = "profileImages/{userUuid}",
		consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
	)
	public ResponseEntity<Object> uploadUserProfileImage(
		@Parameter(description = "userUuid") @PathVariable("userUuid") UUID userUuid,
		@RequestPart(value = "userProfileImage") MultipartFile userProfileImage
	) {
		userService.uploadUserProfileImage(userUuid, userProfileImage);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
		deprecated = true,
		summary = "Update Avatar",
		description = "This method will update the avatar of the User, please provide the email of the user"
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
		value = "/avatar/{userEmail}",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Void> updateAvatar(
		@Parameter(
			description = "User Email",
			example = "developer@ravel.world"
		) @PathVariable(name = "userEmail") String userEmail,
		@Valid @RequestBody AvatarPostDto avatarPostDto
	) {
		Sentry.captureMessage("User email in api-request URL");
		userService.updateAvatar(userEmail, avatarPostDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
		summary = "Get userdetail list with given UUIDS",
		description = "This method will return a list of active User Details after providing a list of UUIDS"
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
	@GetMapping(value = "/uuids/uuids/", params = "uuids")
	public ResponseEntity<List<UserDetailsGetDto>> getUsersByUuids(
		@Parameter(name = "uuids") @RequestParam List<UUID> uuids
	) {
		return ResponseEntity.ok().body(userService.findOnlyActiveUsersByUuids(uuids));
	}

	@Operation(
		summary = "Get userdetail list with given UUIDS",
		description = "This method will return a list of active User Details after providing a list of emails"
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
	@GetMapping(value = "/emails/", params = "userEmails")
	public ResponseEntity<List<UserDetailsGetDto>> getUsersByEmails(
		@Parameter(name = "userEmails") @RequestParam List<String> userEmails
	) {
		return ResponseEntity
			.ok()
			.body(userService.findOnlyActiveUsersByEmails(userEmails));
	}

	@Operation(
		deprecated = true,
		summary = "Send Reset Password email",
		description = "By providing a email, a reset password email will be send if a user_account with that email exists "
	)
	@ApiResponse(responseCode = "200", description = "Password has been updated")
	@PostMapping(value = "/resetpassword/{userEmail}")
	public ResponseEntity<Object> sendResetPassword(
		@Parameter(
			description = "User Email",
			example = "developer@ravel.world"
		) @PathVariable(name = "userEmail") String userEmail
	) throws MessagingException {
		Sentry.captureMessage("User email in api-request URL");
		userService.sendResetPassword(userEmail);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
		summary = "Set new password",
		description = "After user requested a reset password, receives an email with e reset link and, it can provide a new password on the webpage \n\n Please provide the new  password and confirmPassword. User account will be unlocked with this"
	)
	@ApiResponse(responseCode = "200", description = "Password has been updated")
	@PostMapping(
		value = "/resetpassword",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Object> resetPasswordWithToken(
		@RequestParam("token") String token,
		@Valid @RequestBody ResetPasswordPost resetPasswordPost
	) {
		userService.resetPasswordWithToken(token, resetPasswordPost);
		return ResponseEntity.ok().body(HttpStatus.OK);
	}
}
