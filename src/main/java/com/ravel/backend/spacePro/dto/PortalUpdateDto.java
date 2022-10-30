package com.ravel.backend.spacePro.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Schema(name = "PortalUpdateDto")
public class PortalUpdateDto {


    private JsonNode metadata;
}
