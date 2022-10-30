package com.ravel.backend.modules.customWaterschap.userEntry;

import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(path = "api/v1/module/waterschappen")
@RestController
@Tag(name = "App Module Custom Client", description = "Custom App Moduels for Clients")
public class ModuleWaterschappenController {

	@Autowired
	private ModuleWaterschappenService moduleWaterschappenService;

	@Operation(
		summary = "Get active entries",
		description = "This endpoint wil show all active Modules\n ## THIS ENDPOINT IS FOR NOT MEANT TO BE USED BY THE UNITY APPLICATION AND IS ONLY temporary accessible"
	)
	@GetMapping
	public List<ModuleWaterschappen> getEntries() {
		return moduleWaterschappenService.getEntries();
	}

	@Operation(
		summary = "Get the entry for a given user Email",
		description = "## This endpoint will provide the details for a givin username for the Waterschappen Module\n This is custom module for the organization Waterschappen"
	)
	@GetMapping(value = "/{email}")
	public ResponseEntity<ModuleWaterschappen> getEntryByUsername(
		@Parameter(name = "email", description = "developer@ravel.world") @PathVariable(
			"email"
		) String email
	) {
		Sentry.captureMessage("User email in api-request URL");
		return ResponseEntity.ok().body(moduleWaterschappenService.getEntry(email));
	}

	@Operation(
		summary = "UNITY: Get the entry for a given user Uuid",
		description = "## This endpoint will provide the details for a givin username for the Waterschappen Module\n This is custom module for the organization Waterschappen"
	)
	@GetMapping(value = "/uuid/{userUuid}")
	public ResponseEntity<ModuleWaterschappen> getEntryByUserUuid(
		@Parameter(name = "userUuid", description = "") @PathVariable(
			"userUuid"
		) UUID userUuid
	) {
		return ResponseEntity
			.ok()
			.body(moduleWaterschappenService.getEntryUuid(userUuid));
	}

	@Operation(
		summary = "Create a new entry for the module Waterschappen",
		description = "## This endpoint wil create a new entry for the module Waterschappen\n This is custom module for the organization Waterschappen"
	)
	@PostMapping
	public ResponseEntity<ModuleWaterschappen> createNewEntry(
		@RequestParam String email,
		@RequestParam String waterschappenName,
		@RequestParam String kernProcesName
	) {
		ModuleWaterschappen newModuleWaterschappen = moduleWaterschappenService.createNewEntry(
			email,
			waterschappenName,
			kernProcesName
		);
		return ResponseEntity.accepted().body(newModuleWaterschappen);
	}

	@Operation(
		summary = "UNITY: Create a new entry for the module Waterschappen",
		description = "## This endpoint wil create a new entry for the module Waterschappen\n This is custom module for the organization Waterschappen"
	)
	@PostMapping(value = "/uuid/")
	public ResponseEntity<ModuleWaterschappen> createNewEntryUuid(
		@RequestParam(value = "userUuid", defaultValue = "") UUID userUuid,
		@RequestParam(
			value = "Waterschappen name",
			defaultValue = "Amstel Gooi en Vecht"
		) String waterschappenName,
		@RequestParam(
			value = "Kern Process name",
			defaultValue = "Waterveiligheid"
		) String kernProcesName
	) {
		ModuleWaterschappen newModuleWaterschappen = moduleWaterschappenService.createNewEntryUuid(
			userUuid,
			waterschappenName,
			kernProcesName
		);
		return ResponseEntity.accepted().body(newModuleWaterschappen);
	}
}
