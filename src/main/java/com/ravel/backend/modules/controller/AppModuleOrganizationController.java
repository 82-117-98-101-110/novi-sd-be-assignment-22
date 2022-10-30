package com.ravel.backend.modules.controller;

import com.ravel.backend.modules.model.AppModuleOrganization;
import com.ravel.backend.modules.service.AppModuleOrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@RequestMapping(path = "api/v1/module/organizations")
@RestController
@Tag(
	name = "Application Modules",
	description = "Creating application modules, assigning them to users"
)
public class AppModuleOrganizationController {

	@Autowired
	private AppModuleOrganizationService appModuleOrganizationService;

	public AppModuleOrganizationController(
		AppModuleOrganizationService appModuleOrganizationService
	) {
		this.appModuleOrganizationService = appModuleOrganizationService;
	}

	@Operation(
		summary = "Add organization to a module with organizationName",
		description = ""
	)
	@PostMapping
	public ResponseEntity<AppModuleOrganization> createNewEntry(
		@RequestParam(
			value = "organizationName",
			defaultValue = "Ravel"
		) String organizationName,
		@RequestParam(
			value = "Module name name",
			defaultValue = "Waterschappen"
		) String moduleName
	) {
		AppModuleOrganization newAppModuleOrganization = appModuleOrganizationService.addModuleToOrganization(
			organizationName,
			moduleName
		);
		return ResponseEntity.accepted().body(newAppModuleOrganization);
	}

	@Operation(
		summary = "Get modules for Organization with organizationName",
		description = ""
	)
	@GetMapping(value = "/{organizationName}")
	public ResponseEntity getModulesForOrganization(
		@Parameter(name = "organizationName", description = "Ravel") @PathVariable(
			"organizationName"
		) String organizationName
	) {
		AppModuleOrganization appModuleOrganization = appModuleOrganizationService.getOrganizationModules(
			organizationName
		);
		return ResponseEntity.ok(appModuleOrganization);
	}
}
