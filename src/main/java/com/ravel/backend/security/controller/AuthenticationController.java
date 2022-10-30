package com.ravel.backend.security.controller;

import com.ravel.backend.security.payload.VerifyCode;
import com.ravel.backend.security.payload.request.AuthenticationRequest;
import com.ravel.backend.security.payload.request.AuthenticationRequestVR;
import com.ravel.backend.security.payload.request.RequestCodeRequest;
import com.ravel.backend.security.payload.response.AuthenticationResponse;
import com.ravel.backend.security.payload.response.RequestCodeResponse;
import com.ravel.backend.security.service.AuthorizationService;
import io.sentry.spring.tracing.SentryTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication of users")
@RestController
@RequestMapping(path = "/v1/auth")
public class AuthenticationController {

	private AuthorizationService authorizationService;

	@Autowired
	public AuthenticationController(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Operation(
		summary = "User login",
		description = "Provide user credentials to authenticate user. Returns userDetails after successful authentication"
	)
	@ApiResponses(
		value = {
			@ApiResponse(
				responseCode = "200",
				description = "Authentication Successful",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = AuthenticationResponse.class)
					),
				}
			),
		}
	)
	@PostMapping(
		value = "/login",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<AuthenticationResponse> authenticateUser(
		@Valid @RequestBody AuthenticationRequest authenticationRequest
	) {
		return ResponseEntity
			.ok()
			.body(authorizationService.authenticateUser(authenticationRequest));
	}

	@Operation(
		summary = "Request code",
		description = "VR-client device can request code. Code will be valid for three minutes and needs to be validated by authenticated user. "
	)
	@PostMapping(
		value = "/passwordless/request",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<RequestCodeResponse> requestCodeVrClient(
		@RequestBody RequestCodeRequest requestCodeRequest
	) {
		return ResponseEntity
			.ok()
			.body(authorizationService.requestCodeVrClient(requestCodeRequest));
	}

	@Operation(
		summary = "Verify code",
		description = "Authenticated user can verify code. Code can only be verified once and will be valid for three minutes since creation"
	)
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping(
		value = "/passwordless/verify",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Object> verifyCodeVrClient(@RequestBody VerifyCode verifyCode) {
		authorizationService.verifyCodeVrClient(verifyCode);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
		summary = "Authenticate with code",
		description = "VR-client device can use code to authenticate user. Code can only be used once and will be valid for three minutes since creation and needs to be validated by authenticated user."
	)
	@ApiResponse(
		responseCode = "200",
		description = "Authentication Successful",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = AuthenticationResponse.class)
			),
		}
	)
	@PostMapping(
		value = "/passwordless/authenticate",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<AuthenticationResponse> authenticateVrClient(
		@RequestBody AuthenticationRequestVR authenticationRequestVR
	) {
		return ResponseEntity
			.ok()
			.body(authorizationService.authenticateVrClient(authenticationRequestVR));
	}
}
