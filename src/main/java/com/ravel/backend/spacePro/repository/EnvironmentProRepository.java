package com.ravel.backend.spacePro.repository;

import com.ravel.backend.spacePro.model.EnvironmentPro;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentProRepository extends JpaRepository<EnvironmentPro, Long> {
	@Query(
		"select e from EnvironmentPro e where upper(e.name) = upper(?1) and e.isActive = ?2"
	)
	Optional<EnvironmentPro> findByNameIgnoreCase(String name, boolean isActive);

	@Query(
		"select e from EnvironmentPro e where e.environmentUuid = ?1 and e.isActive = ?2"
	)
	Optional<EnvironmentPro> findByEnvironmentUuidAndIsActive(
		UUID environmentUuid,
		boolean isActive
	);

	@Query("select e from EnvironmentPro e where upper(e.name) = upper(?1)")
	EnvironmentPro findByName(String name);

	@Query(
		"select (count(e) > 0) from EnvironmentPro e where upper(e.unitySceneName) = upper(?1)"
	)
	boolean existsByUnitySceneNameIgnoreCase(String unitySceneName);

	List<EnvironmentPro> findByIsActive(boolean isActive);

	@Query("select e from EnvironmentPro e where e.isPublicEnvironment = ?1 and e.isActive = ?2")
	List<EnvironmentPro> findByIsPublicEnvironmentAndIsActive(boolean isPublicEnvironment, boolean isActive);


}
