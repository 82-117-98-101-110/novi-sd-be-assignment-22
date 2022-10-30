package com.ravel.backend.spacePro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Schema(name = "JoinSpaceGetDtoV2")
public class JoinSpaceGetDtoV2 {

    private String photonRoomId;
    private String agoraToken;
    private String sessionUserId; //always use this sessionUserId to join the room, if host this is correct one
    private boolean isActiveActor; // if true, user should re-join the room instead of join
    private String hostUserUuid; //if match, then user should never abandon the room

    private List<PortalGetDto> portals;
    private List<String> defaultSpaceRoles;
    private List<String> spaceRolesUser;
}
