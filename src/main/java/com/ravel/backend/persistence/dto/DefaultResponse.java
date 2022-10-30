package com.ravel.backend.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Schema(name = "DefaultResponse")
public class DefaultResponse {

    @JsonProperty(value = "ResultCode")
    private int resultCode;
}
