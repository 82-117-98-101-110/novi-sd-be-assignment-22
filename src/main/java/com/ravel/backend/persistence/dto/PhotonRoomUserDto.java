package com.ravel.backend.persistence.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PhotonRoomUserDto implements Serializable {
    private  String idSessionUserId;
    private  String idPhotonRoomId;
    private  UUID userUuid;
    private  boolean isOnline;
    private  String firstName;
}
