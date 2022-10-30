package com.ravel.backend.appAuth.service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.ravel.backend.appAuth.dto.PermissionGetDto;
import com.ravel.backend.appAuth.dto.PermissionPostDto;
import com.ravel.backend.appAuth.dto.PermissionRoleGetDto;
import com.ravel.backend.appAuth.mappers.AppAuthMapper;
import com.ravel.backend.appAuth.model.AppPermission;
import com.ravel.backend.appAuth.model.AppRole;
import com.ravel.backend.appAuth.repository.AppPermissionRepository;
import com.ravel.backend.appAuth.repository.AppRoleRepository;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Transactional
@Service
@AllArgsConstructor
public class AppPermissionServiceImpl implements AppPermissionService {

	private final AppPermissionRepository appPermissionRepository;
	private final AppRoleRepository appRoleRepository;
	private AppAuthMapper appAuthMapper;

	@Override
	public PermissionGetDto getAppPermission(String appPermissionName) {
		if (
			!appPermissionRepository.existsByAppPermissionNameIgnoreCase(
				appPermissionName
			)
		) throw new NotFoundException("Permission does not exist");
		AppPermission appPermission = appPermissionRepository.findByAppPermissionNameIgnoreCase(
			appPermissionName
		);
		PermissionGetDto permissionGetDto = appAuthMapper.permissionToPermissionGetDto(
			appPermission
		);
		return permissionGetDto;
	}

	@Override
	public PermissionPostDto createNewAppPermission(PermissionPostDto permissionPostDto) {
		validateNewAppPermission(EMPTY, permissionPostDto.getAppPermissionName());
		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionName(permissionPostDto.getAppPermissionName());
		appPermission.setDescription(permissionPostDto.getDescription());
		appPermission.setCreatedAt(new Date());
		appPermissionRepository.save(appPermission);
		return null;
	}

	@Override
	public PermissionPostDto updatePermission(
		String currentPermissionName,
		PermissionPostDto permissionPostDto
	) {
		validateNewAppPermission(
			currentPermissionName,
			permissionPostDto.getAppPermissionName()
		);
		AppPermission currentAppPermission = appPermissionRepository.findByAppPermissionNameIgnoreCase(
			currentPermissionName
		);
		currentAppPermission.setAppPermissionName(
			permissionPostDto.getAppPermissionName()
		);
		currentAppPermission.setDescription(permissionPostDto.getDescription());
		appPermissionRepository.save(currentAppPermission);
		return null;
	}

	@Override
	public AppPermission addRoleToPermission(
		String appPermissionName,
		String appRoleName
	) {
		if (
			!appRoleRepository.existsByAppRoleNameIgnoreCase(appRoleName)
		) throw new NotFoundException(
			"Role with name " + appRoleName + " does not exists"
		);
		if (
			!appPermissionRepository.existsByAppPermissionNameIgnoreCase(
				appPermissionName
			)
		) throw new NotFoundException(
			"Permission with name " + appPermissionName + " does not exist"
		);

		AppRole appRole = appRoleRepository.findByAppRoleNameIgnoreCase(appRoleName);
		AppPermission currentAppPermission = appPermissionRepository.findByAppPermissionNameIgnoreCase(
			appPermissionName
		);
		currentAppPermission.setAppRole(appRole);
		appPermissionRepository.save(currentAppPermission);
		return currentAppPermission;
	}

	@Override
	public List<PermissionGetDto> getAllAppPermissions() {
		List<AppPermission> appPermissionList = appPermissionRepository.findAll();
		List<PermissionGetDto> permissionGetDtoList = appAuthMapper.permissionsToPermissionGetDtoList(
			appPermissionList
		);
		return permissionGetDtoList;
	}

	@Override
	public List<PermissionRoleGetDto> getAllAppPermissionsWithPermissions() {
		return appAuthMapper.permissionToPermissionRolesDto(
			appPermissionRepository.findAll()
		);
	}

	private AppPermission findAppPermission(String appPermissionName) {
		return appPermissionRepository.findByAppPermissionNameIgnoreCase(
			appPermissionName
		);
	}

	private AppPermission validateNewAppPermission(
		String currentAppPermissionName,
		String newAppPermissionName
	) {
		AppPermission permissionByNewName = findAppPermission(newAppPermissionName);
		if (StringUtils.isNotBlank(currentAppPermissionName)) {
			AppPermission currentAppPermission = findAppPermission(
				currentAppPermissionName
			);
			if (currentAppPermission == null) {
				throw new NotFoundException("Permission not found");
			}
			if (
				permissionByNewName != null &&
				!currentAppPermission
					.getAppPermissionName()
					.equals(permissionByNewName.getAppPermissionName())
			) {
				throw new AlreadyExistException("Permission already exists");
			}
			return currentAppPermission;
		} else {
			if (permissionByNewName != null) {
				throw new AlreadyExistException("Permission already exists");
			}
			return null;
		}
	}
}
