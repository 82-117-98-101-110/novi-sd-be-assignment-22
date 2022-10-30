package com.ravel.backend.organization.repository;

import com.ravel.backend.organization.model.OrganizationInvite;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface OrganizationInviteRepository
	extends JpaRepository<OrganizationInvite, Long> {
	Optional<OrganizationInvite> findByToken(String token);

	@Transactional
	@Modifying
	@Query(
		"UPDATE OrganizationInvite o " + "SET o.confirmedAt = ?2 " + "WHERE o.token = ?1"
	)
	int updateConfirmedAt(String token, OffsetDateTime confirmedAt);

	@Query(
		"select o from OrganizationInvite o where o.userUUID = ?1 and o.organizationUUID = ?2"
	)
	Optional<OrganizationInvite> findByUserUUIDAndOrganizationUUID(
		UUID userUUID,
		UUID organizationUUID
	);
}
