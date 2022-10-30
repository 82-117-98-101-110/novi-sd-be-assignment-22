package com.ravel.backend.appAuth.repository;

import com.ravel.backend.appAuth.model.AppPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppPermissionRepository extends JpaRepository<AppPermission, Long> {
	AppPermission findByAppPermissionNameIgnoreCase(String appPermissionName);

	boolean existsByAppPermissionNameIgnoreCase(String appPermissionName);
}
