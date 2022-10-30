package com.ravel.backend.spacePro.repository;

import com.ravel.backend.spacePro.model.SpaceProOrganization;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceProOrganizationRepository
	extends JpaRepository<SpaceProOrganization, Long> {
	SpaceProOrganization findByOrganizationIdAndIsActive(
		UUID organizationId,
		boolean isActive
	);

	@Query(
		"select (count(s) > 0) from SpaceProOrganization s where s.organizationId = ?1 and s.isActive = ?2"
	)
	boolean existsByOrganizationIdAndIsActive(UUID organizationId, boolean isActive);
}
