package com.ravel.backend.spaceLite.model;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "space_lite")
public class SpaceLite {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String name;

	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "is_active")
	private boolean isActive;

	private String src;

	@Column(name = "room_code")
	private String roomCode;

	@Column(name = "invite_link")
	private String inviteLink;

	@Column(name = "embedded_code")
	private String embeddedCode;

	@ManyToMany
	@JoinTable(
		name = "space_lite_space_lite_organizations",
		joinColumns = @JoinColumn(name = "space_lite_id"),
		inverseJoinColumns = @JoinColumn(name = "space_lite_organization_id")
	)
	private Set<SpaceLiteOrganization> spaceLiteOrganizations = new LinkedHashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		SpaceLite spaceLite = (SpaceLite) o;
		return id != null && Objects.equals(id, spaceLite.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
