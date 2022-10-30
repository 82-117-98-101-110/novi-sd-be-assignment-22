package com.ravel.backend.spaceLite.repository;

import com.ravel.backend.spaceLite.model.SpaceLiteOrganization;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceLiteOrganizationRepository
	extends JpaRepository<SpaceLiteOrganization, Long> {
	@Query(
		"select s from SpaceLiteOrganization s where s.organizationId in :organizationIds and s.isActive = true"
	)
	List<SpaceLiteOrganization> findByOrganizationIdAndIsActive(
		@Param("organizationIds") List<UUID> organizationId
	);

	@Query(
		"select s from SpaceLiteOrganization s where s.organizationId = ?1 and s.isActive = ?2"
	)
	Optional<SpaceLiteOrganization> findByOrganizationIdAndIsActiveSingle(
		UUID organizationId,
		boolean isActive
	);

	boolean existsByOrganizationIdAndIsActive(UUID organizationId, boolean isActive);
}
