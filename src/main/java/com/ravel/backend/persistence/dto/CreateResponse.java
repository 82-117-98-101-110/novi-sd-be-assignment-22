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
@Schema(name = "CreateResponse")
public class CreateResponse {
    @JsonProperty(value = "State")
    private JsonNode state;
    @JsonProperty(value = "ResultCode")
    private int resultCode;
}
