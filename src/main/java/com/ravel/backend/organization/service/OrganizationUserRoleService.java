package com.ravel.backend.organization.service;

import com.ravel.backend.organization.dtos.DetailedUsersOrganization;
import com.ravel.backend.organization.dtos.GetOrganizationsForUserDto;
import com.ravel.backend.organization.dtos.OrganizationUserRoleOnlyGetDto;
import com.ravel.backend.organization.dtos.OrganizationUsersRoleGetDto;
import com.ravel.backend.organization.model.OrganizationUserRoleId;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;

public interface OrganizationUserRoleService {
	void removeUserFromOrganizationWithUuid(String organizationName, UUID userUuid);

	void setNewInvitedUserActive(UUID organizationId, UUID userUuid);

	//	List<GetOrganizationsForUserDto> getOrganizationsForUser(String userEmail);

	//	List<OrganizationUsersRoleGetDto> getDetailedOrganizationsForUser(String userEmail);

	List<OrganizationUsersRoleGetDto> getDetailedOrganizationsForUserWithUuid(
		UUID userUuid
	);

	OrganizationUserRoleOnlyGetDto getRoleForUserForOrganization(
		String organizationName,
		UUID userUUID
	);

	//TODO make only return accepted
	//TODO replace get user UUID with only active user accounts
	List<GetOrganizationsForUserDto> getOrganizationsForUserUuid(UUID userUuid);

	List<UUID> getOrganizationIdsForUser(UUID userUuid);

	List<UserDetailsGetDto> getUsersFromOrganizationsForUser(UUID userUUID);

	List<DetailedUsersOrganization> getUsersForOrganization(String organizationName);

	OrganizationUserRoleId addUserEmailToOrgWithoutInviteEmail(
		String organizationName,
		String userEmail,
		String organizationRole
	) throws MessagingException;

	OrganizationUserRoleId addUserEmailToOrganizationWithInviteEmail(
		String organizationName,
		String userEmail,
		String organizationRole
	) throws MessagingException;

	//	OrganizationUserRoleId resentOrganizationInvite(
	//		String organizationName,
	//		String userEmail
	//	);

    OrganizationUserRoleId resentOrganizationInvite(
            String organizationName,
            String userEmail
    );

    void acceptOrganizationInvite(String token);

	void updateRole(String organizationName, String userEmail, String organizationRole);

	//TODO replace get user UUID with only active user accounts
	void updateRoleWithUuid(
		String organizationName,
		UUID userUuid,
		String organizationRole
	);

	void removeUserFromOrganization(String organizationName, String userEmail);
}
