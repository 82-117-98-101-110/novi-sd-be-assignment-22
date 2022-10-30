package com.ravel.backend.spacePro.repository;

import com.ravel.backend.spacePro.model.SpaceProUser;
import com.ravel.backend.spacePro.model.SpaceProUserId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceProUserRepository
	extends JpaRepository<SpaceProUser, SpaceProUserId> {
	@Query("select s from SpaceProUser s where s.spaceProUserId = ?1")
	Optional<SpaceProUser> findBySpaceProUserId(SpaceProUserId spaceProUserId);

	@Query("select (count(s) > 0) from SpaceProUser s where s.spaceProUserId = ?1")
	boolean existsBySpaceProUserId(SpaceProUserId spaceProUserId);

	@Modifying
	@Query("delete from SpaceProUser s where s.spaceProUserId.spaceProId = ?1")
	int deleteBySpaceProUserId_SpaceProId(Long spaceProId);

	@Query("select s from SpaceProUser s where s.spaceProUserId.spaceProId = ?1")
	List<SpaceProUser> findBySpaceProUserId_SpaceProId(Long spaceProId);
}
