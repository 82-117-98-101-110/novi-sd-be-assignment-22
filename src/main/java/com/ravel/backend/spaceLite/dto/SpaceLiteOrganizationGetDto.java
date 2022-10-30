package com.ravel.backend.spaceLite.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class SpaceLiteOrganizationGetDto implements Serializable {

	private final UUID organizationId;
	private final Set<SpaceLiteGetDto> spaceLites;
}
