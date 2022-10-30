package com.ravel.backend.modules.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
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
@Table(name = "app_module_user")
public class AppModuleUser {

	@JsonIgnore
	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@JsonIgnore
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@JsonIgnore
	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "username", nullable = true)
	private String username;

	@Column(name = "user_uuid", nullable = true)
	private UUID userUUID;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "app_module_user_app_modules",
		joinColumns = @JoinColumn(name = "app_module_user_id"),
		inverseJoinColumns = @JoinColumn(name = "app_modules_module_id")
	)
	public Set<AppModule> enrolledModules = new HashSet<>();

	public Set<AppModule> getEnrolledModules() {
		return enrolledModules;
	}

	public void setEnrolledModules(Set<AppModule> appModules) {
		this.enrolledModules = appModules;
	}
}
