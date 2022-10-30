package com.ravel.backend.modules.customWaterschap.waterschap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/v1/module/waterschappen/details/waterschap")
@RestController
@Tag(name = "App Module Custom Client", description = "Custom App Moduels for Clients")
public class WaterschapController {

	private final WaterschapService waterschapService;

	@Autowired
	public WaterschapController(WaterschapService waterschapService) {
		this.waterschapService = waterschapService;
	}

	@Operation(
		summary = "Create a new waterschap",
		description = "## This endpoint wil create a new waterschap for the module Waterschappen\n This is custom module for the organization Waterschappen"
	)
	@PostMapping
	public ResponseEntity<Waterschap> createNewWaterschap(
		@RequestParam(
			value = "waterschap name",
			defaultValue = "Amstel Gooi en Vecht"
		) String waterschapName,
		@RequestParam(
			value = "waterschap imageURL",
			defaultValue = "https://ravelhubs-1-assets.ravel.cloud/files/768b4fb8-64a8-44c2-8c4f-cf3b8a4d2a54.png"
		) String waterschapImageUrl
	) {
		Waterschap newWaterschap = waterschapService.createWaterschap(
			waterschapName,
			waterschapImageUrl
		);
		return ResponseEntity.ok().body(newWaterschap);
	}

	@Operation(
		summary = "Get all waterschap",
		description = "This endpoint wil show all waterschappen"
	)
	@GetMapping
	public List<Waterschap> getAllKernproces() {
		return waterschapService.findAll();
	}
}
