package com.ravel.backend.organization.service;

import com.ravel.backend.organization.dtos.OrganizationAdminGetDto;
import com.ravel.backend.organization.dtos.OrganizationGetDto;
import com.ravel.backend.organization.dtos.OrganizationPostDto;
import com.ravel.backend.organization.model.Organization;
import java.util.List;
import java.util.UUID;

public interface OrganizationService {
	OrganizationPostDto createOrganization(OrganizationPostDto OrganizationPostDto);

	OrganizationPostDto updateOrganization(
		String currentOrganizationName,
		OrganizationPostDto OrganizationPostDto
	);

	List<OrganizationAdminGetDto> findAllOrganizations();

	OrganizationGetDto getOrganizationById(UUID organizationId);

	UUID getOrganizationId(String organizationName);

	OrganizationGetDto getOrganizationByName(String organizationName);

	List<OrganizationGetDto> getAllActiveOrganizations();

	List<OrganizationGetDto> findOrganizationsByUuids(List<UUID> uuids);

	Organization deactivateOrganization(String organizationName, Boolean bool);

	Organization getOrganizationByName2(String organizationName);
}
