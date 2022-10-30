package com.ravel.backend.organization.controller;

import com.ravel.backend.organization.dtos.OrganizationAdminGetDto;
import com.ravel.backend.organization.dtos.OrganizationGetDto;
import com.ravel.backend.organization.dtos.OrganizationPostDto;
import com.ravel.backend.organization.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "Organizations Admin",
	description = "Manage organizations & assigning users to organizations"
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v1/admin/organizations")
@RestController
public class OrganizationAdminController {

	private OrganizationService organizationService;

	@Autowired
	public OrganizationAdminController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Operation(
		summary = "Get all organizations",
		description = "Will return all organizations and include if they are active or not active \n \n **For this endpoint the user needs the admin:access authority**"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Organizations found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = OrganizationAdminGetDto.class)
			),
		}
	)
	@PreAuthorize("hasAuthority('admin:access')")
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<OrganizationAdminGetDto>> findOrganizationsDto() {
		return ResponseEntity.ok().body(organizationService.findAllOrganizations());
	}

	@Operation(
		summary = "Create a new organization",
		description = "Create a new organization by providing the organization name.  \n \n **For this endpoint the user needs the admin:access authority**"
	)
	@ApiResponse(
		responseCode = "201",
		description = "Organization created",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = OrganizationPostDto.class)
			),
		}
	)
	@PreAuthorize("hasAuthority('admin:access')")
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Void> createOrganization(
		@RequestBody OrganizationPostDto organizationPostDto
	) {
		organizationService.createOrganization(organizationPostDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(
		summary = "Update an existing organization",
		description = "Please provide the organization name of an existing organization as the path. Then give the new organization name in de body"
	)
	@ApiResponse(responseCode = "202", description = "Organization updated")
	@PreAuthorize("hasAuthority('admin:access')")
	@PutMapping(
		value = "/{organizationName}",
		consumes = "application/json",
		produces = "application/json"
	)
	public ResponseEntity<Void> updateOrganization(
		@Parameter(
			description = "The name of the organization",
			example = "Ravel"
		) @PathVariable(name = "organizationName") String organizationName,
		@Valid @RequestBody OrganizationPostDto organizationPostDto
	) {
		organizationService.updateOrganization(organizationName, organizationPostDto);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Operation(
		summary = "Get organization by UUID",
		description = "Will return only active organizations"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Organizations found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = OrganizationGetDto.class)
			),
		}
	)
	@GetMapping(value = "/active/ids/{organizationId}", produces = "application/json")
	public ResponseEntity<OrganizationGetDto> getOrganizationById(
		@Parameter(
			description = "Organization UUID",
			example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
		) @PathVariable(name = "organizationId") UUID organizationId
	) {
		return ResponseEntity
			.ok()
			.body(organizationService.getOrganizationById(organizationId));
	}

	@PreAuthorize("hasAuthority('admin:access')")
	@Operation(
		summary = "Activate or deactivate Organizations",
		description = "This endpoint will deactivate or activate an organization \n \n **For this endpoint the user needs the admin:access authority**"
	)
	@PutMapping(value = "/activation/{organizationName}", produces = "application/json")
	@ApiResponse(responseCode = "202", description = "Organization updated")
	public ResponseEntity<Void> organizationActivation(
		@Parameter(description = "Organization Name", example = "Ravel") @PathVariable(
			"organizationName"
		) String organizationName,
		Boolean bool
	) {
		organizationService.deactivateOrganization(organizationName, bool);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
