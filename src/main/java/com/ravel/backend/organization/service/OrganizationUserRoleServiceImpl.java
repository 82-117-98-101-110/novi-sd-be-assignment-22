package com.ravel.backend.organization.service;

import com.ravel.backend.appAuth.service.AppRoleService;
import com.ravel.backend.organization.dtos.DetailedUsersOrganization;
import com.ravel.backend.organization.dtos.GetOrganizationsForUserDto;
import com.ravel.backend.organization.dtos.OrganizationUserRoleOnlyGetDto;
import com.ravel.backend.organization.dtos.OrganizationUsersRoleGetDto;
import com.ravel.backend.organization.mappers.OrganizationMapper;
import com.ravel.backend.organization.model.Organization;
import com.ravel.backend.organization.model.OrganizationUserRole;
import com.ravel.backend.organization.model.OrganizationUserRoleId;
import com.ravel.backend.organization.repository.OrganizationRepository;
import com.ravel.backend.organization.repository.OrganizationUserRoleRepository;
import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.BadRequestException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.service.UserService;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class OrganizationUserRoleServiceImpl implements OrganizationUserRoleService {

    private OrganizationService organizationService;
    private AppRoleService appRoleService;
    private final OrganizationRepository organizationRepository;
    private final OrganizationUserRoleRepository organizationUserRoleRepository;
    private UserService userService;
    private OrganizationMapper organizationMapper;
    private OrganizationInviteService organizationInviteService;
	private IAuthenticationFacade authenticationFacade;


	@Autowired
    public OrganizationUserRoleServiceImpl(
			OrganizationService organizationService,
			AppRoleService appRoleService,
			OrganizationRepository organizationRepository,
			OrganizationUserRoleRepository organizationUserRoleRepository,
			@Lazy UserService userService, //circular dependencies https://www.baeldung.com/circular-dependencies-in-spring
			OrganizationMapper organizationMapper,
			OrganizationInviteService organizationInviteService,
			IAuthenticationFacade authenticationFacade) {
        this.organizationService = organizationService;
        this.appRoleService = appRoleService;
        this.organizationRepository = organizationRepository;
        this.organizationUserRoleRepository = organizationUserRoleRepository;
        this.userService = userService;
        this.organizationMapper = organizationMapper;
        this.organizationInviteService = organizationInviteService;
		this.authenticationFacade = authenticationFacade;
	}

    @Override
    public OrganizationUserRoleId addUserEmailToOrgWithoutInviteEmail(
            String organizationName,
            String userEmail,
            String organizationRole
    ) {
        Organization organization = getOrganization(organizationName);
        UUID userUuid = getUserUuidForEmail(userEmail);
        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuid
        );

        if (organizationUserRoleRepository.existsById(id)) {
            throw new AlreadyExistException("Users is already added to organization");
        }
        addUserToOrganizationWithRole(organization, organizationRole, id, userUuid, true);
        return id;
    }

    @Override
    public OrganizationUserRoleId addUserEmailToOrganizationWithInviteEmail(
            String organizationName,
            String userEmail,
            String organizationRole
    ) {

        Organization organization = getOrganization(organizationName);
        appRoleService.validatePurposeOrganization(organizationRole);

        isUserAllowedToInvite(organization);

        if (!userService.existsByEmailAndIsActive(userEmail)) {
            UUID uuidNewUser = userService.inviteNewUserForOrg(
                    userEmail,
                    organization.getOrganizationId(),
                    organizationName
            );
            OrganizationUserRoleId id = new OrganizationUserRoleId(
                    organization.getOrganizationId(),
                    uuidNewUser
            );
            addUserToOrganizationWithRole(
                    organization,
                    organizationRole,
                    id,
                    uuidNewUser,
                    false
            );
        } else {
            UUID userUuid = getUserUuidForEmail(userEmail);
            OrganizationUserRoleId id = new OrganizationUserRoleId(
                    organization.getOrganizationId(),
                    userUuid
            );
            if (organizationUserRoleRepository.findById(id).isPresent()) {
                throw new AlreadyExistException("User is already added to organization");
            }
            inviteExistingUserToOrganization(
                    organization,
                    organizationRole,
                    id,
                    userEmail,
                    userUuid
            );
            return id;
        }
        return null;
    }

    	@Override
    	public OrganizationUserRoleId resentOrganizationInvite(
                String organizationName,
                String userEmail
    	) {
    		Organization organization = getOrganization(organizationName);
    		UUID userUuid = getUserUuidForEmail(userEmail);
    		OrganizationUserRoleId id = new OrganizationUserRoleId(
    			organization.getOrganizationId(),
    			userUuid
    		);
    		organizationInviteService.resendOrganizationInviteExistingUser(
    			userEmail,
    			organizationName,
    			organization.getOrganizationId(),
    			userUuid
    		);
    		return id;
    	}

    @Override
    public void acceptOrganizationInvite(String token) {
        OrganizationUserRoleId id = organizationInviteService.confirmToken(token);
        setUserActive(id);
    }

    //TODO remove this method after switching to UUID based role update
    @Override
    public void updateRole(
            String organizationName,
            String userEmail,
            String organizationRole
    ) {
        Organization organization = organizationService.getOrganizationByName2(
                organizationName
        );
        UUID userUuid = getUserUuidForEmail(userEmail);


        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuid
        );
        if (!organizationUserRoleRepository.existsById(id)) {
            throw new AlreadyExistException(
                    "Users with email " +
                            userEmail +
                            " does not belong to " +
                            organizationName
            );
        }
        OrganizationUserRole existingOrganizationUserRole = organizationUserRoleRepository
                .findById(id)
                .orElse(null);
        appRoleService.validatePurposeOrganization(organizationRole);
        existingOrganizationUserRole.setOrganizationRole(organizationRole);
        existingOrganizationUserRole.setUpdatedAt(OffsetDateTime.now());
        organizationUserRoleRepository.save(existingOrganizationUserRole);
    }

    @Override
    public void updateRoleWithUuid(
            String organizationName,
            UUID userUuid,
            String organizationRole
    ) {
        appRoleService.validatePurposeOrganization(organizationRole);


        Organization organization = organizationService.getOrganizationByName2(
                organizationName
        );
        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuid
        );


        OrganizationUserRole existingOrganizationUserRole = organizationUserRoleRepository
                .findById(id).orElseThrow(
                        () -> new NotFoundException(" User does not belong to " + organizationName)
                );
        isUserAllowedToChangeRole(organization, organizationRole);

        existingOrganizationUserRole.setOrganizationRole(organizationRole);
        existingOrganizationUserRole.setUpdatedAt(OffsetDateTime.now());
        organizationUserRoleRepository.save(existingOrganizationUserRole);
    }



    //TODO remove this method after switching to UUID based role update
    @Override
    public void removeUserFromOrganization(String organizationName, String userEmail) {
        Organization organization = getOrganization(organizationName);
        UUID userUuid = getUserUuidForEmail(userEmail);
        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuid
        );
        if (!organizationUserRoleRepository.existsById(id)) {
            throw new AlreadyExistException(
                    "Users with email " +
                            userEmail +
                            " does not belong to " +
                            organizationName
            );
        }
        organizationUserRoleRepository.deleteById(id);
    }

    @Override
    public void removeUserFromOrganizationWithUuid(
            String organizationName,
            UUID userUuid
    ) {

        Organization organization = getOrganization(organizationName);

//        Authentication authentication = authenticationFacade.getAuthentication();
//        UUID userUuidRequester = UUID.fromString(authentication.getPrincipal().toString());
//        OrganizationUserRoleId id = new OrganizationUserRoleId(
//                organization.getOrganizationId(),
//                userUuidRequester
//        );
//        OrganizationUserRole existingOrganizationUserRole = organizationUserRoleRepository
//                .findById(id).orElseThrow(
//                        () -> new BadRequestException("You do not belong to the organization")
//                );

        if(!isUserAllowedToRemove(organization)) {
            throw new BadRequestException("You're not allowed to remove user from organization");

        }
        OrganizationUserRoleId idToDelete = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuid
        );


        if (!organizationUserRoleRepository.existsById(idToDelete)) {
            throw new AlreadyExistException(
                    "User does not belong to " + organizationName
            );
        }
        organizationUserRoleRepository.deleteById(idToDelete);

    }

    @Override
    public void setNewInvitedUserActive(UUID organizationId, UUID userUuid) {
        OrganizationUserRoleId id = new OrganizationUserRoleId(organizationId, userUuid);
        setUserActive(id);
    }

    //	//TODO make only return accepted
    //	//TODO replace get user UUID with only active user accounts
    //	@Override
    //	public List<GetOrganizationsForUserDto> getOrganizationsForUser(String userEmail) {
    //		UUID userUuid = getUserUuidForEmail(userEmail);
    //		List<Organization> organizationList = organizationRepository.findByOrganizationUserRoles_Id_UserUuid(
    //			userUuid
    //		);
    //		List<GetOrganizationsForUserDto> organizationToUserOrganizationsGetDtoList = organizationMapper.organizationToUserOrganizationsGetDtoList(
    //			organizationList
    //		);
    //		return organizationToUserOrganizationsGetDtoList;
    //	}

    @Override
    public List<GetOrganizationsForUserDto> getOrganizationsForUserUuid(UUID userUuid) {
        List<Organization> organizationList = organizationRepository.findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser(
                userUuid,
                true
        );
        List<GetOrganizationsForUserDto> organizationToUserOrganizationsGetDtoList = organizationMapper.organizationToUserOrganizationsGetDtoList(
                organizationList
        );
        return organizationToUserOrganizationsGetDtoList;
    }

    @Override
    public List<UUID> getOrganizationIdsForUser(UUID userUuid) {
        List<Organization> organizationList = organizationRepository.findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser(
                userUuid,
                true
        );
        List<UUID> organizationIdList = organizationList
                .stream()
                .map(Organization::getOrganizationId)
                .collect(Collectors.toList());
        return organizationIdList;
    }

    //	@Override
    //	public List<OrganizationUsersRoleGetDto> getDetailedOrganizationsForUser(
    //		String userEmail
    //	) {
    //		UUID userUuid = getUserUuidForEmail(userEmail); //TODO replace get user UUID with only active user accounts
    //		List<OrganizationUserRole> organizationUserRoleList = organizationUserRoleRepository.findByUserUuidAndIsActive(
    //			userUuid, true
    //		);
    //		List<OrganizationUsersRoleGetDto> organizationListToOrganizationUsersRoleGetDtoList = organizationMapper.organizationListToOrganizationUsersRoleGetDtoList(
    //			organizationUserRoleList
    //		);
    //		return organizationListToOrganizationUsersRoleGetDtoList;
    //	}

    @Override
    public List<OrganizationUsersRoleGetDto> getDetailedOrganizationsForUserWithUuid(
            UUID userUuid
    ) {
        List<OrganizationUserRole> organizationUserRoleList = organizationUserRoleRepository.findByUserUuidAndIsActive(
                userUuid,
                true
        );
        List<OrganizationUsersRoleGetDto> organizationListToOrganizationUsersRoleGetDtoList = organizationMapper.organizationListToOrganizationUsersRoleGetDtoList(
                organizationUserRoleList
        );
        return organizationListToOrganizationUsersRoleGetDtoList;
    }

    @Override
    public OrganizationUserRoleOnlyGetDto getRoleForUserForOrganization(
            String organizationName,
            UUID userUUID
    ) {
        OrganizationUserRole userRole = organizationUserRoleRepository.findByOrganization_OrganizationNameAndId_UserUuidAndIsActiveUser(
                organizationName,
                userUUID,
                true
        );
        OrganizationUserRoleOnlyGetDto organizationUserRoleOnlyGetDto = organizationMapper.entityToOrganizationUserRoleOnlyGetDto(
                userRole
        );
        return organizationUserRoleOnlyGetDto;
    }

    @Override
    public List<UserDetailsGetDto> getUsersFromOrganizationsForUser(UUID userUUID) {
        List<Organization> organizationList = organizationRepository
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser(
                        userUUID,
                        true
                );
        List<UUID> organizationIdList = organizationList
                .stream()
                .map(Organization::getOrganizationId)
                .collect(Collectors.toList());
        List<OrganizationUserRole> organizationUserRoleList = organizationUserRoleRepository
                .findById_OrganizationIdAndIsActiveUser(
                        organizationIdList
                );
        List<UUID> userUuids = organizationUserRoleList
                .stream()
                .map(OrganizationUserRole::getUserUUID)
                .collect(Collectors.toList());
        List<UserDetailsGetDto> userDetailsList = userService
                .findUsersDetailsForOrg(
                        userUuids
                );
        return userDetailsList;
    }

    @Override
    public List<DetailedUsersOrganization> getUsersForOrganization(
            String organizationName
    ) {
        List<OrganizationUserRole> organizationUserRoleList = organizationUserRoleRepository.findByOrganization_OrganizationNameIgnoreCase(
                organizationName
        );
        List<UUID> usersOrganizationUuids = organizationUserRoleList //TODO replace get user UUID with only active user accounts
                .stream()
                .map(OrganizationUserRole::getUserUUID)
                .collect(Collectors.toList());
        List<DetailedUsersOrganization> detailedUsersOrganizationList = createDetailedUserListForOrganization(
                usersOrganizationUuids,
                organizationUserRoleList
        );
        return detailedUsersOrganizationList;
    }

    private void isUserAllowedToInvite(Organization organization){
        Authentication authentication = authenticationFacade.getAuthentication();
        UUID userUuidRequester = UUID.fromString(authentication.getPrincipal().toString());
        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuidRequester
        );
        OrganizationUserRole requestOrganizationUser = organizationUserRoleRepository
                .findById(id).orElseThrow(
                        () -> new NotFoundException("You do not belong to the organization")
                );
        if (!isUserOrganizationOwner(requestOrganizationUser) && !isUserOrganizationAdmin(requestOrganizationUser))
            throw new BadRequestException("User is not allowed to invite users");

    }

    private OrganizationUserRole requesterOrganizationUser(Organization organization){
        Authentication authentication = authenticationFacade.getAuthentication();
        UUID userUuidRequester = UUID.fromString(authentication.getPrincipal().toString());
        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuidRequester
        );
        OrganizationUserRole existingOrganizationUserRole = organizationUserRoleRepository
                .findById(id).orElseThrow(
                        () -> new NotFoundException("You do not belong to the organization")
                );
        return existingOrganizationUserRole;
    }

    private void isUserAllowedToChangeRole(Organization organization, String organizationRole) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UUID userUuidRequester = UUID.fromString(authentication.getPrincipal().toString());
        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuidRequester
        );
        OrganizationUserRole existingOrganizationUserRole = organizationUserRoleRepository
                .findById(id).orElseThrow(
                        () -> new NotFoundException("You do not belong to the organization")
                );
        if (!isUserOrganizationOwner(existingOrganizationUserRole) && !isUserOrganizationAdmin(existingOrganizationUserRole))
            throw new BadRequestException("User is not allowed to change role");
        if (isUserOrganizationOwner(existingOrganizationUserRole) && organizationRole.equals("ORGANIZATION_OWNER")) {
            throw new BadRequestException("User cannot transfer ownership to another user");
        }
    }

    private boolean isUserAllowedToRemove(Organization organization) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UUID userUuidRequester = UUID.fromString(authentication.getPrincipal().toString());
        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuidRequester
        );
        OrganizationUserRole existingOrganizationUserRole = organizationUserRoleRepository
                .findById(id).orElseThrow(
                        () -> new BadRequestException("You do not belong to the organization")
                );
        if(existingOrganizationUserRole.getOrganizationRole().equals("ORGANIZATION_OWNER"))
            throw new BadRequestException("User cannot remove organization owner");
        if(existingOrganizationUserRole.getOrganizationRole().equals("ORGANIZATION_ADMIN"))
            throw new BadRequestException("User cannot remove organization admin");
        if(isUserOrganizationOwner(existingOrganizationUserRole) || isUserOrganizationAdmin(existingOrganizationUserRole)) {
            return true;
        }
        return false;

    }

    public boolean isUserOrganizationOwnerOrAdmin(String organizationName, UUID userUuid) {
        Organization organization = organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(organizationName, true).orElseThrow((() -> new NotFoundException("Organization not found")));
        OrganizationUserRoleId id = new OrganizationUserRoleId(
                organization.getOrganizationId(),
                userUuid
        );
        OrganizationUserRole organizationUserRole = organizationUserRoleRepository
                .findById(id).orElseThrow(
                        () -> new NotFoundException("User does not belong to the organization")
                );
        return isUserOrganizationOwner(organizationUserRole) || isUserOrganizationAdmin(organizationUserRole);
    }

    private boolean isUserOrganizationOwner(OrganizationUserRole organizationUserRole) {
        if (!organizationUserRole.getOrganizationRole().equals("ORGANIZATION_OWNER")) {
            return false;
        }
        return true;
    }

    private boolean isUserOrganizationAdmin(OrganizationUserRole organizationUserRole) {
        if (!organizationUserRole.getOrganizationRole().equals("ORGANIZATION_ADMIN")) {
            return false;
        }
        return true;
    }

    private Organization getOrganization(String organizationName) {
        Organization organization = organizationService.getOrganizationByName2(
                organizationName
        );
        return organization;
    }

    private List<DetailedUsersOrganization> createDetailedUserListForOrganization(
            List<UUID> usersOrganizationUuids,
            List<OrganizationUserRole> organizationUserRoleList
    ) {
        List<User> userList = userService.findUsersForOrg(usersOrganizationUuids);
        List<DetailedUsersOrganization> detailedUsersOrganizationList = organizationMapper.entityToDetailedUsersOrganizationList(
                userList
        );

        detailedUsersOrganizationList.forEach(
                one -> {
                    organizationUserRoleList
                            .stream()
                            .filter(
                                    organizationUserRole ->
                                            organizationUserRole.getUserUUID().equals(one.getUserUUID())
                            )
                            .limit(1)
                            .forEach(
                                    organizationUserRole -> {
                                        one.setOrganizationRole(
                                                (organizationUserRole.getOrganizationRole())
                                        );
                                    }
                            );
                }
        );
        detailedUsersOrganizationList.forEach(
                one -> {
                    organizationUserRoleList
                            .stream()
                            .filter(
                                    organizationUserRole ->
                                            organizationUserRole.getUserUUID().equals(one.getUserUUID())
                            )
                            .limit(1)
                            .forEach(
                                    organizationUserRole -> {
                                        one.setJoinedAt((organizationUserRole.getJoinedAt()));
                                    }
                            );
                }
        );
        detailedUsersOrganizationList.forEach(
                one -> {
                    organizationUserRoleList
                            .stream()
                            .filter(
                                    organizationUserRole ->
                                            organizationUserRole.getUserUUID().equals(one.getUserUUID())
                            )
                            .limit(1)
                            .forEach(
                                    organizationUserRole -> {
                                        one.setActiveUser((organizationUserRole.getIsActiveUser()));
                                    }
                            );
                }
        );
        return detailedUsersOrganizationList;
    }

    private UUID getUserUuidForEmail(String userEmail) {
        UUID userUuid = userService.getUserUuidFromAnyExistingUser(userEmail);
        return userUuid;
    }

    private void inviteExistingUserToOrganization(
            Organization organization,
            String organizationRole,
            OrganizationUserRoleId id,
            String userEmail,
            UUID userUuid
    ) {
        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        appRoleService.validatePurposeOrganization(organizationRole);
        organizationUserRole.setId(id);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole(organizationRole);
        organizationUserRole.setJoinedAt(OffsetDateTime.now());
        organizationUserRole.setUserUUID(userUuid);
        organizationUserRole.setActiveUser(true);
        organizationUserRoleRepository.save(organizationUserRole);
        organizationInviteService.newOrganizationInvite(
                userEmail,
                organization.getOrganizationName(),
                organization.getOrganizationId(),
                userUuid
        );
    }

    public void setUserActive(OrganizationUserRoleId id) {
        OrganizationUserRole setActive = organizationUserRoleRepository
                .findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("User cannot accept invite"));
        setActive.setActiveUser(true);
        organizationUserRoleRepository.save(setActive);
    }

    private OrganizationUserRoleId addUserToOrganizationWithRole(
            Organization organization,
            String organizationRole,
            OrganizationUserRoleId id,
            UUID userUuid,
            Boolean isActiveUser
    ) {
        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        appRoleService.validatePurposeOrganization(organizationRole);
        organizationUserRole.setId(id);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole(organizationRole);
        organizationUserRole.setJoinedAt(OffsetDateTime.now());
        organizationUserRole.setUserUUID(userUuid);
        organizationUserRole.setActiveUser(isActiveUser);
        organizationUserRoleRepository.save(organizationUserRole);
        return id;
    }
}
