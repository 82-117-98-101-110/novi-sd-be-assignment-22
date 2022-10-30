package com.ravel.backend.modules.customWaterschap.userEntry;

import com.ravel.backend.modules.customWaterschap.kernproces.Kernproces;
import com.ravel.backend.modules.customWaterschap.waterschap.Waterschap;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mod_waterschappen_user")
public class ModuleWaterschappen {

	@Id
	@Setter(AccessLevel.NONE)
	@SequenceGenerator(name = "id", sequenceName = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "username", nullable = true, unique = true, length = 50)
	private String username;

	private UUID userUuid;

	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Column(name = "updated_at", nullable = true)
	private Date updatedAt;

	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "kernproces_id", referencedColumnName = "id", nullable = false)
	private Kernproces kernproces;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "waterschap_id", referencedColumnName = "id", nullable = false)
	private Waterschap waterschap;

	public Waterschap getWaterschap() {
		return waterschap;
	}

	public void setWaterschap(Waterschap waterschap) {
		this.waterschap = waterschap;
	}

	public Kernproces getKernproces() {
		return kernproces;
	}

	public void setKernproces(Kernproces kernproces) {
		this.kernproces = kernproces;
	}
}
