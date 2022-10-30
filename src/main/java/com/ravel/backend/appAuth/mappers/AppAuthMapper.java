package com.ravel.backend.appAuth.mappers;

import com.ravel.backend.appAuth.dto.PermissionGetDto;
import com.ravel.backend.appAuth.dto.PermissionRoleGetDto;
import com.ravel.backend.appAuth.dto.RoleGetDto;
import com.ravel.backend.appAuth.dto.RolePermissionGetDto;
import com.ravel.backend.appAuth.model.AppPermission;
import com.ravel.backend.appAuth.model.AppRole;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppAuthMapper {
	PermissionGetDto permissionToPermissionGetDto(AppPermission appPermission);

	List<PermissionGetDto> permissionsToPermissionGetDtoList(
		List<AppPermission> appPermissionList
	);

	RoleGetDto roleToRoleGetDto(AppRole appRole);

	List<RoleGetDto> roleToRoleGetDtoList(List<AppRole> appRole);

	List<RolePermissionGetDto> roleToRolesPermissionsDto(List<AppRole> appRoleList);

	List<PermissionRoleGetDto> permissionToPermissionRolesDto(
		List<AppPermission> appPermissionList
	);
}
