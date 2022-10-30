package com.ravel.backend.spaceLite.service;

import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spaceLite.model.SpaceLite;
import com.ravel.backend.spaceLite.model.SpaceLiteOrganization;
import com.ravel.backend.spaceLite.repository.SpaceLiteOrganizationRepository;
import com.ravel.backend.spaceLite.repository.SpaceLiteRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpaceLiteOrganizationService {

	private final SpaceLiteOrganizationRepository spaceLiteOrganizationRepository;
	private final SpaceLiteRepository spaceLiteRepository;
	private IAuthenticationFacade authenticationFacade;
	private OrganizationUserRoleService organizationUserRoleService;
	private OrganizationService organizationService;

	public List<Set<SpaceLite>> getSpacesLiteForOrganizations(
		List<UUID> organizationIdList
	) {
		List<SpaceLiteOrganization> spaceLiteOrganizations = spaceLiteOrganizationRepository.findByOrganizationIdAndIsActive(
			organizationIdList
		);

		List<Set<SpaceLite>> listOfArrays = spaceLiteOrganizations
			.stream()
			.map(SpaceLiteOrganization::getSpaceLites)
			.collect(Collectors.toList());
		return listOfArrays;
	}

	public SpaceLiteOrganization addSpaceLiteToOrganization(
		String organizationName,
		String spaceLiteName
	) {
		UUID organizationId = organizationService.getOrganizationId(organizationName);
		if (
			!spaceLiteOrganizationRepository.existsByOrganizationIdAndIsActive(
				organizationId,
				true
			)
		) {
			addNewOrgToSpaceLite(organizationId, spaceLiteName);
			return null;
		}
		addExistingOrgToSpaceLite(organizationId, spaceLiteName);
		return null;
	}

	private SpaceLiteOrganization addExistingOrgToSpaceLite(
		UUID organizationId,
		String spaceLiteName
	) {
		SpaceLiteOrganization spaceLiteOrganization = spaceLiteOrganizationRepository
			.findByOrganizationIdAndIsActiveSingle(organizationId, true)
			.orElseThrow(() -> new NotFoundException("Space for Ravel Lite not found"));
		SpaceLite spaceLite = spaceLiteRepository
			.findByNameIgnoreCaseAndIsActive(spaceLiteName, true)
			.orElseThrow(() -> new NotFoundException("Space for Ravel Lite not found"));
		spaceLiteOrganization.setUpdatedAt(OffsetDateTime.now());
		spaceLiteOrganization.getSpaceLites().add(spaceLite);
		spaceLiteOrganizationRepository.save(spaceLiteOrganization);
		return null;
	}

	private SpaceLiteOrganization addNewOrgToSpaceLite(
		UUID organizationId,
		String spaceLiteName
	) {
		SpaceLite spaceLite = spaceLiteRepository
			.findByNameIgnoreCaseAndIsActive(spaceLiteName, true)
			.orElseThrow(() -> new NotFoundException("Space for Ravel Lite not found"));
		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setOrganizationId(organizationId);
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setCreated_at(OffsetDateTime.now());
		spaceLiteOrganization.getSpaceLites().add(spaceLite);
		spaceLiteOrganizationRepository.save(spaceLiteOrganization);

		return null;
	}
}
