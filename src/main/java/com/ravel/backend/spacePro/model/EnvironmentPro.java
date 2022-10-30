package com.ravel.backend.spacePro.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.URL;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EnvironmentPro {

	@Id
	@SequenceGenerator(
		name = "environment_pro_sequence",
		sequenceName = "environment_pro_sequence"
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "environment_pro_sequence"
	)
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
	private UUID environmentUuid;

	@NotNull
	@Column(unique = true, length = 50)
	private String name;

	@Column(unique = true) //TODO make unique
	private String unitySceneName;

	@URL
	@Column(name = "asset_bundle_url", length = 500)
	private String assetBundleUrl;

	@Column(name = "description", length = 2000)
	private String description;

	@URL
	@Column(name = "image_url", length = 500)
	private String imageUrl;

	@Column(name = "is_public_environment")
	private boolean isPublicEnvironment;

	@OneToMany(mappedBy = "environmentPro")
	private List<SpacePro> spacePro = new ArrayList<>();
}
