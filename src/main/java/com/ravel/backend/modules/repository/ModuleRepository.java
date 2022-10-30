package com.ravel.backend.modules.repository;

import com.ravel.backend.modules.model.AppModule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<AppModule, Long> {
	@Query("select a from AppModule a where upper(a.moduleName) = upper(:moduleName)")
	AppModule findByModuleNameIgnoreCase(@Param("moduleName") String moduleName);

	@Query(
		"select a from AppModule a where upper(a.moduleName) = upper(:moduleName) and a.isActive = :isActive"
	)
	AppModule findByModuleNameIgnoreCaseAndIsActive(
		@Param("moduleName") String moduleName,
		@Param("isActive") boolean isActive
	);

	@Query(
		"select (count(a) > 0) from AppModule a where upper(a.moduleName) = upper(:moduleName)"
	)
	boolean existsByModuleNameIgnoreCase(@Param("moduleName") String moduleName);

	@Query(
		"select (count(a) > 0) from AppModule a where upper(a.moduleName) = upper(:moduleName) and a.isActive = :isActive"
	)
	boolean existsByModuleNameIgnoreCaseAndIsActive(
		@Param("moduleName") String moduleName,
		@Param("isActive") boolean isActive
	);

	@Query(value = "SELECT * FROM app_module WHERE is_active = TRUE", nativeQuery = true)
	List<AppModule> findActiveModules();
}
