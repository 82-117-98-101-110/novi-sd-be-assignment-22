package com.ravel.backend.appAuth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "app_role")
public class AppRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_role_id", nullable = false)
	private long appRoleId;

	@NotNull(message = "Please provide a role name")
	@Column(name = "app_role_name", nullable = false, unique = true, length = 50)
	@NotBlank
	@Schema(name = "Unique name for role", example = "Presentor")
	private String appRoleName;

	@Column(name = "app_role_description")
	@Schema(
		name = "Short description for role",
		example = "This role is for more privileged users"
	)
	private String description;

	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	private String purpose;

	@ManyToMany(mappedBy = "appRoles", cascade = CascadeType.ALL)
	private Set<AppPermission> appPermissions;
}
