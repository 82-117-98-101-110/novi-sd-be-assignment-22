package com.ravel.backend.organization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class Organization {

	@JsonIgnore
	@Id
	@GeneratedValue(generator = "UUIDGenerator")
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
	@Column(name = "organization_id")
	private UUID organizationId;

	@Column(name = "organization_name", nullable = false, unique = true, length = 150)
	private String organizationName;

	@JsonIgnore
	@Column(
		name = "created_at",
		columnDefinition = "TIMESTAMP WITH TIME ZONE",
		nullable = false
	)
	private OffsetDateTime createdAt;

	@JsonIgnore
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime updatedAt;

	@JsonIgnore
	@Column(name = "is_active")
	private boolean isActive;

	@JsonIgnore
	@OneToMany(
		mappedBy = "organization",
		cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
		orphanRemoval = true
	)
	private Set<OrganizationUserRole> organizationUserRoles;
}
