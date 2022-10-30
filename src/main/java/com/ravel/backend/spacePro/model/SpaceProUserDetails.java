package com.ravel.backend.spacePro.model;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SpaceProUserDetails {

	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "space_pro_user_details_seq"
	)
	@SequenceGenerator(
		name = "space_pro_user_details_seq",
		sequenceName = "space_pro_user_details_seq"
	)
	@Column(nullable = false, updatable = false)
	private long spaceProUserDetailsId;

	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@OneToOne(mappedBy = "spaceProUserDetails")
	private SpaceProUser spaceProUser;

	@ManyToMany
	@JoinTable(
		name = "space_pro_user_role_details_space_roles",
		joinColumns = @JoinColumn(
			name = "space_pro_user_role_details_",
			referencedColumnName = "spaceProUserDetailsId"
		),
		inverseJoinColumns = @JoinColumn(
			name = "space_roles_space_role_id",
			referencedColumnName = "spaceRoleId"
		)
	)
	private Set<SpaceRole> spaceRoles = new LinkedHashSet<>();
}
