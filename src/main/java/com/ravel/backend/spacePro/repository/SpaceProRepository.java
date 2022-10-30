package com.ravel.backend.spacePro.repository;

import com.ravel.backend.spacePro.model.SpacePro;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SpaceProRepository extends JpaRepository<SpacePro, Long> {
	@Query("select s from SpacePro s where s.spaceUuid = ?1 and s.isActive = ?2")
	Optional<SpacePro> findBySpaceUuidAndIsActive(UUID spaceUuid, boolean isActive);

	@Query(
		"select (count(s) > 0) from SpacePro s where upper(s.sessionSpaceId) = upper(?1)"
	)
	boolean existsByPhotonRoomIdIgnoreCase(String photonRoomId);

	@Query("select (count(s) > 0) from SpacePro s where s.spaceUuid = ?1 and s.isActive = ?2")
	boolean existsBySpaceUuidAndIsActive(UUID spaceUuid, boolean isActive);

	@Query(
		"select s from SpacePro s left join s.spaceProOrganizations spaceProOrganizations where spaceProOrganizations.organizationId = ?1 and s.isActive = ?2"
	)
	List<SpacePro> findBySpaceProOrganizations_OrganizationIdAndIsActive(
		UUID organizationId,
		boolean isActive
	);

	@Modifying
	@Query("delete from SpacePro s where s.spaceUuid = ?1")
	int deleteBySpaceUuid(UUID spaceUuid);

	@Query("select s from SpacePro s where s.spaceUuid = ?1")
	Optional<SpacePro> findBySpaceUuid(UUID spaceUuid);

	@Transactional
	@Modifying
	@Query("update SpacePro s set s.sessionSpaceId = ?1 where s.id = ?2")
	void updatePhotonRoomIdById(String photonRoomId, Long id);

	@Query("select s from SpacePro s where s.id = ?1 and s.isActive = ?2")
	Optional<SpacePro> findByIdAndIsActive(Long id, boolean isActive);

}
