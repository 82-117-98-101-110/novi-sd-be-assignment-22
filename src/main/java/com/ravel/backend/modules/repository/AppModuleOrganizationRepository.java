package com.ravel.backend.modules.repository;

import com.ravel.backend.modules.model.AppModuleOrganization;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//TODO write tests
@Repository
public interface AppModuleOrganizationRepository
	extends JpaRepository<AppModuleOrganization, Long> {
	@Query(
		"select (count(a) > 0) from AppModuleOrganization a where a.organizationId = ?1"
	)
	boolean existsByOrganizationId(UUID organizationId);

	@Query("select a from AppModuleOrganization a where a.organizationId = ?1")
	Optional<AppModuleOrganization> findByOrganizationId(UUID organizationId);

	@Query(
		"select a from AppModuleOrganization a where a.organizationId in :organizationIds and a.isActive = true"
	)
	List<AppModuleOrganization> findByOrganizationIdAndIsActive(
		@Param("organizationIds") List<UUID> organizationIds
	);
}
