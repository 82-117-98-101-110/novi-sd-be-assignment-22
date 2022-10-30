package com.ravel.backend.modules.customAde;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdeRepository extends JpaRepository<Ade, Long> {
	@Query(
		"select a from Ade a where a.username = ?1 and a.isActive = ?2 order by a.username"
	)
	Ade findAdeByUsername(String username, boolean isActive);

	Ade findByUsernameIgnoreCaseAndIsActive(String username, boolean isActive);

	boolean existsByUsernameIgnoreCase(String username);

	Boolean existsByUsername(String username);

	@Query(
		value = "SELECT * FROM mod_ade_user WHERE is_active = TRUE",
		nativeQuery = true
	)
	List<Ade> getEntries();
}
