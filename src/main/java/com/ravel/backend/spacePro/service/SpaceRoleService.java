package com.ravel.backend.spacePro.service;

import com.ravel.backend.appAuth.service.AppRoleService;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spacePro.dto.SpaceProRolePostDto;
import com.ravel.backend.spacePro.model.SpaceRole;
import com.ravel.backend.spacePro.repository.SpaceRoleRepository;
import java.time.OffsetDateTime;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service
public class SpaceRoleService {

	private final SpaceRoleRepository spaceRoleRepository;
	private AppRoleService appRoleService;

	public void addAppRoleToSpaceRoles(SpaceProRolePostDto spaceRolePostDto) {
		appRoleService.validatePurposeSpace(spaceRolePostDto.getAppRoleName());
		validateNewSpaceRole(spaceRolePostDto.getAppRoleName());
		SpaceRole newSpaceRole = new SpaceRole();
		newSpaceRole.setAppRoleName(spaceRolePostDto.getAppRoleName());
		newSpaceRole.setCreated_at(OffsetDateTime.now());
		spaceRoleRepository.save(newSpaceRole);
	}

	public SpaceRole findSpaceRole(String appRoleName) {
		SpaceRole existingSpaceRole = spaceRoleRepository
			.findByAppRoleNameIgnoreCase(appRoleName)
			.orElseThrow(() -> new NotFoundException("SpaceRole not found"));
		return existingSpaceRole;
	}

	private void validateNewSpaceRole(String appRoleName) {
		if (spaceRoleRepository.existsByAppRoleNameIgnoreCase(appRoleName)) {
			throw new AlreadyExistException(
				"Error storing AppRole in SpaceRole Database, SpaceRole already exists in SpaceRole database"
			);
		}
	}
}
