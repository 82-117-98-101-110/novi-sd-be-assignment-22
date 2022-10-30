package com.ravel.backend.modules.controller;

import com.ravel.backend.modules.dto.CreateModuleDto;
import com.ravel.backend.modules.model.AppModule;
import com.ravel.backend.modules.service.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "api/v1/module")
@RestController
@Tag(
	name = "App Module",
	description = "Creating modules, assigning them to organizations & users"
)
public class ModuleController {

	@Autowired
	private final ModuleService moduleService;

	@Autowired
	public ModuleController(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	@Operation(
		summary = "Get active Modules",
		description = "This endpoint wil show all active Modules"
	)
	@GetMapping(value = "/active")
	public List<AppModule> getModules() {
		return moduleService.findActiveModules();
	}

	@Operation(
		summary = "Get all Modules",
		description = "This endpoint wil show all active and non-active Modules\n ## THIS ENDPOINT IS FOR NOT MEANT TO BE USED BY THE UNITY APPLICATION AND IS ONLY TEMPORARY ACCESSIBLE"
	)
	@GetMapping(value = "/admin")
	public List<AppModule> getAllModules() {
		return moduleService.findAllModules();
	}

	@Operation(
		summary = "Get details for given appModule by name",
		description = "## This endpoint wil provide the details for a given appModule name\n Only active modules will be shown, following data will be included:\n <br/>  \n * ModuleWaterschappen Name\n * ModuleWaterschappen Description\n * ModuleWaterschappen Image URL\n * If appModule is Active"
	)
	@GetMapping(value = "/active/{moduleName}")
	public ResponseEntity<AppModule> findActiveModule(
		@Parameter(description = "Module name", example = "Waterschappen") @PathVariable(
			"moduleName"
		) String moduleName
	) {
		return ResponseEntity.ok().body(moduleService.findActiveModule(moduleName));
	}

	@Operation(
		summary = "Get details for given appModule by name",
		description = "## This endpoint wil provide the details for a given appModule name\n Active as not-active modules are included, following data will be included:\n <br/>  \n * ModuleWaterschappen Name\n * ModuleWaterschappen Description\n * ModuleWaterschappen Image URL\n * If appModule is Active"
	)
	@GetMapping(value = "/admin/{moduleName}")
	public ResponseEntity<AppModule> getModuleByName(
		@Parameter(description = "Module name", example = "Waterschappen") @PathVariable(
			"moduleName"
		) String moduleName
	) {
		return ResponseEntity.ok().body(moduleService.getModule(moduleName));
	}

	@Operation(
		summary = "Create a new module",
		description = "## This endpoint wil create a new active module\n Text"
	)
	@PostMapping(value = "/admin")
	public ResponseEntity<AppModule> createNewActiveModule(
		@RequestBody CreateModuleDto createModuleDto
	) {
		AppModule newAppModule = moduleService.createActiveModule(createModuleDto);
		return ResponseEntity.ok().body(newAppModule);
	}

	@Operation(
		summary = "Upload module Image",
		description = "## Upload a image for a module or update the existing image\n Text"
	)
	@PostMapping(
		value = "/admin/{moduleName}/image",
		consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
	)
	public ResponseEntity<AppModule> uploadModuleImage(
		@Parameter(description = "Module Name", example = "Waterschappen") @PathVariable(
			"moduleName"
		) String moduleName,
		@RequestPart(value = "Module Image") MultipartFile moduleImage
	) {
		moduleService.uploadModuleImage(moduleName, moduleImage);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(
		summary = "Delete appModule by name",
		description = "This endpoint wil delete the appModule"
	)
	@DeleteMapping(path = "/admin/{moduleName}")
	public void module(
		@Parameter(description = "Module Name", example = "Waterschappen") @PathVariable(
			"moduleName"
		) String moduleName
	) {
		moduleService.deleteModule(moduleName);
	}

	@Operation(
		summary = "Deactivate module",
		description = "## This endpoint will deactivate an active module\n Text"
	)
	@PutMapping(value = "/admin/{moduleName}")
	public ResponseEntity<AppModule> deactivateModule(
		@Parameter(description = "Module name", example = "Waterschappen") @PathVariable(
			"moduleName"
		) String moduleName,
		Boolean bool
	) {
		return ResponseEntity.ok().body(moduleService.deactivateModule(moduleName, bool));
	}
}
