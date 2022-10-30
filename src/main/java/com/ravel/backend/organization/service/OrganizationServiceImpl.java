package com.ravel.backend.organization.service;

import com.ravel.backend.organization.dtos.OrganizationAdminGetDto;
import com.ravel.backend.organization.dtos.OrganizationGetDto;
import com.ravel.backend.organization.dtos.OrganizationPostDto;
import com.ravel.backend.organization.mappers.OrganizationMapper;
import com.ravel.backend.organization.model.Organization;
import com.ravel.backend.organization.repository.OrganizationRepository;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Qualifier("organizationService")
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

	private final OrganizationRepository organizationRepository;
	private final OrganizationMapper organizationMapper;

	@Override
	public OrganizationPostDto createOrganization(
		OrganizationPostDto organizationPostDto
	) {
		if (
			organizationRepository.existsByOrganizationNameIgnoreCase(
				organizationPostDto.getOrganizationName()
			)
		) throw new AlreadyExistException(
			"Organization with name " +
			organizationPostDto.getOrganizationName() +
			" already exists"
		);
		Organization newOrganization = new Organization();
		newOrganization.setOrganizationName(organizationPostDto.getOrganizationName());
		newOrganization.setCreatedAt(OffsetDateTime.now());
		newOrganization.setActive(true);
		organizationRepository.save(newOrganization);
		return null;
	}

	@Override
	public OrganizationPostDto updateOrganization(
		String currentOrganizationName,
		OrganizationPostDto OrganizationPostDto
	) {
		validateNewOrganizationName(
			currentOrganizationName,
			OrganizationPostDto.getOrganizationName()
		);
		Organization currentOrganization = organizationRepository.findByOrganizationNameIgnoreCase(
			currentOrganizationName
		);
		currentOrganization.setOrganizationName(
			OrganizationPostDto.getOrganizationName()
		);
		currentOrganization.setUpdatedAt(OffsetDateTime.now());
		organizationRepository.save(currentOrganization);
		return null;
	}

	@Override
	public List<OrganizationAdminGetDto> findAllOrganizations() {
		List<Organization> organizationList = organizationRepository.findAll();
		List<OrganizationAdminGetDto> organizationGetDtoList = organizationMapper.organizationToOrganizationAdminGetDtoList(
			organizationList
		);
		return organizationGetDtoList;
	}

	@Override
	public List<OrganizationGetDto> getAllActiveOrganizations() {
		List<Organization> organizationList = organizationRepository.findAllActiveOrganizations();
		List<OrganizationGetDto> organizationGetDtoList = organizationMapper.organizationToOrganizationGetDtoList(
			organizationList
		);
		return organizationGetDtoList;
	}

	@Override
	public List<OrganizationGetDto> findOrganizationsByUuids(List<UUID> uuids) {
		List<Organization> organizationList = organizationRepository.findByMulIds(uuids);
		List<OrganizationGetDto> organizationGetDtoList = organizationMapper.organizationToOrganizationGetDtoList(
			organizationList
		);
		return organizationGetDtoList;
	}

	@Override
	public Organization deactivateOrganization(String organizationName, Boolean bool) {
		if (
			!organizationRepository.existsByOrganizationNameIgnoreCase(organizationName)
		) throw new NotFoundException(
			"Organization with name " + organizationName + " does not exists"
		);
		Organization deActivatedOrganization = organizationRepository.findByOrganizationNameIgnoreCase(
			organizationName
		);
		deActivatedOrganization.setActive(bool);
		deActivatedOrganization.setUpdatedAt(OffsetDateTime.now());
		organizationRepository.save(deActivatedOrganization);
		return deActivatedOrganization;
	}

	@Override
	public OrganizationGetDto getOrganizationById(UUID organizationId) {
		if (
			!organizationRepository.existsByOrganizationId(organizationId)
		) throw new NotFoundException(
			"Organization with UUID " + organizationId + " does not exists"
		);
		Organization organization = organizationRepository.findByOrganizationIdAndIsActive(
			organizationId,
			true
		);
		OrganizationGetDto organizationGetDto = organizationMapper.organizationToOrganizationGetDto(
			organization
		);
		return organizationGetDto;
	}

	@Override
	public UUID getOrganizationId(String organizationName) {
		Organization organization = organizationRepository
			.findByOrganizationNameIgnoreCaseAndIsActive(organizationName, true)
			.orElseThrow(() -> new NotFoundException("Organization not found"));
		return organization.getOrganizationId();
	}

	@Override
	public OrganizationGetDto getOrganizationByName(String organizationName) {
		if (
			!organizationRepository.existsByOrganizationNameIgnoreCase(organizationName)
		) throw new NotFoundException(
			"Organization with name " + organizationName + " does not exists"
		);
		Organization organization = organizationRepository
			.findByOrganizationNameIgnoreCaseAndIsActive(organizationName, true)
			.orElseThrow(
				() ->
					new NotFoundException(
						"Organization with name:  " + organizationName + " is not active"
					)
			);
		OrganizationGetDto organizationGetDto = organizationMapper.organizationToOrganizationGetDto(
			organization
		);
		return organizationGetDto;
	}

	@Override
	public Organization getOrganizationByName2(String organizationName) {
		if (
			!organizationRepository.existsByOrganizationNameIgnoreCase(organizationName)
		) throw new NotFoundException(
			"Organization with name " + organizationName + " does not exists"
		);
		Organization organization = organizationRepository
			.findByOrganizationNameIgnoreCaseAndIsActive(organizationName, true)
			.orElseThrow(
				() ->
					new NotFoundException(
						"Organization with name:  " + organizationName + " is not active"
					)
			);

		return organization;
	}

	private Organization findOrganizationByName(String organizationName) {
		return organizationRepository.findByOrganizationNameIgnoreCase(organizationName);
	}

	private Organization validateNewOrganizationName(
		String currentOrganizationName,
		String newOrganizationName
	) {
		Organization organizationByNewName = findOrganizationByName(newOrganizationName);
		if (StringUtils.isNotBlank(currentOrganizationName)) {
			Organization currentOrganization = findOrganizationByName(
				currentOrganizationName
			);
			if (currentOrganization == null) {
				throw new NotFoundException(
					"Could not validate current organization with name " +
					currentOrganizationName +
					" does not exist"
				);
			}
			if (
				organizationByNewName != null &&
				!currentOrganization
					.getOrganizationId()
					.equals(organizationByNewName.getOrganizationId())
			) {
				throw new AlreadyExistException(
					"Could not validate organizationname with name " +
					newOrganizationName +
					"because it has been already taken"
				);
			}
			return currentOrganization;
		} else {
			if (organizationByNewName != null) {
				throw new AlreadyExistException(
					"Could not validate organizationname with name " +
					newOrganizationName +
					"because it has been already taken"
				);
			}
			return null;
		}
	}
}
