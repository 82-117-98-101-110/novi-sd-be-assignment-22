package com.ravel.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@TypeDef(
        name = "jsonb-node",
        typeClass = JsonNodeBinaryType.class
)
public class PhotonRoom {
    @JsonIgnore
    @Id
    @SequenceGenerator(
            name = "photon_room_sequence",
            sequenceName = "photon_room_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "photon_room_sequence"
    )
    @Column(nullable = false, updatable = false)
    private Long id;

    @JsonIgnore
    @Column(name = "created_at")
    private OffsetDateTime created_at;

    @JsonIgnore
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Type(type = "jsonb-node")
    @Column(name = "state", columnDefinition = "jsonb")
    private JsonNode state;

    private String stateString;

    @Column(nullable = false, unique = true, updatable = false, length = 164)
    private String photonRoomId;

    private String photonAppId;
    private String hostSessionUserId;

    private UUID hostUserUuid;

    private boolean roomIsOnline;




}
