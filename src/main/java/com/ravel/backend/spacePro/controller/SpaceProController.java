package com.ravel.backend.spacePro.controller;

import com.ravel.backend.spacePro.dto.*;
import com.ravel.backend.spacePro.service.PortalService;
import com.ravel.backend.spacePro.service.SpaceProService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Ravel Pro Spaces", description = "Ravel Pro Spaces")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v1/spaces/pro/")
@RestController
@AllArgsConstructor
public class SpaceProController {

	private SpaceProService spaceProService;
	private PortalService portalService;

	@Operation(
			summary = "Create a portal",
			description = "When user is inside a Space Pro, it can create a portal to another Space Pro. When users gives up the destination Space, this API will store the portal. Use the Metadata value to add any data such as position and rotation of portal in space pro"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Portal created",
			content = {
					@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PortalPostDto.class)
					),
			}
	)
	@PostMapping(value = "portals", consumes = "application/json")
	public ResponseEntity <PortalPostDto> createPortal(
			@RequestBody PortalPostDto portalPostDto
	) {
		spaceProService.createSpaceProPortal(portalPostDto);
		return ResponseEntity.ok().build();
	}



	@Operation(
			summary = "Delete portal",
			description = "When user is inside a Space Pro,it can delete existing portals via this API"
	)
	@ApiResponse(
			responseCode = "204",
			description = "Portal deleted"
	)
	@DeleteMapping(value = "/portals/{portalUuid}")
	public ResponseEntity<Object> deletePortal(
			@Parameter(description = "Portal UUID") @PathVariable UUID portalUuid
	){
		spaceProService.deleteSpaceProPortal(portalUuid);
		return ResponseEntity.noContent().build();
	}

	@Operation(
			summary = "Get all portals for space",
			description = "Returns all portals for a space"
	)
	@ApiResponse(
			responseCode = "200",
			description = "OK",
			content = {
					@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PortalGetDto.class)
					),
			}
	)
	@GetMapping(value = "/portals/{spaceUuid}")
	public ResponseEntity<List<PortalGetDto>> getAllPortalsForSpace(
			@Parameter(description = "Space Pro Uuid") @PathVariable UUID spaceUuid
	) {
		return ResponseEntity.ok().body(portalService.getPortalsForSpaceUuid(spaceUuid));
	}

	@Operation(
			summary = "Update portal",
			description = "Update an existing portal"
	)
	@ApiResponse(
			responseCode = "202",
			description = "Updated",
			content = {
					@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PortalUpdateDto.class)
					),
			}
	)
	@PutMapping(value = "/portals/{portalUuid}")
	public ResponseEntity<List<PortalGetDto>> updatePortal(
			@Parameter(description = "Portal UUID") @PathVariable UUID portalUuid,
			@RequestBody PortalUpdateDto portalUpdateDto
	) {
		portalService.updatePortal(portalUpdateDto,portalUuid);
		return ResponseEntity.accepted().build();
	}


	@Operation(
		summary = "Get Active Pro Spaces for Organization",
		description = "A user with the Organization Role Admin or Owner can create a new Ravel Pro Space for an given organization"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Ravel Pro Space found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SpaceProGetEnvDto.class)
			),
		}
	)
	@GetMapping(value = "/spaces/{organizationUuid}")
	public ResponseEntity<List<SpaceProGetEnvDto>> getActiveProSpacesForOrg(
		@Parameter(description = "Organization UUID") @PathVariable UUID organizationUuid
	) {
		return ResponseEntity
			.ok()
			.body(spaceProService.getProSpacesOrganization(organizationUuid));
	}

	@Operation(
			summary = "Get Active Pro Space by UUID",
			description = "-"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Ravel Pro Space found",
			content = {
					@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = SpaceProGetEnvDto.class)
					),
			}
	)
	@GetMapping(value = "/spaces/url/{spaceProUuid}")
	public ResponseEntity<SpaceProGetEnvDto> getActiveSpacePro(
			@Parameter(description = "Space Pro UUID") @PathVariable UUID spaceProUuid
	) {


		return ResponseEntity
				.ok()
				.body(spaceProService.getSpaceProByUuid(spaceProUuid));
	}

	@Operation(
			summary = "Get Active Pro Space by code",
			description = "-"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Ravel Pro Space found",
			content = {
					@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = SpaceProGetEnvDto.class)
					),
			}
	)
	@GetMapping(value = "/sessions/{spaceProCode}")
	public ResponseEntity<SpaceProGetEnvDto> getActiveSpaceProByCode(
			@Parameter(description = "Space Pro Code") @PathVariable String spaceProCode
	) {


		return ResponseEntity
				.ok()
				.body(spaceProService.getSpaceProByCode(spaceProCode));
	}



	//    @Operation(summary = "Get Private and Active Pro Spaces for User", description = "User can create a private Space")
	//    @ApiResponse(
	//            responseCode = "200",
	//            description = "Ravel Pro Space found",
	//            content = {
	//                    @Content(
	//                            mediaType = "application/json",
	//                            schema = @Schema(implementation = SpaceProGetEnvDto.class)
	//                    ),
	//            }
	//    )
	//    @GetMapping(value = "/private/{userUuid}")
	//    public ResponseEntity<List<SpaceProGetEnvDto>>getActiveProSpacesForUser(
	//            @Parameter(
	//                    description = "User UUID"
	//            ) @PathVariable UUID userUuid
	//    ) {
	//
	//        return ResponseEntity.ok().body(spaceProService.getProSpacesOrganization(userUuid));
	//    }

	@Operation(
		summary = "Get server connection details",
		description = "Returns details to connect to Photon server and Agora Voice Servers \n "
	)
	@ApiResponse(
		responseCode = "200",
		description = "Photon Room Id found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = JoinSpaceGetDtoV2.class)
			),
		}
	)
	@GetMapping(value = "/roomIds/{userUuid}/{spaceUuid}")
	public ResponseEntity<Object> getPhotonRoomId(
		@Parameter(description = "userUuid") @PathVariable UUID userUuid,
		@Parameter(description = "spaceUuid") @PathVariable UUID spaceUuid
	) {
		return ResponseEntity
			.ok()
			.body(spaceProService.getPhotonRoomId(userUuid, spaceUuid));
	}

	@Operation(
			summary = "Get session details",
			description = "Returns details to connect to Photon server and Agora Voice Servers \n "
	)
	@ApiResponse(
			responseCode = "200",
			description = "Details found",
			content = {
					@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = JoinSpaceGetDtoV2.class)
					),
			}
	)
	@GetMapping(value = "/sessions/{userUuid}/{spaceProCode}")
	public ResponseEntity<Object> getSessionDetails(
			@Parameter(description = "userUuid") @PathVariable UUID userUuid,
			@Parameter(description = "spaceUuid") @PathVariable String spaceProCode
	) {
		return ResponseEntity
				.ok()
				.body(spaceProService.getSessionDetails(userUuid, spaceProCode));
	}

	//    @Operation(summary = "Create new private Ravel Pro Space", description = " Creates a new space for an user. Please provide an existing environment name, cannot create Space without. SpaceType can only be :PRIVATE, PUBLIC, ORGANIZATION, OTHER")
	//    @ApiResponse(
	//            responseCode = "201",
	//            description = "New space created"
	//    )
	//    @PostMapping(value = "/private/{userUuid}", consumes = "application/json" )
	//    public ResponseEntity<Object> createNewPrivateProSpace(
	//            @Parameter(
	//                    description = "User Uuid"
	//            ) @PathVariable UUID userUuid,
	//            @RequestBody SpaceProPostDto spaceProPostDto
	//            ) {
	//        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	//    }

	@Operation(
		summary = "Create new Ravel Pro Space for Organization",
		description = " Creates a new space for an organization. Please provide an existing environment name, cannot create Space without. \n SpaceType can only be :PRIVATE, PUBLIC, ORGANIZATION, OTHER "
	)
	@ApiResponse(responseCode = "201", description = "New space created")
	@PostMapping(value = "/organizations/", consumes = "application/json")
	public ResponseEntity<Object> createNewOrgProSpace(
		@RequestBody SpaceProPostDto spaceProPostDto
	) {
		String photonRoomId = spaceProService
			.createNewOrgProSpace(spaceProPostDto)
			.getSessionSpaceId();
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{spaceUuid}")
			.buildAndExpand(photonRoomId)
			.toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(
		summary = "Update Environment for Ravel Pro Space",
		description = "Updates the environment for Ravel Pro Space. Please provide an existing environment name, cannot update Space without."
	)
	@ApiResponse(responseCode = "202", description = "Space Pro Environment updated")
	@PutMapping(value = "/{spaceUuid}", consumes = "application/json")
	public ResponseEntity<Object> updateEnvForOrgProSpace(
		@Parameter(description = "Space Pro UUID") @PathVariable UUID spaceUuid,
		@RequestBody SpaceProEnvironmentPostDto spaceProEnvironmentPostDto
	) {
		spaceProService.updateSpaceProEnvironment(
			spaceUuid,
			spaceProEnvironmentPostDto.getEnvironmentName()
		);
		return ResponseEntity.accepted().build();
	}

	@Operation(
		summary = "Delete ravel Pro space",
		description = "Deletes an Ravel Pro Space"
	)
	@ApiResponse(responseCode = "204", description = "Space Pro Environment updated")
	@DeleteMapping(value = "/{spaceUuid}")
	public ResponseEntity<Object> deleteSpacePro(
		@Parameter(description = "Space Pro UUID") @PathVariable UUID spaceUuid
	) {
		spaceProService.deleteSpacePro(spaceUuid);
		return ResponseEntity.noContent().build();
	}

	@Operation(
		summary = "Assign role to SpaceProUser",
		description = "Assign role to user for specific Ravel Pro Space"
	)
	@ApiResponse(responseCode = "202", description = "New role assigned")
	@PostMapping(value = "/roles/{spaceUuid}", consumes = "application/json")
	public ResponseEntity<Object> assignRoleToSpaceProUser(
		@Parameter(description = "Space Uuid") @PathVariable UUID spaceUuid,
		@RequestBody AssignRoleToProSpaceUser assignRoleToProSpaceUser
	) {
		spaceProService.assignRoleToProSpaceUser(spaceUuid, assignRoleToProSpaceUser);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
