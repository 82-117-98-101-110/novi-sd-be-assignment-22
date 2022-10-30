package com.ravel.backend.spacePro.model;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
public class SpaceProOrganization {

	@Id
	@SequenceGenerator(
		name = "space_pro_org_sequence",
		sequenceName = "space_pro_org_sequence"
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "space_pro_org_sequence"
	)
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(name = "organization_id")
	private UUID organizationId;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
	@JoinTable(
		name = "space_pro_organization_space_pros",
		joinColumns = @JoinColumn(
			name = "space_pro_organization_",
			referencedColumnName = "id"
		),
		inverseJoinColumns = @JoinColumn(
			name = "space_pros_id",
			referencedColumnName = "id"
		)
	)
	private Set<SpacePro> spacePros = new LinkedHashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		SpaceProOrganization that = (SpaceProOrganization) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
