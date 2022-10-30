package com.ravel.backend.organization.controller;

import com.ravel.backend.organization.dtos.OrganizationGetDto;
import com.ravel.backend.organization.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
	name = "Organizations",
	description = "Manage organizations & assigning users to organizations"
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v1/organizations")
@RestController
public class OrganizationController {

	private OrganizationService organizationService;

	@Autowired
	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Operation(
		summary = "Get all active organizations",
		description = "Will return all active organizations"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Organization found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = OrganizationGetDto.class)
			),
		}
	)
	@GetMapping(value = "/active", produces = "application/json")
	public List<OrganizationGetDto> getActiveOrganizations() {
		return organizationService.getAllActiveOrganizations();
	}

	@Operation(
		summary = "Get organization by name",
		description = "Will return only active organizations"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Organization found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = OrganizationGetDto.class)
			),
		}
	)
	@GetMapping(value = "/active/{organizationName}", produces = "application/json")
	public ResponseEntity<OrganizationGetDto> getOrganizationByName(
		@Parameter(description = "Organization Name", example = "Ravel") @PathVariable(
			name = "organizationName"
		) String organizationName
	) {
		return ResponseEntity
			.ok()
			.body(organizationService.getOrganizationByName(organizationName));
	}
}
