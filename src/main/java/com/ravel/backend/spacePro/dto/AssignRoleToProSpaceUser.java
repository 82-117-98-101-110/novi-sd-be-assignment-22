package com.ravel.backend.spacePro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "AssignRoleToProSpaceUser")
public class AssignRoleToProSpaceUser {

	private UUID userUuid;
	private String appRoleName;
}
