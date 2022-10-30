package com.ravel.backend.appAuth.service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.ravel.backend.appAuth.dto.RoleGetDto;
import com.ravel.backend.appAuth.dto.RolePermissionGetDto;
import com.ravel.backend.appAuth.dto.RolePostDto;
import com.ravel.backend.appAuth.enumeration.RoleName;
import com.ravel.backend.appAuth.enumeration.RolePurpose;
import com.ravel.backend.appAuth.mappers.AppAuthMapper;
import com.ravel.backend.appAuth.model.AppRole;
import com.ravel.backend.appAuth.repository.AppRoleRepository;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Transactional
@Service
@AllArgsConstructor
public class AppRoleServiceImpl implements AppRoleService {

	private final AppRoleRepository appRoleRepository;
	private AppAuthMapper appAuthMapper;

	@Override
	public RolePostDto createNewAppRole(RolePostDto rolePostDto) {
		validateNewAppRole(EMPTY, rolePostDto.getAppRoleName());
		AppRole appRole = new AppRole();
		appRole.setAppRoleName(rolePostDto.getAppRoleName());
		appRole.setDescription(rolePostDto.getDescription());
		appRole.setCreatedAt(new Date());
		appRole.setPurpose(getPurposeEnumName(rolePostDto.getPurpose()).name());
		appRoleRepository.save(appRole);
		if (appRole.getPurpose() == String.valueOf(RolePurpose.SPACE)) {
			System.out.println("yes");
		}
		return null;
	}

	@Override
	public RolePostDto updateRole(String currentAppRoleName, RolePostDto rolePostDto) {
		validateNewAppRole(currentAppRoleName, rolePostDto.getAppRoleName());
		AppRole currentAppRole = appRoleRepository.findByAppRoleNameIgnoreCase(
			currentAppRoleName
		);
		currentAppRole.setDescription(rolePostDto.getDescription());
		currentAppRole.setPurpose(getPurposeEnumName(rolePostDto.getPurpose()).name());
		currentAppRole.setUpdatedAt(new Date());
		appRoleRepository.save(currentAppRole);
		return null;
	}

	@Override
	public RoleGetDto getAppRole(String appRoleName) {
		if (
			!appRoleRepository.existsByAppRoleNameIgnoreCase(appRoleName)
		) throw new NotFoundException("Role " + appRoleName + " does not exist");
		AppRole appRole = appRoleRepository.findByAppRoleNameIgnoreCase(appRoleName);
		RoleGetDto roleGetDto = appAuthMapper.roleToRoleGetDto(appRole);
		return roleGetDto;
	}

	@Override
	public RoleGetDto getAppRoleById(Long appRoleId) {
		if (!appRoleRepository.existsByAppRoleId(appRoleId)) throw new NotFoundException(
			"Role does not exist"
		);
		AppRole appRole = appRoleRepository.findByAppRoleId(appRoleId);
		RoleGetDto roleGetDto = appAuthMapper.roleToRoleGetDto(appRole);
		return roleGetDto;
	}

	@Override
	public List<RoleGetDto> getAllAppRole() {
		List<AppRole> appRoleList = appRoleRepository.findAll();
		List<RoleGetDto> roleGetDtoList = appAuthMapper.roleToRoleGetDtoList(appRoleList);
		return roleGetDtoList;
	}

	@Override
	public List<RolePermissionGetDto> getAllAppRolesWithPermissions() {
		return appAuthMapper.roleToRolesPermissionsDto(appRoleRepository.findAll());
	}

	@Override
	public List<RolePermissionGetDto> findRolesByIds(List<Long> ids) {
		List<AppRole> appRoleList = appRoleRepository.findByIds(ids);
		List<RolePermissionGetDto> rolePermissionGetDtoList = appAuthMapper.roleToRolesPermissionsDto(
			appRoleList
		);
		return rolePermissionGetDtoList;
	}

	@Override
	public AppRole validatePurposeOrganization(String appRoleName) {
		if (
			!appRoleRepository.existsByAppRoleNameAndPurpose(
				appRoleName,
				String.valueOf(RolePurpose.ORGANIZATION)
			)
		) throw new NotFoundException(
			"Role " +
			appRoleName +
			" does not exist or does not have purpose ORGANIZATION"
		);
		return null;
	}

	@Override
	public AppRole validatePurposeSpace(String appRoleName) {
		if (
			!appRoleRepository.existsByAppRoleNameAndPurpose(
				appRoleName,
				String.valueOf(RolePurpose.SPACE)
			)
		) throw new NotFoundException(
			"Role " + appRoleName + " does not exist or does not have purpose SPACE"
		);
		return null;
	}

	private RoleName getRoleEnumName(String roleName) {
		RoleName validateRoleName = RoleName.valueOf(roleName.toUpperCase(Locale.ROOT));
		return validateRoleName;
	}

	private RolePurpose getPurposeEnumName(String purpose) {
		RolePurpose validatedPurpose = RolePurpose.valueOf(
			purpose.toUpperCase(Locale.ROOT)
		);
		return validatedPurpose;
	}

	private AppRole findAppRoleByName(String appRoleName) {
		return appRoleRepository.findByAppRoleNameIgnoreCase(appRoleName);
	}

	private AppRole validateNewAppRole(String currentAppRoleName, String newAppRoleName) {
		AppRole roleByNewName = findAppRoleByName(newAppRoleName);
		if (StringUtils.isNotBlank(currentAppRoleName)) {
			AppRole currentAppRole = findAppRoleByName(currentAppRoleName);
			if (currentAppRole == null) {
				throw new NotFoundException("Role not found");
			}
			if (
				roleByNewName != null &&
				!currentAppRole.getAppRoleName().equals(roleByNewName.getAppRoleName())
			) {
				throw new AlreadyExistException("Role already exists");
			}
			return currentAppRole;
		} else {
			if (roleByNewName != null) {
				throw new AlreadyExistException("Role already exists");
			}
			return null;
		}
	}
}
