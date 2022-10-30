package com.ravel.backend.spaceLite.repository;

import com.ravel.backend.spaceLite.model.SpaceLite;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceLiteRepository extends JpaRepository<SpaceLite, Long> {
	@Query("select s from SpaceLite s where upper(s.name) = upper(?1)")
	SpaceLite findByName(String name);

	@Query("select s from SpaceLite s where upper(s.name) = upper(?1)")
	Optional<SpaceLite> findByNameIgnoreCase(String name);

	boolean existsByNameIgnoreCase(String name);

	Optional<SpaceLite> findByNameIgnoreCaseAndIsActive(String name, boolean isActive);
}
