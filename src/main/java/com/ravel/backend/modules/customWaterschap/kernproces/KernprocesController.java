package com.ravel.backend.modules.customWaterschap.kernproces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/v1/module/waterschappen/details/kernproces")
@RestController
@Tag(name = "App Module Custom Client", description = "Custom App Moduels for Clients")
public class KernprocesController {

	private final KernprocesService kernprocesService;

	@Autowired
	public KernprocesController(KernprocesService kernprocesService) {
		this.kernprocesService = kernprocesService;
	}

	@Operation(
		summary = "Create a new kernproces",
		description = "## This endpoint wil create a new kernproces for the module Waterschappen\n This is custom module for the organization Waterschappen"
	)
	@PostMapping
	public ResponseEntity<Kernproces> createNewKernproces(
		@RequestParam(
			value = "Kern Process name",
			defaultValue = "Waterveiligheid"
		) String kernProcessName,
		@RequestParam(
			value = "Kernprocess imageURL",
			defaultValue = "https://ravelhubs-1-assets.ravel.cloud/files/768b4fb8-64a8-44c2-8c4f-cf3b8a4d2a54.png"
		) String kernProcessImageUrl
	) {
		Kernproces newKernProces = kernprocesService.createKernproces(
			kernProcessName,
			kernProcessImageUrl
		);
		return ResponseEntity.ok().body(newKernProces);
	}

	@Operation(
		summary = "Get all Kernproces",
		description = "This endpoint wil show all kenrproces"
	)
	@GetMapping
	public List<Kernproces> getAllKernproces() {
		return kernprocesService.findAll();
	}
}
