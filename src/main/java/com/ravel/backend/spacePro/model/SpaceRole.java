package com.ravel.backend.spacePro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SpaceRole {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "space_pro_roles_seq")
	@SequenceGenerator(name = "space_pro_roles_seq", sequenceName = "space_pro_roles_seq")
	@Column(nullable = false, updatable = false)
	private long spaceRoleId;

	private String appRoleName;

	@JsonIgnore
	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@JsonIgnore
	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@JsonIgnore
	@ManyToMany(
		mappedBy = "spaceRoles",
		cascade = { CascadeType.PERSIST, CascadeType.REMOVE }
	)
	private Set<SpaceProUserDetails> spaceProUserDetails = new LinkedHashSet<>();

	@JsonIgnore
	@ManyToMany(
		mappedBy = "defaultSpaceRoles",
		cascade = { CascadeType.PERSIST, CascadeType.REMOVE }
	)
	private Set<SpacePro> spacePros = new LinkedHashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SpaceRole spaceRole = (SpaceRole) o;
		return (
			spaceRoleId == spaceRole.spaceRoleId &&
			Objects.equals(appRoleName, spaceRole.appRoleName) &&
			Objects.equals(created_at, spaceRole.created_at) &&
			Objects.equals(updatedAt, spaceRole.updatedAt) &&
			Objects.equals(spaceProUserDetails, spaceRole.spaceProUserDetails)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			spaceRoleId,
			appRoleName,
			created_at,
			updatedAt,
			spaceProUserDetails
		);
	}
}
