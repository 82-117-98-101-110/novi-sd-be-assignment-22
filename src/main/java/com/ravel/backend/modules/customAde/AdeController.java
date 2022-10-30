package com.ravel.backend.modules.customAde;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(path = "api/v1/module/ade")
@RestController
@Tag(name = "App Module Custom Client", description = "Custom App Moduels for Clients")
public class AdeController {

	@Autowired
	private AdeService adeService;

	@GetMapping
	public List<Ade> getEntries() {
		return adeService.getEntries();
	}

	@Operation(
		summary = "Get the entry for a given username",
		description = "## This endpoint will provide the details for a given username for the ADE Module\n This is custom module for the organization ADE",
		deprecated = true
	)
	@GetMapping(value = "/{username}")
	public ResponseEntity<Ade> getEntryByUsername(
		@Parameter(name = "username", description = "rubenlangeweg") @PathVariable(
			"username"
		) String username
	) {
		return ResponseEntity.ok().body(adeService.getEntry(username));
	}

	@Operation(
		summary = "Create a new entry for the module ADE",
		description = "## This endpoint wil create a new entry for the module ADEn\n This is custom module for the organization ADE",
		deprecated = true
	)
	@PostMapping
	public ResponseEntity<Ade> createNewEntry(
		@RequestParam(value = "Username", defaultValue = "rubenlangeweg") String username,
		@RequestParam(value = "Country Code", defaultValue = "250") Long countryCode,
		@RequestParam(value = "Country Name", defaultValue = "France") String countryName,
		@RequestParam(
			value = "Country Alpha 2",
			defaultValue = "fr"
		) String countryAlpha2,
		@RequestParam(
			value = "Country Alpha3",
			defaultValue = "fra"
		) String countryAlpha3,
		@RequestParam(value = "Company", defaultValue = "XRBASE") String company
	) {
		Ade newAde = adeService.createNewEntry(
			username,
			countryCode,
			countryName,
			countryAlpha2,
			countryAlpha3,
			company
		);
		return ResponseEntity.accepted().body(newAde);
	}
}
