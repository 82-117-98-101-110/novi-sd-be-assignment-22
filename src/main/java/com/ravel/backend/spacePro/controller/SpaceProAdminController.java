package com.ravel.backend.spacePro.controller;

import com.ravel.backend.spacePro.dto.*;
import com.ravel.backend.spacePro.model.Portal;
import com.ravel.backend.spacePro.service.PortalService;
import com.ravel.backend.spacePro.service.SpaceProService;
import com.ravel.backend.spacePro.service.SpaceRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Ravel Pro Spaces Admin", description = "Management API for Ravel Pro Spaces")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v1/admin/spaces/pro/")
@RestController
@AllArgsConstructor
public class SpaceProAdminController {

	private SpaceRoleService spaceRoleService;
	private SpaceProService spaceProService;
	private PortalService portalService;

	@Operation(
			summary = "Get all portals",
			description = "receive all portals that exist"
	)
	@GetMapping(value = "/portal")
	public ResponseEntity<List<Portal>> getPortals(
	) {
		return ResponseEntity.ok(portalService.getPortal());
	}

	@Operation(
		summary = "Add new AppRole to SpaceRoles",
		description = "Adds a AppRole to the SpaceRole database. After adding AppRole to SpaceRole service/database, it can be used to assign to users. Only AppRoles that exist in the AppRole service can be added here. This Service will check if the AppRole purpose is valid(Purpose must be SPACE) and if it exists in AppRole Service"
	)
	@ApiResponse(responseCode = "201", description = "AppRole added to Spaces Pro")
	@PreAuthorize("hasAuthority('dev:access')")
	@PostMapping(value = "/spaceRoles", consumes = "application/json")
	public ResponseEntity<Object> addAppRoleToSpaceRoles(
		@RequestBody SpaceProRolePostDto spaceRolePostDto
	) {
		spaceRoleService.addAppRoleToSpaceRoles(spaceRolePostDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(
		summary = "Get all Ravel Pro Spaces ",
		description = "Returns all Ravel Pro Spaces"
	)
	@ApiResponse(
		responseCode = "200",
		description = "OK",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = EnvironmentProGetDto.class)
			),
		}
	)
	@PreAuthorize("hasAuthority('dev:access')")
	@GetMapping(value = "/all")
	public ResponseEntity<List<SpaceProGetEnvDto>> getAllProSpaces() {
		return ResponseEntity.ok().body(spaceProService.getAllSpacesPro());
	}
	//post new ravel pro organization space
	//Get all ravel pro spaces(active or not active, returns a boolean if active or not)
	//put update existing ravel pro space
	//delete ravel pro space
	//de-activate/activate ravel pro space



}
