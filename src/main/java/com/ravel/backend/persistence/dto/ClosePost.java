package com.ravel.backend.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "ClosePost")
public class ClosePost {
    @JsonProperty(value = "AppVersion")
    private String appVersion;
    @JsonProperty(value = "AppId")
    private String appId;
    @JsonProperty(value = "GameId")
    private String gameId;
    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "State")
    private JsonNode state;
}
