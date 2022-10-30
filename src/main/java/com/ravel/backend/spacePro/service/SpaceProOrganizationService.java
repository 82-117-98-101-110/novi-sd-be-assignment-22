package com.ravel.backend.spacePro.service;

import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spacePro.model.SpacePro;
import com.ravel.backend.spacePro.model.SpaceProOrganization;
import com.ravel.backend.spacePro.repository.SpaceProOrganizationRepository;
import com.ravel.backend.spacePro.repository.SpaceProRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpaceProOrganizationService {

	private final SpaceProOrganizationRepository spaceProOrganizationRepository;
	private final SpaceProRepository spaceProRepository;
	private OrganizationService organizationService;

	public SpaceProOrganization addProSpaceToOrg(
		String organizationName,
		UUID spaceProUUID
	) {
		UUID organizationId = organizationService.getOrganizationId(organizationName);
		if (
			!spaceProOrganizationRepository.existsByOrganizationIdAndIsActive(
				organizationId,
				true
			)
		) {
			SpaceProOrganization newSpaceProOrganization = addNewOrgToProSpace(
				organizationId,
				spaceProUUID
			);
			return newSpaceProOrganization;
		}
		SpaceProOrganization existingSpaceProOrganization = addExistingOrgToProSpace(
			organizationId,
			spaceProUUID
		);
		return existingSpaceProOrganization;
	}

	private SpaceProOrganization addExistingOrgToProSpace(
		UUID organizationId,
		UUID spaceProUUID
	) {
		SpacePro spacePro = spaceProRepository
			.findBySpaceUuidAndIsActive(spaceProUUID, true)
			.orElseThrow(() -> new NotFoundException("Ravel Space Pro not found"));
		SpaceProOrganization spaceProOrganization = spaceProOrganizationRepository.findByOrganizationIdAndIsActive(
			organizationId,
			true
		);
		spaceProOrganization.getSpacePros().add(spacePro);
		spaceProOrganizationRepository.save(spaceProOrganization);
		return spaceProOrganization;
	}

	private SpaceProOrganization addNewOrgToProSpace(
		UUID organizationId,
		UUID spaceProUUID
	) {
		SpacePro spacePro = spaceProRepository
			.findBySpaceUuidAndIsActive(spaceProUUID, true)
			.orElseThrow(() -> new NotFoundException("Ravel Space Pro not found"));
		SpaceProOrganization newSpaceProOrganization = new SpaceProOrganization();
		newSpaceProOrganization.setOrganizationId(organizationId);
		newSpaceProOrganization.setActive(true);
		newSpaceProOrganization.setCreated_at(OffsetDateTime.now());
		newSpaceProOrganization.getSpacePros().add(spacePro);
		spaceProOrganizationRepository.save(newSpaceProOrganization);
		return newSpaceProOrganization;
	}
}
