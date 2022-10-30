package com.ravel.backend.modules.repository;

import com.ravel.backend.modules.model.AppModuleUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppModuleUserRepository extends JpaRepository<AppModuleUser, Long> {
	@Query(
		"select (count(a) > 0) from AppModuleUser a where upper(a.username) = upper(?1)"
	)
	boolean existsByUsernameIgnoreCase(String username);

	@Query(
		"select a from AppModuleUser a where upper(a.username) = upper(?1) and a.isActive = ?2"
	)
	AppModuleUser findByUsernameIgnoreCaseAndIsActive(String username, boolean isActive);

	@Query("select (count(a) > 0) from AppModuleUser a where a.userUUID = ?1")
	boolean existsByUserUUID(UUID userUUID);

	@Query("select a from AppModuleUser a where a.userUUID = ?1 and a.isActive = ?2")
	AppModuleUser findByUserUUIDAndIsActive(UUID userUUID, boolean isActive);
}
