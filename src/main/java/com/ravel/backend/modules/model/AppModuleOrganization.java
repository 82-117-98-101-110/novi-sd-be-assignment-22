package com.ravel.backend.modules.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppModuleOrganization {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private long id;

	@JsonIgnore
	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@JsonIgnore
	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "organization_id")
	private UUID organizationId;

	@ManyToMany
	@JoinTable(
		name = "app_module_organization_app_modules",
		joinColumns = @JoinColumn(name = "app_module_organization_id"),
		inverseJoinColumns = @JoinColumn(name = "app_modules_module_id")
	)
	public Set<AppModule> enrolledModules = new LinkedHashSet<>();

	public Set<AppModule> getEnrolledModules() {
		return enrolledModules;
	}

	public void setEnrolledModules(Set<AppModule> appModules) {
		this.enrolledModules = appModules;
	}
}
