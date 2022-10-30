package com.ravel.backend.modules.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_module")
public class AppModule {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "module_id", nullable = false)
	private long moduleId;

	@NotNull(message = "Please provide a appModule name")
	@Column(name = "module_name", nullable = false, unique = true, length = 50)
	@NotBlank
	private String moduleName;

	@Column(name = "module_description")
	private String description;

	@JsonIgnore
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "module_image_url")
	private String moduleImageUrl;

	@Column(name = "module_color")
	private String moduleColor;

	@JsonIgnore
	@ManyToMany(mappedBy = "enrolledModules")
	private Set<AppModuleUser> appModuleUsers = new HashSet<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "enrolledModules")
	private Set<AppModuleOrganization> appModuleOrganizations = new LinkedHashSet<>();

	public Set<AppModuleOrganization> getAppModuleOrganizations() {
		return appModuleOrganizations;
	}

	public void setAppModuleOrganizations(
		Set<AppModuleOrganization> appModuleOrganizations
	) {
		this.appModuleOrganizations = appModuleOrganizations;
	}

	public Set<AppModuleUser> getAppModuleUsers() {
		return appModuleUsers;
	}

	public void setAppModuleUsers(Set<AppModuleUser> appModuleUsers) {
		this.appModuleUsers = appModuleUsers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		AppModule appModule = (AppModule) o;
		return false;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
