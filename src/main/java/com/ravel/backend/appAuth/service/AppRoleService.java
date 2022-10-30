package com.ravel.backend.appAuth.service;

import com.ravel.backend.appAuth.dto.RoleGetDto;
import com.ravel.backend.appAuth.dto.RolePermissionGetDto;
import com.ravel.backend.appAuth.dto.RolePostDto;
import com.ravel.backend.appAuth.model.AppRole;
import java.util.List;

public interface AppRoleService {
	RolePostDto createNewAppRole(RolePostDto rolePostDto);

	RolePostDto updateRole(String currentAppRoleName, RolePostDto rolePostDto);

	RoleGetDto getAppRole(String appRoleName);

	RoleGetDto getAppRoleById(Long appRoleId);

	List<RoleGetDto> getAllAppRole();

	List<RolePermissionGetDto> getAllAppRolesWithPermissions();

	List<RolePermissionGetDto> findRolesByIds(List<Long> ids);

	AppRole validatePurposeOrganization(String appRoleName);

	AppRole validatePurposeSpace(String appRoleName);
}
