package com.ravel.backend.spacePro.service;

import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.persistence.dto.PhotonRoomUserDto;
import com.ravel.backend.persistence.mapper.PersistenceMapper;
import com.ravel.backend.persistence.model.PhotonRoom;
import com.ravel.backend.persistence.model.PhotonRoomUser;
import com.ravel.backend.persistence.service.PhotonRoomService;
import com.ravel.backend.persistence.service.PhotonRoomUserService;
import com.ravel.backend.shared.exception.BadRequestException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spacePro.agoraRtc.AgoraTokenGenerator;
import com.ravel.backend.spacePro.dto.*;
import com.ravel.backend.spacePro.mapper.SpaceProMapper;
import com.ravel.backend.spacePro.model.*;
import com.ravel.backend.spacePro.repository.SpaceProRepository;
import com.ravel.backend.spacePro.repository.SpaceProUserDetailsRepository;
import com.ravel.backend.spacePro.repository.SpaceProUserRepository;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.service.UserService;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.hashids.Hashids;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SpaceProServiceImpl implements SpaceProService {

    private final SpaceProRepository spaceProRepository;
    private SpaceProMapper spaceProMapper;
    private OrganizationService organizationService;
    private SpaceProOrganizationService spaceProOrganizationService;
    private EnvironmentProService environmentProService;
    private SpaceRoleService spaceRoleService;
    private SpaceProUserService spaceProUserService;
    private SpaceProUserDetailsService spaceProUserDetailsService;
    private SpaceProUserRepository spaceProUserRepository;
    private SpaceProUserDetailsRepository spaceProUserDetailsRepository;
    private PortalService portalService;
    private PhotonRoomService photonRoomService;
    private PhotonRoomUserService photonRoomUserService;
    private PersistenceMapper persistenceMapper;


    private AgoraTokenGenerator agoraTokenGenerator;

    private UserService userService;

    public SpaceProServiceImpl(
            SpaceProRepository spaceProRepository,
            SpaceProMapper spaceProMapper,
            OrganizationService organizationService,
            SpaceProOrganizationService spaceProOrganizationService,
            EnvironmentProService environmentProService,
            SpaceRoleService spaceRoleService,
            @Lazy SpaceProUserService spaceProUserService,
            @Lazy SpaceProUserDetailsService spaceProUserDetailsService,
            SpaceProUserRepository spaceProUserRepository,
            SpaceProUserDetailsRepository spaceProUserDetailsRepository,
            PortalService portalService, PhotonRoomService photonRoomService, PhotonRoomUserService photonRoomUserService, PersistenceMapper persistenceMapper, AgoraTokenGenerator agoraTokenGenerator, UserService userService) { // circular dependencies https://www.baeldung.com/circular-dependencies-in-spring
        this.spaceProRepository = spaceProRepository;
        this.spaceProMapper = spaceProMapper;
        this.organizationService = organizationService;
        this.spaceProOrganizationService = spaceProOrganizationService;
        this.environmentProService = environmentProService;
        this.spaceRoleService = spaceRoleService;
        this.spaceProUserService = spaceProUserService;
        this.spaceProUserDetailsService = spaceProUserDetailsService;
        this.spaceProUserRepository = spaceProUserRepository;
        this.spaceProUserDetailsRepository = spaceProUserDetailsRepository;
        this.portalService = portalService;
        this.photonRoomService = photonRoomService;
        this.photonRoomUserService = photonRoomUserService;
        this.persistenceMapper = persistenceMapper;
        this.agoraTokenGenerator = agoraTokenGenerator;
        this.userService = userService;
    }

    @Override
    public SpacePro createNewOrgProSpace(SpaceProPostDto spaceProPostDto) {
        String appRoleNameSpaceCreator = "SPACE_OWNER";

        //validate org name & get org UUID
        UUID orgUuid = organizationService.getOrganizationId(
                spaceProPostDto.getOrganizationName()
        );

        //validate environmentName
        EnvironmentPro environmentPro = environmentProService.getEnvironmentByName(
                spaceProPostDto.getEnvironmentName()
        );

        SpacePro newSpacePro = createSpacePro(spaceProPostDto);
        newSpacePro.setEnvironmentPro(environmentPro);

        Set<SpaceRole> defaultSpaceRoles = new HashSet<>();

        SpaceRole spaceRole;
        if (defaultSpaceRoles == null) {
            spaceRole = spaceRoleService.findSpaceRole("USER");
        } else {
            spaceRole =
                    spaceRoleService.findSpaceRole(spaceProPostDto.getDefaultSpaceRole());
        }
        defaultSpaceRoles.add(spaceRole);
        newSpacePro.setDefaultSpaceRoles(defaultSpaceRoles);
        spaceProRepository.save(newSpacePro);


        SpacePro spacePro=  spaceProRepository.findBySpaceUuid(newSpacePro.getSpaceUuid())  .orElseThrow(() -> new NotFoundException("Something went wrong"));

        newSpacePro.setSessionSpaceId( createPhotonRoomId(spacePro.getId()));


        //add env to space
        spaceProOrganizationService.addProSpaceToOrg(
                spaceProPostDto.getOrganizationName(),
                newSpacePro.getSpaceUuid()
        );
        assignRoleToSpaceCreator(
                newSpacePro.getSpaceUuid(),
                spaceProPostDto.getUserUuid(),
                appRoleNameSpaceCreator
        );
        return newSpacePro;
    }

    private void assignRoleToSpaceCreator(
            UUID spaceUuid,
            UUID userUuid,
            String appRoleName
    ) {
        SpacePro spacePro = spaceProRepository
                .findBySpaceUuidAndIsActive(spaceUuid, true)
                .orElseThrow(() -> new NotFoundException("Ravel Space Pro not found"));

        //get SpaceProUser, if not exists create new one and return
        SpaceProUser spaceProUser = spaceProUserService.getSpaceProUserIfnNotExistCreateNew(
                spacePro,
                userUuid
        );

        //store new appRole to spaceProUser
        SpaceProUserDetails spaceProUserDetail = spaceProUserDetailsService.addRoleToSpaceProUserDetails(
                spaceProUser.getSpaceProUserDetails().getSpaceProUserDetailsId(),
                appRoleName
        );
    }

    @Override
    public void assignRoleToProSpaceUser(
            UUID spaceUuid,
            AssignRoleToProSpaceUser assignRoleToProSpaceUser
    ) {
        SpacePro spacePro = spaceProRepository
                .findBySpaceUuidAndIsActive(spaceUuid, true)
                .orElseThrow(() -> new NotFoundException("Ravel Space Pro not found"));

        //get SpaceProUser, if not exists create new one and return
        SpaceProUser spaceProUser = spaceProUserService.getSpaceProUserIfnNotExistCreateNew(
                spacePro,
                assignRoleToProSpaceUser.getUserUuid()
        );

        //store new appRole to spaceProUser
        SpaceProUserDetails spaceProUserDetail = spaceProUserDetailsService.addRoleToSpaceProUserDetails(
                spaceProUser.getSpaceProUserDetails().getSpaceProUserDetailsId(),
                assignRoleToProSpaceUser.getAppRoleName()
        );
    }

    @Override
    public void updateSpaceProEnvironment(UUID spaceUuid, String environmentName) {
        EnvironmentPro environmentPro = environmentProService.getEnvironmentByName(
                environmentName
        );
        SpacePro spacePro = spaceProRepository
                .findBySpaceUuidAndIsActive(spaceUuid, true)
                .orElseThrow(() -> new NotFoundException("Ravel Space Pro not found"));
        spacePro.setEnvironmentPro(environmentPro);
        spaceProRepository.save(spacePro);
    }

    @Override
    public void deleteSpacePro(UUID spaceUuid) {
        SpacePro spacePro = spaceProRepository
                .findBySpaceUuidAndIsActive(spaceUuid, true)
                .orElseThrow(() -> new NotFoundException("Ravel Space Pro not found"));
        List<SpaceProUser> spaceProUserList = spaceProUserService.getSpaceProUserList(
                spacePro.getId()
        );
        List<SpaceProUserDetails> userDetailIds = spaceProUserList
                .stream()
                .map(SpaceProUser::getSpaceProUserDetails)
                .collect(Collectors.toList());
        List<Long> longList = userDetailIds
                .stream()
                .map(SpaceProUserDetails::getSpaceProUserDetailsId)
                .collect(Collectors.toList());
        spaceProUserService.deleteSpaceProUserWhereSpaceUuid(spacePro.getId());
        spaceProRepository.deleteBySpaceUuid(spacePro.getSpaceUuid());
        spaceProUserDetailsService.deleteDetailsForDeletingSpacePro(longList);
    }

    //TODO gives error with : spaceProList = Cannot find local variable 'spaceProList'
    //TODO optimize hibernate query?
    @Override
    public List<SpaceProGetEnvDto> getProSpacesOrganization(UUID organizationUuid) {
        List<SpacePro> spaceProList = spaceProRepository.findBySpaceProOrganizations_OrganizationIdAndIsActive(
                organizationUuid,
                true
        );
        List<String> photonRoomIds = spaceProList
                .stream()
                .map(SpacePro::getSessionSpaceId)
                .collect(Collectors.toList());
        List<SpaceProGetEnvDto> spaceProGetEnvDtoList = spaceProMapper.spaceProListToSpaceProGetEnvDtoList(spaceProList); //needs boolean if online?
        List<PhotonRoom> photonRoomList = photonRoomService.getOnlineRooms(photonRoomIds); //contains boolean isOnline?
        if (!photonRoomList.isEmpty()) {

            spaceProGetEnvDtoList.forEach(
                    one -> {
                        photonRoomList
                                .stream()
                                .filter(
                                        organizationUserRole ->
                                                organizationUserRole.getPhotonRoomId().equals(one.getPhotonRoomId())
                                )
                                .limit(1)
                                .forEach(
                                        organizationUserRole -> {
                                            one.setRoomIsOnline(
                                                    (organizationUserRole.isRoomIsOnline())
                                            );
                                        }
                                );
                    }
            );

            List<String> currentOnlineRooms = photonRoomList.stream().map(PhotonRoom::getPhotonRoomId).collect(java.util.stream.Collectors.toList());
            List<PhotonRoomUser> photonRoomUsers = photonRoomUserService.getOnlinePhotonRoomUsers(currentOnlineRooms);
            List<PhotonRoomUserDto> photonRoomUserDtoList = persistenceMapper.photonRoomUserToPhotonRoomUserDtoList(photonRoomUsers);

            List<User> userList = userService.findUsersForOrg(
                    photonRoomUsers
                            .stream()
                            .map(PhotonRoomUser::getUserUuid)
                            .collect(Collectors.toList()));

            photonRoomUserDtoList.forEach(
                    output -> {
                        userList
                                .stream()
                                .filter(
                                        input ->
                                                input.getUserUUID().equals(output.getUserUuid())
                                )
//							.limit(1)
                                .forEach(
                                        input -> {
                                            output.setFirstName(
                                                    (input.getFirstName())
                                            );
                                        }
                                );
                    }
            );
            spaceProGetEnvDtoList.forEach(
                    output -> {
                        photonRoomUserDtoList
                                .stream()
                                .filter(
                                        input ->
                                                input.getIdPhotonRoomId().equals(output.getPhotonRoomId())
                                );
                        output.setPhotonRoomUserDtoList(photonRoomUserDtoList);
                    }
            );
        }
        return spaceProGetEnvDtoList;
    }

    //TODO: implement validation of userUuid if allowed to request PhotonRoomId for Space
    //TODO: create nicer if-statement for optional return of getSpaceProUserDetails
    @Override
    public JoinSpaceGetDtoV2 getPhotonRoomId(UUID userUuid, UUID spaceUuid) {
        // get SpacePro
        SpacePro spacePro = spaceProRepository
                .findBySpaceUuidAndIsActive(spaceUuid, true)
                .orElseThrow(() -> new NotFoundException("Ravel Space Pro not found"));

        // set basic values for response
        JoinSpaceGetDtoV2 response = new JoinSpaceGetDtoV2();
        response.setPhotonRoomId(spacePro.getSessionSpaceId());
        Set<SpaceRole> defaultSpaceRoles = spacePro.getDefaultSpaceRoles();
        List<String> spaceDefaultRoleList = defaultSpaceRoles
                .stream()
                .map(SpaceRole::getAppRoleName)
                .collect(Collectors.toList());
        response.setDefaultSpaceRoles(spaceDefaultRoleList);

        List<Portal> portals = spacePro.getPortals();
        List<PortalGetDto> portalGetDtoList = spaceProMapper.portalToPortalGetDtoList(portals);
        response.setPortals(portalGetDtoList);


        // get the photonRoom for the spacePro
        PhotonRoom photonRoom = photonRoomService.findByPhotonRoomId(spacePro.getSessionSpaceId());

        if (photonRoom != null) {
            // set the current hostUserUuid in the response
            response.setHostUserUuid(photonRoom.getHostSessionUserId());

            //if photonRoodanm is found and hostUserUuid is set, search for photonRoomUser //TODO should only return user once a 5 min?
            PhotonRoomUser photonRoomUser = photonRoomUserService.getPhotonRoomUser(userUuid, spacePro.getSessionSpaceId());

            // if photonRoomUser is found, set the photonRoomUserId in the response
            if (photonRoomUser != null) {

                response.setActiveActor(photonRoomUser.isInActive());
                response.setSessionUserId(photonRoomUser.getId().getSessionUserId());

                //if photonRoomUser is not found, user is not an activeActor and we create a new unique sessionUserId
            } else {
                response.setActiveActor(false);
                response.setSessionUserId(createSessionUserId(userUuid));
            }
        }
        if (photonRoom == null) {
            //if photonRoom is not found,
            // 1: setActiveUser to false
            response.setActiveActor(false);

            // 2: create new agoraToken and set as sessionUserId
            response.setSessionUserId(createSessionUserId(userUuid));

            // 3: set hostUserUuid to null
            response.setHostUserUuid(null);
        }
        response.setAgoraToken(createAgoraVoiceToken(spacePro.getSessionSpaceId(), response.getSessionUserId()));

        //need portalsGetDto with destination space Pro which also has a name and image URL. Write mapper and dtos with jpa buddy
        if (spaceProUserService.doesUserHasDetailsForSpacePro(spacePro, userUuid)) {
            SpaceProUser spaceProUser = spaceProUserService.getSpaceProUserDetails(
                    spacePro,
                    userUuid
            );
            Set<SpaceRole> spaceRoles = spaceProUser
                    .getSpaceProUserDetails()
                    .getSpaceRoles();
            List<String> spaceRoleGetDtoList = spaceRoles
                    .stream()
                    .map(SpaceRole::getAppRoleName)
                    .collect(Collectors.toList());
            response.setSpaceRolesUser(spaceRoleGetDtoList);
        } else {
            List<String> emptySpaceDefaultRoleList = new ArrayList<>();
            response.setSpaceRolesUser(emptySpaceDefaultRoleList);
        }
        return response;
    }

    //TODO: implement validation of userUuid if allowed to request PhotonRoomId for Space
    //TODO: create nicer if-statement for optional return of getSpaceProUserDetails
    @Override
    public JoinSpaceGetDtoV2 getSessionDetails(UUID userUuid, String spaceProCode) {
        // get SpacePro

        Long spaceProId =  decodePhotonRoomId(spaceProCode);

        SpacePro spacePro = spaceProRepository.findByIdAndIsActive(spaceProId, true).orElseThrow(() -> new NotFoundException("Ravel Space Pro not found"));


        // set basic values for response
        JoinSpaceGetDtoV2 response = new JoinSpaceGetDtoV2();
        response.setPhotonRoomId(spacePro.getSessionSpaceId());
        Set<SpaceRole> defaultSpaceRoles = spacePro.getDefaultSpaceRoles();
        List<String> spaceDefaultRoleList = defaultSpaceRoles
                .stream()
                .map(SpaceRole::getAppRoleName)
                .collect(Collectors.toList());
        response.setDefaultSpaceRoles(spaceDefaultRoleList);

        List<Portal> portals = spacePro.getPortals();
        List<PortalGetDto> portalGetDtoList = spaceProMapper.portalToPortalGetDtoList(portals);
        response.setPortals(portalGetDtoList);


        // get the photonRoom for the spacePro
        PhotonRoom photonRoom = photonRoomService.findByPhotonRoomId(spacePro.getSessionSpaceId());

        if (photonRoom != null) {
            // set the current hostUserUuid in the response
            response.setHostUserUuid(photonRoom.getHostSessionUserId());

            //if photonRoodanm is found and hostUserUuid is set, search for photonRoomUser //TODO should only return user once a 5 min?
            PhotonRoomUser photonRoomUser = photonRoomUserService.getPhotonRoomUser(userUuid, spacePro.getSessionSpaceId());

            // if photonRoomUser is found, set the photonRoomUserId in the response
            if (photonRoomUser != null) {

                response.setActiveActor(photonRoomUser.isInActive());
                response.setSessionUserId(photonRoomUser.getId().getSessionUserId());

                //if photonRoomUser is not found, user is not an activeActor and we create a new unique sessionUserId
            } else {
                response.setActiveActor(false);
                response.setSessionUserId(createSessionUserId(userUuid));
            }
        }
        if (photonRoom == null) {
            //if photonRoom is not found,
            // 1: setActiveUser to false
            response.setActiveActor(false);

            // 2: create new agoraToken and set as sessionUserId
            response.setSessionUserId(createSessionUserId(userUuid));

            // 3: set hostUserUuid to null
            response.setHostUserUuid(null);
        }
        response.setAgoraToken(createAgoraVoiceToken(spacePro.getSessionSpaceId(), response.getSessionUserId()));

        //need portalsGetDto with destination space Pro which also has a name and image URL. Write mapper and dtos with jpa buddy
        if (spaceProUserService.doesUserHasDetailsForSpacePro(spacePro, userUuid)) {
            SpaceProUser spaceProUser = spaceProUserService.getSpaceProUserDetails(
                    spacePro,
                    userUuid
            );
            Set<SpaceRole> spaceRoles = spaceProUser
                    .getSpaceProUserDetails()
                    .getSpaceRoles();
            List<String> spaceRoleGetDtoList = spaceRoles
                    .stream()
                    .map(SpaceRole::getAppRoleName)
                    .collect(Collectors.toList());
            response.setSpaceRolesUser(spaceRoleGetDtoList);
        } else {
            List<String> emptySpaceDefaultRoleList = new ArrayList<>();
            response.setSpaceRolesUser(emptySpaceDefaultRoleList);
        }
        return response;
    }

    @Override
    public SpaceProGetEnvDto getSpaceProByUuid(UUID spaceProUuid) {
        SpacePro spacePro = spaceProRepository
                .findBySpaceUuidAndIsActive(spaceProUuid, true)
                .orElseThrow(() -> new NotFoundException("Space not found"));

        return spaceProMapper.spaceProToSpaceProGetEnvDto(spacePro);
    }

    @Override
    public SpaceProGetEnvDto getSpaceProByCode(String code) {
        Long spaceProId = decodePhotonRoomId(code);
        SpacePro spacePro = spaceProRepository
                .findByIdAndIsActive(spaceProId, true)
                .orElseThrow(() -> new NotFoundException("Space not found"));

        return spaceProMapper.spaceProToSpaceProGetEnvDto(spacePro);
    }

    @Override
    public List<SpaceProGetEnvDto> getAllSpacesPro() {
        List<SpacePro> spaceProList = spaceProRepository.findAll();
        List<SpaceProGetEnvDto> spaceProGetDtos = spaceProMapper.spaceProListToSpaceProGetEnvDtoList(
                spaceProList
        );
        return spaceProGetDtos;
    }

    @Override
    public void createSpaceProPortal(PortalPostDto portalPostDto) {
        spaceProRepository.existsBySpaceUuidAndIsActive(portalPostDto.getDestinationSpace(), true);
        SpacePro spacePro = spaceProRepository
                .findBySpaceUuidAndIsActive(portalPostDto.getSpaceProUuid(), true)
                .orElseThrow(() -> new NotFoundException("Space not found"));
        SpacePro spaceProDestination = spaceProRepository
                .findBySpaceUuidAndIsActive(portalPostDto.getDestinationSpace(), true)
                .orElseThrow(() -> new NotFoundException("Destination Space not found"));

        portalService.createPortal(portalPostDto, spacePro, spaceProDestination);
    }

    @Override
    public void deleteSpaceProPortal(UUID portalUuid) {
        portalService.deletePortal(portalUuid);
    }

    private SpacePro createSpacePro(
            SpaceProPostDto spaceProPostDto
    ) {
        SpacePro newSpacePro = SpacePro
                .builder()
                .created_at(OffsetDateTime.now())
                .spaceUuid(UUID.randomUUID())
                .isActive(true)
                .description(spaceProPostDto.getDescription())
                .spaceName(spaceProPostDto.getSpaceName())
                .codeProtected(spaceProPostDto.isCodeProtected())
                .spaceType(spaceProPostDto.getSpaceType())
                .sessionSpaceId(UUID.randomUUID().toString())
                .build();

        return newSpacePro;
    }

    private String createPhotonRoomId(Long spaceId) {
            Hashids hashids = new Hashids("Bok-Barge-Chafe-Ship9-Whimsical-Moistness", 11);
            String uniqueHash = hashids.encode(spaceId);
            String code =  uniqueHash;
            if (spaceProRepository.existsByPhotonRoomIdIgnoreCase(code)) {
            throw new BadRequestException("Something went wrong, please try again");
        }
        spaceProRepository.updatePhotonRoomIdById(code, spaceId);
        return code;
    }

    private Long decodePhotonRoomId(String code) {
        Hashids hashids = new Hashids("Bok-Barge-Chafe-Ship9-Whimsical-Moistness", 11);
        long[] spaceProId = hashids.decode(code);
                return spaceProId[0];
    }

    private String createAgoraVoiceToken(String photonRoomId, String sessionUserId) {
        String agoraToken = agoraTokenGenerator.generateAgoraToken(photonRoomId, sessionUserId);
        return agoraToken;
    }

    private String createSessionUserId(UUID userUuid) {
        Hashids hashids = new Hashids(UUID.randomUUID().toString());
        String uniqueHash = hashids.encode(1, 2, 3, 4, 5);
        String sessionUserId = userUuid.toString() + "-" + uniqueHash;
        return sessionUserId;

    }
}
