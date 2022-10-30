package com.ravel.backend.organization.mappers;

import com.ravel.backend.organization.dtos.*;
import com.ravel.backend.organization.model.Organization;
import com.ravel.backend.organization.model.OrganizationUserRole;
import com.ravel.backend.users.model.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
	List<DetailedUsersOrganization> entityToDetailedUsersOrganizationList(
		List<User> organizationUserRoles
	);

	OrganizationUserRoleOnlyGetDto entityToOrganizationUserRoleOnlyGetDto(
		OrganizationUserRole organizationUserRole
	);

	List<GetOrganizationsForUserDto> organizationToUserOrganizationsGetDtoList(
		List<Organization> organization
	);

	List<OrganizationUsersRoleGetDto> organizationListToOrganizationUsersRoleGetDtoList(
		List<OrganizationUserRole> organizationUserRole
	);

	OrganizationGetDto organizationToOrganizationGetDto(Organization organization);

	List<OrganizationGetDto> organizationToOrganizationGetDtoList(
		List<Organization> organization
	);
	List<OrganizationAdminGetDto> organizationToOrganizationAdminGetDtoList(
		List<Organization> organization
	);
}
