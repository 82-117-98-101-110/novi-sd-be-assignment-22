package com.ravel.backend.spaceLite.service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spaceLite.dto.SpaceLiteGetDto;
import com.ravel.backend.spaceLite.dto.SpaceLiteMapper;
import com.ravel.backend.spaceLite.dto.SpaceLitePostDto;
import com.ravel.backend.spaceLite.model.SpaceLite;
import com.ravel.backend.spaceLite.repository.SpaceLiteRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpaceLiteService {

	private final SpaceLiteRepository spaceLiteRepository;
	private IAuthenticationFacade authenticationFacade;
	private OrganizationUserRoleService organizationUserRoleService;
	private SpaceLiteOrganizationService spaceLiteOrganizationService;
	private SpaceLiteMapper spaceLiteMapper;

	public SpaceLiteGetDto createNewSpaceLite(SpaceLitePostDto spaceLitePostDto) {
		validateNewSpaceLite(EMPTY, spaceLitePostDto.getName());
		SpaceLite spaceLite = new SpaceLite();
		spaceLite.setName(spaceLitePostDto.getName());
		spaceLite.setCreated_at(OffsetDateTime.now());
		spaceLite.setSrc(spaceLitePostDto.getSrc());
		spaceLite.setRoomCode(spaceLitePostDto.getRoomCode());
		spaceLite.setInviteLink(spaceLitePostDto.getInviteLink());
		spaceLite.setEmbeddedCode(spaceLitePostDto.getEmbeddedCode());
		spaceLite.setCreated_at(OffsetDateTime.now());
		spaceLite.setActive(true);
		spaceLiteRepository.save(spaceLite);
		SpaceLiteGetDto spaceLiteGetDto = spaceLiteMapper.spaceLiteToSpaceLiteDto(
			spaceLite
		);
		return spaceLiteGetDto;
	}

	public List<SpaceLiteGetDto> getSpacesForLite() {
		List<SpaceLite> spaceLiteList = spaceLiteRepository.findAll();
		return spaceLiteMapper.spaceLiteListToSpaceLiteDtoSet(spaceLiteList);
	}

	public SpaceLiteGetDto updateSpaceLite(
		String currentSpaceLiteName,
		SpaceLitePostDto spaceLitePostDto
	) {
		validateNewSpaceLite(currentSpaceLiteName, spaceLitePostDto.getName());
		SpaceLite spaceLite = spaceLiteRepository
			.findByNameIgnoreCaseAndIsActive(currentSpaceLiteName, true)
			.orElseThrow(() -> new NotFoundException("Space for Ravel Lite not found"));
		spaceLiteMapper.updateSpaceLiteFromSpaceLiteDto(spaceLitePostDto, spaceLite);
		spaceLite.setUpdatedAt(OffsetDateTime.now());
		spaceLiteRepository.save(spaceLite);
		SpaceLiteGetDto spaceLiteGetDto = spaceLiteMapper.spaceLiteToSpaceLiteDto(
			spaceLite
		);
		return spaceLiteGetDto;
	}

	public SpaceLiteGetDto getActiveSpaceLiteDto(String spaceLiteName) {
		SpaceLite spaceLite = spaceLiteRepository
			.findByNameIgnoreCaseAndIsActive(spaceLiteName, true)
			.orElseThrow(() -> new NotFoundException("Space for Ravel Lite not found"));
		SpaceLiteGetDto spaceLiteGetDto = spaceLiteMapper.spaceLiteToSpaceLiteDto(
			spaceLite
		);
		return spaceLiteGetDto;
	}

	public SpaceLite getActiveSpaceLite(String spaceLiteName) {
		SpaceLite spaceLite = spaceLiteRepository
			.findByNameIgnoreCaseAndIsActive(spaceLiteName, true)
			.orElseThrow(() -> new NotFoundException("Space for Ravel Lite not found"));
		return spaceLite;
	}

	public Set<SpaceLiteGetDto> getSpaceLiteForUser() {
		Authentication authentication = authenticationFacade.getAuthentication();
		UUID userUuid = UUID.fromString(authentication.getPrincipal().toString());
		List<UUID> organizationIdList = organizationUserRoleService.getOrganizationIdsForUser(
			userUuid
		);
		List<Set<SpaceLite>> source = spaceLiteOrganizationService.getSpacesLiteForOrganizations(
			organizationIdList
		);

		Set<SpaceLite> collection = Sets.newHashSet();
		source
			.stream()
			.forEach(
				one -> {
					for (Set<SpaceLite> e : source) {
						Iterables.addAll(collection, e);
					}
				}
			);
		Set<SpaceLiteGetDto> spaceLiteGetDto = spaceLiteMapper.spaceLiteSetToSpaceLiteDtoSet(
			collection
		);
		return spaceLiteGetDto;
	}

	private SpaceLite findSpaceLiteByName(String spaceLiteName) {
		return spaceLiteRepository.findByName(spaceLiteName);
	}

	private SpaceLite validateNewSpaceLite(String currentName, String newName) {
		SpaceLite objectByNewName = findSpaceLiteByName(newName);
		if (StringUtils.isNotBlank(currentName)) {
			SpaceLite currentObject = findSpaceLiteByName(currentName);
			if (currentObject == null) {
				throw new NotFoundException(
					"Could not validate current with name " +
					currentName +
					" does not exist"
				);
			}
			if (
				objectByNewName != null &&
				!currentObject.getId().equals(objectByNewName.getId())
			) {
				throw new AlreadyExistException(
					"Could not validate with name " +
					newName +
					"because it has been already taken"
				);
			}
			return currentObject;
		} else {
			if (objectByNewName != null) {
				throw new AlreadyExistException(
					"Could not validate with name " +
					newName +
					"because it has been already taken"
				);
			}
			return null;
		}
	}
	//remove space
}
