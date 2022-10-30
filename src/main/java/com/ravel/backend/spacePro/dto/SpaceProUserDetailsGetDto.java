package com.ravel.backend.spacePro.dto;

import java.io.Serializable;
import java.util.Set;
import lombok.Data;

@Data
public class SpaceProUserDetailsGetDto implements Serializable {

	private final Set<SpaceRoleGetDto> spaceRoles;
}
