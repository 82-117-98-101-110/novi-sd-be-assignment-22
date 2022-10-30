package com.ravel.backend.spacePro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ravel.backend.spacePro.enumeration.SpaceType;
import java.time.OffsetDateTime;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "space_pro")
public class SpacePro {

	@Id
	@SequenceGenerator(name = "space_pro_sequence", sequenceName = "space_pro_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "space_pro_sequence")
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "is_active")
	private boolean isActive;

	@GeneratedValue(generator = "UUIDGenerator")
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
	@Column(unique = true, length = 36)
	private UUID spaceUuid;

	@NotNull
	@Column(unique = false, length = 50)
	private String spaceName;

	@NotNull
	@Column(nullable = false, unique = true, updatable = false, length = 164)
	private String sessionSpaceId;

	@Column(name = "description", length = 2000)
	private String description;

	@Column(name = "code_protected")
	private boolean codeProtected;

	@Column(name = "space_access_code", length = 50)
	private String spaceAccessCode;

	@ManyToMany(mappedBy = "spacePros")
	private Set<SpaceProPrivateUser> spaceProPrivateUsers = new LinkedHashSet<>();

	@ManyToMany(mappedBy = "spacePros")
	private Set<SpaceProOrganization> spaceProOrganizations = new LinkedHashSet<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "space_type")
	private SpaceType spaceType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "environment_pro_id", nullable = false)
	private EnvironmentPro environmentPro;

	//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "spacePro", orphanRemoval = true)
	//	private Set<SpaceProUser> spaceProUser = new LinkedHashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "space_pro_space_roles",
		joinColumns = @JoinColumn(name = "space_pro_", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(
			name = "space_roles_space_role_id",
			referencedColumnName = "spaceRoleId"
		)
	)
	private Set<SpaceRole> defaultSpaceRoles = new LinkedHashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "spacePro", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Portal> portals = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		SpacePro spacePro = (SpacePro) o;
		return id != null && Objects.equals(id, spacePro.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
