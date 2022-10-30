package com.ravel.backend.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class JoinPost {

    @JsonProperty(value = "ActorNr")
    private Integer actorNr;
    @JsonProperty(value = "AppVersion")
    private String appVersion;
    @JsonProperty(value = "AppId")
    private String appId;
    @JsonProperty(value = "GameId")
    private String gameId;
    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "UserId")
    private String userId;
    @JsonProperty(value = "Nickname")
    private String nickname;
}
