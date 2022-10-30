package com.ravel.backend.spacePro.model;

import java.time.OffsetDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SpaceProUser {

	@EmbeddedId
	@JsonIgnore
	private SpaceProUserId spaceProUserId;

	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	//	@ManyToOne(fetch = FetchType.LAZY)
	//	@MapsId("spaceProId")
	//	@JoinColumn(name = "space_pro_id")
	//	private SpacePro spacePro;

	@OneToOne(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, orphanRemoval = true) //TODO make optional false
	@JoinColumn(name = "space_pro_user_role_details_id") //make nullable false?
	private SpaceProUserDetails spaceProUserDetails;
}
