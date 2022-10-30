package com.ravel.backend.spacePro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Schema(name = "PhotonRoomGetDto")
public class JoinSpaceGetDto {

	private String photonRoomId;
	private List<String> defaultSpaceRoles;
	private List<String> spaceRolesUser;
}
