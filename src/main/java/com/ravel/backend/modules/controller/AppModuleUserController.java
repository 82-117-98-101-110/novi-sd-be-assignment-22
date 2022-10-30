package com.ravel.backend.modules.controller;

import com.ravel.backend.modules.model.AppModuleUser;
import com.ravel.backend.modules.service.AppModuleUserService;
import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@RequestMapping(path = "api/v1/module/user")
@RestController
@Tag(name = "App Module", description = "Creating modules, assigning them to users")
public class AppModuleUserController {

	@Autowired
	private AppModuleUserService appModuleUserService;

	@Autowired
	public AppModuleUserController(AppModuleUserService appModuleUserService) {
		this.appModuleUserService = appModuleUserService;
	}

	@Operation(
		summary = "Add user to a Module with Email",
		description = "Please use add a module to an organization, user specific modules are not yet implemented",
		deprecated = true
	)
	@PostMapping
	public ResponseEntity<AppModuleUser> createNewEntry(
		@RequestParam(
			value = "userEmail",
			defaultValue = "developer@ravel.world"
		) String userEmail,
		@RequestParam(
			value = "Module name name",
			defaultValue = "Waterschappen"
		) String moduleName
	) {
		AppModuleUser newAppModuleUser = appModuleUserService.addNewUserToModule(
			userEmail,
			moduleName
		);
		Sentry.captureMessage("User email in api-request");
		return ResponseEntity.accepted().body(newAppModuleUser);
	}

	@Operation(
		summary = "Add user to a Module with user UUID",
		description = "Please use add a module to an organization, user specific modules are not yet implemented",
		deprecated = true
	)
	@PostMapping(value = "/uuid")
	public ResponseEntity<AppModuleUser> createNewEntryWithUuid(
		@RequestParam(
			value = "userUuid",
			defaultValue = "developer@ravel.world"
		) UUID userUuid,
		@RequestParam(
			value = "Module name name",
			defaultValue = "Waterschappen"
		) String moduleName
	) {
		AppModuleUser newAppModuleUser = appModuleUserService.addNewUserUuidToModule(
			userUuid,
			moduleName
		);
		return ResponseEntity.accepted().body(newAppModuleUser);
	}

	@Operation(summary = "Get modules for user with Email", description = "")
	@GetMapping(value = "/{userEmail}", produces = "application/json")
	public ResponseEntity getModulesForUser(
		@Parameter(
			description = "userEmail",
			example = "developer@ravel.world"
		) @PathVariable("userEmail") String userEmail
	) {
		AppModuleUser appModuleUser = appModuleUserService.getModulesForUser(userEmail);
		Sentry.captureMessage("User email in api-request URL");
		return ResponseEntity.ok().body(appModuleUser);
	}

	@Operation(summary = "Get modules for user with user UUID", description = "")
	@GetMapping(value = "/uuid/{userUuid}")
	public ResponseEntity getModulesForUserUuid(
		@Parameter(
			name = "userUuid",
			description = "a4160cca-f0c0-4933-bcc2-c56c964e518c"
		) @PathVariable("userUuid") UUID userUuid
	) {
		AppModuleUser appModuleUser = appModuleUserService.getModulesForUserUuid(
			userUuid
		);
		return ResponseEntity.ok(appModuleUser);
	}
}
