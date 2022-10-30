package com.ravel.backend.spacePro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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
public class Portal {


    @JsonIgnore
    @Id
    @SequenceGenerator(
            name = "portal_pro_sequence",
            sequenceName = "portal_pro_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "portal_pro_sequence"
    )
    @Column(nullable = false, updatable = false)
    private Long id;

    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @Column(unique = true, length = 36)
    private UUID portalUuid;

    @JsonIgnore
    @Column(name = "created_at")
    private OffsetDateTime created_at;

    @JsonIgnore
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "is_active")
    private boolean isActive;

    private UUID destinationSpace;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "space_pro_id")
    private SpacePro spacePro;


    @Type(type = "jsonb-node")
    @Column(name = "metadata", columnDefinition = "jsonb")
    private JsonNode metadata;

    @JsonIgnore
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "destination_space_pro_id")
    private SpacePro destinationSpacePro;

}
