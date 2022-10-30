package com.ravel.backend.appAuth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_permission")
public class AppPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_permission_id", nullable = false)
	private long appPermissionId;

	@NotNull(message = "Please provide a permission name")
	@Column(name = "app_permission_name", nullable = false, unique = true, length = 50)
	@NotBlank
	@Schema(name = "Unique name for the permission", example = "spawn-testplayer")
	private String appPermissionName;

	@Column(name = "app_permission_description")
	@Schema(
		name = "Short description for the permission",
		example = "This permissions allows to spawn testplayers in the game"
	)
	private String description;

	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "app_permission_app_roles",
		joinColumns = @JoinColumn(name = "app_permission_app_permission_id"),
		inverseJoinColumns = @JoinColumn(name = "app_roles_app_role_id")
	)
	private Set<AppRole> appRoles;

	public void setAppRole(AppRole appRole) {
		this.appRoles.add(appRole);
	}
}
