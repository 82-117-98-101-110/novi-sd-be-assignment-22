package com.ravel.backend.spacePro.controller;

import com.ravel.backend.spacePro.dto.EnvironmentProGetDto;
import com.ravel.backend.spacePro.service.EnvironmentProService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Ravel Pro Environments", description = "Ravel Pro Environments")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "api/v1/spaces/environments/pro/")
@RestController
@AllArgsConstructor
public class EnvironmentProController {

	private EnvironmentProService environmentProService;

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
	@GetMapping(value = "/all")
	public ResponseEntity<List<EnvironmentProGetDto>> getProEnvironments() {
		return ResponseEntity.ok().body(environmentProService.getActivePublicEnvironments());
	}
}
