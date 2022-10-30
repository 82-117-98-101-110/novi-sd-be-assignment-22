package com.ravel.backend.organization.repository;

import com.ravel.backend.organization.model.OrganizationUserRole;
import com.ravel.backend.organization.model.OrganizationUserRoleId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationUserRoleRepository
	extends JpaRepository<OrganizationUserRole, OrganizationUserRoleId> {
	List<OrganizationUserRole> findById_UserUuid(UUID userUuid);

	@Query(
		"select o from OrganizationUserRole o where o.id.userUuid = ?1 and o.isActiveUser = ?2"
	)
	List<OrganizationUserRole> findByUserUuidAndIsActive(
		UUID userUuid,
		boolean isActiveUser
	);

	OrganizationUserRole findByOrganization_OrganizationName(String organizationName);

	List<OrganizationUserRole> findByOrganization_OrganizationNameIgnoreCase(
		String organizationName
	);

	@Query("select o from OrganizationUserRole o where o.id = ?1")
	Optional<OrganizationUserRole> findByIdOptional(OrganizationUserRoleId id);

	@Query(
		"select o from OrganizationUserRole o where o.organization.organizationName = ?1 and o.id.userUuid = ?2 and o.isActiveUser = ?3"
	)
	OrganizationUserRole findByOrganization_OrganizationNameAndId_UserUuidAndIsActiveUser(
		String organizationName,
		UUID userUuid,
		boolean isActiveUser
	);

	@Query(
		"select o from OrganizationUserRole o where o.id.userUuid = ?1 and o.isActiveUser = ?2"
	)
	List<OrganizationUserRole> findById_UserUuidAndIsActiveUser(
		UUID userUuid,
		boolean isActiveUser
	);

	@Query(
		"select o from OrganizationUserRole o where o.id.organizationId in :uuids and o.isActiveUser = true "
	)
	List<OrganizationUserRole> findById_OrganizationIdAndIsActiveUser(
		@Param("uuids") List<UUID> userUUIDS
	);

	@Query("select (count(o) > 0) from OrganizationUserRole o where o.id = ?1 and o.isActiveUser = ?2")
	boolean existsByIdAndIsActiveUser(OrganizationUserRoleId id, boolean isActiveUser);


}
