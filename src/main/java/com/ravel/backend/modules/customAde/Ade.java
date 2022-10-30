package com.ravel.backend.modules.customAde;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mod_ade_user")
public class Ade {

	@Id
	@Setter(AccessLevel.NONE)
	@SequenceGenerator(name = "id", sequenceName = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "username", nullable = false, unique = true, length = 50)
	private String username;

	@JsonIgnore
	@Column(name = "user_uuid", nullable = true)
	private UUID userUUID;

	@JsonIgnore
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@JsonIgnore
	@Column(name = "updated_at", nullable = true)
	private Date updatedAt;

	@JsonIgnore
	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "company", nullable = true)
	private String company;

	@Column(name = "country_code", nullable = true)
	private Long countryCode;

	@Column(name = "country_name", nullable = true)
	private String countryName;

	@Column(name = "country_alpha2", nullable = true)
	private String countryAlpha2;

	@Column(name = "country_alpha3", nullable = true)
	private String countryAlpha3;

	public boolean isActive() {
		return isActive;
	}
}
