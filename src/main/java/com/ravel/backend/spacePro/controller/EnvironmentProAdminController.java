package com.ravel.backend.spacePro.controller;

import com.ravel.backend.spacePro.dto.EnvironmentProGetDto;
import com.ravel.backend.spacePro.dto.EnvironmentProPostDto;
import com.ravel.backend.spacePro.service.EnvironmentProService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Ravel Pro Environments Admin", description = "Ravel Pro Environments")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v1/admin/spaces/environments/pro")
@RestController
@AllArgsConstructor
public class EnvironmentProAdminController {

	private EnvironmentProService environmentProService;

	@Operation(
		summary = "Create new Environment",
		description = "Creates a new environment for Ravel Pro. When created, it can be linked to an Ravel Pro Space "
	)
	@ApiResponse(responseCode = "201", description = "Environment Created")
	@PreAuthorize("hasAuthority('dev:access')")
	@PostMapping(value = "", consumes = "application/json")
	public ResponseEntity<Object> createNewProEnvironment(
		@RequestBody EnvironmentProPostDto environmentPostDto
	) {
		UUID environmentUuid = environmentProService
			.createNewProEnvironment(environmentPostDto)
			.getEnvironmentUuid();
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{environmentUuid}")
			.buildAndExpand(environmentUuid)
			.toUri();
		return ResponseEntity.created(location).build();
	}

	//    @Operation(summary = "Update Environment", description = "Updates a environment for Ravel Pro. ")
	//    @ApiResponse(
	//            responseCode = "202",
	//            description = "Environment Updated"
	//    )
	//    @PutMapping(value = "/{environmentName}", consumes = "application/json" )
	//    public ResponseEntity<EnvironmentProGetDto> updateExistingProEnvironment(
	//            @Parameter(
	//                    description = "Environment Name"
	//            ) @PathVariable String environmentName,
	//            @RequestBody EnvironmentProPostDto environmentPostDto
	//    ) {
	////        return ResponseEntity.ok().body(environmentProService.createNewProEnvironment(environmentPostDto));
	//        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	//    }

	//    @Operation(summary = "Get environment by UUID", description = "Returns the environment with environmentUuid")
	//    @ApiResponse(
	//            responseCode = "200",
	//            description = "Environment found",
	//            content = {
	//                    @Content(
	//                            mediaType = "application/json",
	//                            schema = @Schema(implementation = EnvironmentProGetDto.class)
	//                    ),
	//            }
	//    )
	//    @GetMapping(value = "/uuid/{environmentUuid}" )
	//    public ResponseEntity<EnvironmentProGetDto> getEnvironmentByUuid(
	//            @PathVariable UUID environmentUuid) {
	//        return ResponseEntity.ok().body(environmentProService.getActiveEnvironmentByUuid(environmentUuid));
	//    }

	@Operation(
		summary = "Get environment by Name",
		description = "Returns the environment with environmentUuid"
	)
	@ApiResponse(
		responseCode = "200",
		description = "Environment found",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = EnvironmentProGetDto.class)
			),
		}
	)
	@PreAuthorize("hasAuthority('dev:access')")
	@GetMapping(value = "/name/{environmentName}")
	public ResponseEntity<EnvironmentProGetDto> getEnvironmentByName(
		@PathVariable String environmentName
	) {
		return ResponseEntity
			.ok()
			.body(environmentProService.getActiveEnvironmentByName(environmentName));
	}

	@Operation(
		summary = "Get all environments",
		description = "Returns all Ravel Pro Environments, including active or non-active."
	)
	@ApiResponse(
		responseCode = "200",
		description = "Environment Created",
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = EnvironmentProGetDto.class)
			),
		}
	)
	@PreAuthorize("hasAuthority('dev:access')")
	@GetMapping(value = "/all")
	public ResponseEntity<List<EnvironmentProGetDto>> getProEnvironments() {
		return ResponseEntity.ok().body(environmentProService.getAllActiveEnvironments());
	}

	@Operation(
		summary = "Upload image for Environment",
		description = "Upload a preview image for the environment "
	)
	@ApiResponse(responseCode = "202", description = "Environment Updated")
	@PostMapping(
		value = "/image/{environmentName}",
		consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
	)
	@PreAuthorize("hasAuthority('dev:access')")
	public ResponseEntity<Object> uploadEnvironmentImage(
		@Parameter(description = "Environment Name") @PathVariable String environmentName,
		@RequestPart(value = "image") MultipartFile image
	) {
		environmentProService.uploadEnvironmentImage(environmentName, image);
		return ResponseEntity.accepted().build();
	}
	//    @Operation(summary = "Get all Spaces Pro linked to environment", description = "Returns all all Ravel Pro Spaces linked to Environment")
	//    @ApiResponse(
	//            responseCode = "200",
	//            description = "Environment Created",
	//            content = {
	//                    @Content(
	//                            mediaType = "application/json",
	//                            schema = @Schema(implementation = SpaceProGetDto.class)
	//                    ),
	//            }
	//    )
	//    @GetMapping(value = "/organization", consumes = "application/json" )
	//    public ResponseEntity<List<EnvironmentProGetDto>> getProSpacesForEnvironment(
	//    ) {
	//        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	//    }
	//
	//
	//    @Operation(summary = "Activate/De-Activate Environment", description = "De-activates or activates an environment.   ")
	//    @ApiResponse(
	//            responseCode = "202",
	//            description = "Environment Updated"
	//    )
	//    @PutMapping(value = "/activation/{environmentName}")
	//    public ResponseEntity<Object> activationProEnvironment(
	//            @Parameter(
	//                    description = "Environment Name"
	//            ) @PathVariable String environmentName,
	//            @Parameter(
	//                    description = "Environment Name"
	//            ) @PathVariable Boolean bool
	//    ) {
	//        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	//    }
	//
	//    @Operation(summary = "Delete Environment", description = "Updates a environment for Ravel Pro. ")
	//    @ApiResponse(
	//            responseCode = "204",
	//            description = "Environment Updated"
	//    )
	//    @DeleteMapping(value = "/{environmentName}", consumes = "application/json" )
	//    public ResponseEntity<EnvironmentProPostDto> deleteProEnvironment(
	//            @Parameter(
	//                    description = "Environment Name"
	//            ) @PathVariable String environmentName,
	//            @RequestBody EnvironmentProPostDto environmentPostDto
	//    ) {
	//        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	//    }
}
