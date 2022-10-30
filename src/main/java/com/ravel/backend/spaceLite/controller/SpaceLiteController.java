package com.ravel.backend.spaceLite.controller;

import com.ravel.backend.spaceLite.dto.SpaceLiteGetDto;
import com.ravel.backend.spaceLite.dto.SpaceLitePostDto;
import com.ravel.backend.spaceLite.service.SpaceLiteOrganizationService;
import com.ravel.backend.spaceLite.service.SpaceLiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Ravel Lite Spaces", description = "Ravel Lite Spaces")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v1/spaces/lite/")
@RestController
public class SpaceLiteController {

	private SpaceLiteService spaceLiteService;
	private SpaceLiteOrganizationService spaceLiteOrganizationService;

	@Autowired
	public SpaceLiteController(
		SpaceLiteService spaceLiteService,
		SpaceLiteOrganizationService spaceLiteOrganizationService
	) {
		this.spaceLiteService = spaceLiteService;
		this.spaceLiteOrganizationService = spaceLiteOrganizationService;
	}

	@Operation(
		summary = "Create a new Ravel Lite Space",
		description = "Creates a new Rave Lite Space"
	)
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createSpaceLite(
		@RequestBody SpaceLitePostDto spaceLitePostDto
	) {
		String name = spaceLiteService.createNewSpaceLite(spaceLitePostDto).getName();
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{name}")
			.buildAndExpand(name)
			.toUri();

		return ResponseEntity.created(location).build();
	}

	@Operation(
		summary = "Update existing Ravel Lite Space Details",
		description = "By providing the name of a space and update in body, the details of an existing space can be updated. Partial update is also possible by passing a value of null, items with value null will be ignored in the object update."
	)
	@PutMapping(
		value = "/{name}",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Object> updateSpaceLite(
		@Parameter(
			description = "Space name",
			example = "Name"
		) @PathVariable String name,
		@RequestBody SpaceLitePostDto spaceLitePostDto
	) {
		String updatedName = spaceLiteService
			.updateSpaceLite(name, spaceLitePostDto)
			.getName();
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{name}")
			.buildAndExpand(updatedName)
			.toUri();

		return ResponseEntity.created(location).build();
	}

	@Operation(
		summary = "Get Active Ravel Lite Space by Name",
		description = "By providing the name of a space, this service will return the details of space if active"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Ravel Lite Space found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SpaceLiteGetDto.class)
			),
		}
	)
	@GetMapping(value = "/{name}", produces = "application/json")
	public ResponseEntity<SpaceLiteGetDto> getSpaceLite(
		@Parameter(description = "Space name", example = "Name") @PathVariable String name
	) {
		return ResponseEntity.ok().body(spaceLiteService.getActiveSpaceLiteDto(name));
	}

	@Operation(
		summary = "Get Active Ravel Lite Spaces",
		description = "This service will return all the active Ravel Lite Spaces and their details"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Ravel Lite Space found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SpaceLiteGetDto.class)
			),
		}
	)
	@GetMapping(value = "", produces = "application/json")
	public ResponseEntity<List<SpaceLiteGetDto>> getActiveSpaces() {
		return ResponseEntity.ok().body(spaceLiteService.getSpacesForLite());
	}

	@Operation(
		summary = "Get Active Ravel Lite Spaces for User",
		description = "This service will return all the active Ravel Lite Spaces and their details for a user"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Ravel Lite Spaces found for user",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SpaceLiteGetDto.class)
			),
		}
	)
	@GetMapping(value = "/user", produces = "application/json")
	public ResponseEntity<Set<SpaceLiteGetDto>> getActiveSpacesForUser() {
		return ResponseEntity.ok().body(spaceLiteService.getSpaceLiteForUser());
	}

	@Operation(summary = "Add Ravel Lite Space to organization", description = "")
	@PostMapping(
		value = "/organization/{organizationName}/space/{name}",
		produces = "application/json"
	)
	public ResponseEntity<Object> addLiteSpaceToOrganization(
		@Parameter(
			description = "Space name",
			example = "Name"
		) @PathVariable String name,
		@Parameter(
			description = "Organization Name",
			example = "Ravel"
		) @PathVariable String organizationName
	) {
		return ResponseEntity
			.ok()
			.body(
				spaceLiteOrganizationService.addSpaceLiteToOrganization(
					organizationName,
					name
				)
			);
	}
}
