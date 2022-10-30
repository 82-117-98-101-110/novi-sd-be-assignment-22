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
public class SpaceProPrivateUser {

	@Id
	@SequenceGenerator(
		name = "space_pro_private_user_sequence",
		sequenceName = "space_pro_private_user_sequence"
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "space_pro_private_user_sequence"
	)
	@Column(nullable = false, updatable = false)
	private Long id;

	private UUID userUUID;

	@Column(name = "organization_id")
	private UUID organizationId;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(
		name = "space_pro_user_space_pros",
		joinColumns = @JoinColumn(name = "space_pro_user_", referencedColumnName = "id"),
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
		SpaceProPrivateUser that = (SpaceProPrivateUser) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
