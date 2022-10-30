package com.ravel.backend.persistence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

@Data
@Schema(name = "OnlineUserList")
public class OnlineUserList {

    private String sessionUserId;
    private UUID userUUID;
    private String firstName;
}
