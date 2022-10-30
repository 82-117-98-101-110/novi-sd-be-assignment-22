package com.ravel.backend.spacePro.repository;

import com.ravel.backend.spacePro.model.SpaceProUserDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceProUserDetailsRepository
	extends JpaRepository<SpaceProUserDetails, Long> {
	@Modifying
	@Query(
		"delete from SpaceProUserDetails s where s.spaceProUser.spaceProUserId.spaceProId = ?1"
	)
	int deleteBySpaceProUser_SpaceProUserId_SpaceProId(Long spaceProId);

	@Modifying
	@Query("delete from SpaceProUserDetails s where s.spaceProUserDetailsId in :ids")
	int deleteBySpaceProUserDetailsId(@Param("ids") List<Long> ids);
}
