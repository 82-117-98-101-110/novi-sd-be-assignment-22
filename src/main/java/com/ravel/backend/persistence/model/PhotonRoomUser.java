package com.ravel.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "photon_room_user")
public class PhotonRoomUser {
    @EmbeddedId
    private PhotonRoomUserId id;


    private UUID userUuid;

    @Column(name = "actor_nr")
    private Integer actorNr;

    @JsonIgnore
    @Column(name = "created_at")
    private OffsetDateTime created_at;

    // DateTime the user joined the room
    private OffsetDateTime lastJoined;

    // If set to true, the user is currently in the room/online
    private boolean isOnline;

    // If set to true, the user needs to re-join the room, if set to false,
    // the user has abandoned the room for good and needs to join room again.
    private boolean isInActive;
    private boolean isHost;

}