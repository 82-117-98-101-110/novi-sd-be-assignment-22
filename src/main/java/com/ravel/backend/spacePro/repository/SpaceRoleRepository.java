package com.ravel.backend.spacePro.repository;

import com.ravel.backend.spacePro.model.SpaceRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRoleRepository extends JpaRepository<SpaceRole, Long> {
	@Query(
		"select (count(s) > 0) from SpaceRole s where upper(s.appRoleName) = upper(?1)"
	)
	boolean existsByAppRoleNameIgnoreCase(String appRoleName);

	@Query("select s from SpaceRole s where upper(s.appRoleName) = upper(?1)")
	Optional<SpaceRole> findByAppRoleNameIgnoreCase(String appRoleName);
}
