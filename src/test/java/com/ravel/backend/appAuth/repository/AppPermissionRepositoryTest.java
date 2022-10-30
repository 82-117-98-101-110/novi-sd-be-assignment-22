package com.ravel.backend.appAuth.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ravel.backend.appAuth.model.AppPermission;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "h2" })
@DataJpaTest
class AppPermissionRepositoryTest {

	@Autowired
	private AppPermissionRepository appPermissionRepository;

	@Test
	void testFindByAppPermissionNameIgnoreCase() {
		// Arrange
		AppPermission appPermission = new AppPermission();
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setAppPermissionName("42");
		appPermission.setAppRoles(new HashSet<>());

		AppPermission appPermission1 = new AppPermission();
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appPermission1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appPermission1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission1.setAppPermissionName("App Permission Name");
		appPermission1.setAppRoles(new HashSet<>());
		this.appPermissionRepository.save(appPermission);
		this.appPermissionRepository.save(appPermission1);

		// Act and Assert
		assertNull(this.appPermissionRepository.findByAppPermissionNameIgnoreCase("foo"));
	}

	@Test
	void testExistsByAppPermissionNameIgnoreCase() {
		// Arrange
		AppPermission appPermission = new AppPermission();
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setAppPermissionName("42");
		appPermission.setAppRoles(new HashSet<>());

		AppPermission appPermission1 = new AppPermission();
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appPermission1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appPermission1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission1.setAppPermissionName("App Permission Name");
		appPermission1.setAppRoles(new HashSet<>());
		this.appPermissionRepository.save(appPermission);
		this.appPermissionRepository.save(appPermission1);

		// Act and Assert
		assertFalse(
			this.appPermissionRepository.existsByAppPermissionNameIgnoreCase("foo")
		);
	}
}
