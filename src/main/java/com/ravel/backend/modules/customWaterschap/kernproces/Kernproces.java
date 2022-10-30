package com.ravel.backend.modules.customWaterschap.kernproces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ravel.backend.modules.customWaterschap.userEntry.ModuleWaterschappen;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mod_waterschappen_kernproces")
public class Kernproces {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@Column(nullable = false, unique = true)
	private String kernProcesName;

	private String kernProcesImageUrl;

	@JsonIgnore
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@JsonIgnore
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;

	@JsonIgnore
	@OneToMany(mappedBy = "kernproces", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ModuleWaterschappen> moduleWaterschappens;

	public Set<ModuleWaterschappen> getModuleWaterschappens() {
		return moduleWaterschappens;
	}

	public void setModuleWaterschappens(Set<ModuleWaterschappen> moduleWaterschappens) {
		this.moduleWaterschappens = moduleWaterschappens;
	}
}
