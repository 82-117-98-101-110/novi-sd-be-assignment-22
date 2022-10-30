package com.ravel.backend.appAuth.service;

import com.ravel.backend.appAuth.dto.PermissionGetDto;
import com.ravel.backend.appAuth.dto.PermissionPostDto;
import com.ravel.backend.appAuth.dto.PermissionRoleGetDto;
import com.ravel.backend.appAuth.model.AppPermission;
import java.util.List;

public interface AppPermissionService {
	PermissionGetDto getAppPermission(String appPermissionName);

	PermissionPostDto createNewAppPermission(PermissionPostDto permissionPostDto);

	PermissionPostDto updatePermission(
		String currentPermissionName,
		PermissionPostDto permissionPostDto
	);

	AppPermission addRoleToPermission(String appPermissionName, String appRoleName);

	List<PermissionGetDto> getAllAppPermissions();

	List<PermissionRoleGetDto> getAllAppPermissionsWithPermissions();
}
