package com.ravel.backend.spacePro.dto;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class PortalGetDto implements Serializable {
    private final UUID portalUuid;
    private final UUID destinationSpace;
    private final JsonNode metadata;
    private final SpaceProGetEnvDto destinationSpacePro;
}
