package com.ravel.backend.appAuth.repository;

import com.ravel.backend.appAuth.model.AppRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
	AppRole findByAppRoleNameIgnoreCase(String appRoleName);

	boolean existsByAppRoleNameIgnoreCase(String appRoleName);

	boolean existsByAppRoleId(long appRoleId);

	AppRole findByAppRoleId(long appRoleId);

	@Query("select o from AppRole o where o.appRoleId in :ids")
	List<AppRole> findByIds(@Param("ids") List<Long> roleIdList);

	@Query("select a from AppRole a where upper(a.appRoleName) = upper(?1)")
	Optional<AppRole> findByAppRoleNameignoreCaseOptional(String appRoleName);

	AppRole findByAppRoleNameAndPurpose(String appRoleName, String purpose);

	boolean existsByAppRoleNameAndPurpose(String appRoleName, String purpose);
}
