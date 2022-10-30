package com.ravel.backend.spacePro.service;

import com.ravel.backend.spacePro.dto.*;
import com.ravel.backend.spacePro.model.SpacePro;
import java.util.List;
import java.util.UUID;

public interface SpaceProService {
	SpacePro createNewOrgProSpace(SpaceProPostDto spaceProPostDto);

	void assignRoleToProSpaceUser(
		UUID spaceUuid,
		AssignRoleToProSpaceUser assignRoleToProSpaceUser
	);

	void updateSpaceProEnvironment(UUID spaceUuid, String environmentName);

	void deleteSpacePro(UUID spaceUuid);

	List<SpaceProGetEnvDto> getProSpacesOrganization(UUID organizationUuid);

	JoinSpaceGetDtoV2 getPhotonRoomId(UUID userUuid, UUID spaceUuid);

	//TODO: implement validation of userUuid if allowed to request PhotonRoomId for Space
	//TODO: create nicer if-statement for optional return of getSpaceProUserDetails
	JoinSpaceGetDtoV2 getSessionDetails(UUID userUuid, String spaceProCode);

	SpaceProGetEnvDto getSpaceProByUuid(UUID spaceProUuid);

	SpaceProGetEnvDto getSpaceProByCode(String code);

	List<SpaceProGetEnvDto> getAllSpacesPro();

    void createSpaceProPortal(PortalPostDto portalPostDto);

    void deleteSpaceProPortal(UUID portalUuid);
}
