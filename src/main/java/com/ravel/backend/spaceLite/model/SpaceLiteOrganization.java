package com.ravel.backend.spaceLite.model;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "space_lite_organization")
public class SpaceLiteOrganization {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private UUID organizationId;

	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "is_active")
	private boolean isActive;

	@ManyToMany
	@JoinTable(
		name = "space_lite_space_lite_organizations",
		joinColumns = @JoinColumn(name = "space_lite_organization_id"),
		inverseJoinColumns = @JoinColumn(name = "space_lite_id")
	)
	private Set<SpaceLite> spaceLites = new LinkedHashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		SpaceLiteOrganization that = (SpaceLiteOrganization) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
