package com.ravel.backend.organization.repository;

import com.ravel.backend.organization.model.Organization;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	@Query("select o from Organization o where o.organizationName = ?1")
	Organization findOrganizationByOrganizationName(String organizationName);

	@Query(
		value = "SELECT * FROM organization WHERE is_active = TRUE",
		nativeQuery = true
	)
	List<Organization> findAllActiveOrganizations();

	Optional<Organization> findByOrganizationNameIgnoreCaseAndIsActive(
		String organizationName,
		boolean isActive
	);

	Organization findByOrganizationIdAndIsActive(UUID organizationId, boolean isActive);

	@Query("select o from Organization o where o.organizationId in :ids")
	List<Organization> findByMulIds(@Param("ids") List<UUID> orgIdsList);

	@Query("select o from Organization o where o.organizationId in :organizationNames")
	List<Organization> findByOrganizationNames(
		@Param("organizationNames") List<String> organizationNames
	);

	Organization findByOrganizationNameIgnoreCase(String organizationName);

	boolean existsByOrganizationNameIgnoreCase(String organizationName);

	boolean existsByOrganizationId(UUID organizationId);

	@Query(
		"select o from Organization o left join o.organizationUserRoles organizationUserRoles where organizationUserRoles.id.userUuid = ?1"
	)
	List<Organization> findByOrganizationUserRoles_Id_UserUuid(UUID userUuid);

	List<Organization> findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser(
		UUID userUuid,
		boolean isActiveUser
	);
}
