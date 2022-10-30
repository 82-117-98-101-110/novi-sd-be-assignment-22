package com.ravel.backend.modules.customWaterschap.userEntry;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleWaterschappenRepository
	extends JpaRepository<ModuleWaterschappen, Long> {
	@Query(
		value = "SELECT * FROM mod_waterschappen_user WHERE is_active = TRUE",
		nativeQuery = true
	)
	List<ModuleWaterschappen> getEntries();

	@Query("select m from ModuleWaterschappen m where upper(m.username) = upper(?1)")
	ModuleWaterschappen findModuleWaterschappenByUsername(String username);

	@Query(
		"select (count(m) > 0) from ModuleWaterschappen m where upper(m.username) = upper(?1)"
	)
	boolean existsByUsername(String username);

	@Query("select m from ModuleWaterschappen m where m.userUuid = ?1")
	ModuleWaterschappen findByUserUuid(UUID userUuid);

	boolean existsByUserUuid(UUID userUuid);
}
