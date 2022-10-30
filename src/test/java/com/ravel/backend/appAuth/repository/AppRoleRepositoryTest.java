package com.ravel.backend.appAuth.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.ravel.backend.appAuth.model.AppRole;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "h2" })
@DataJpaTest
class AppRoleRepositoryTest {

	@Autowired
	private AppRoleRepository appRoleRepository;

	@Test
	void testFindByAppRoleNameIgnoreCase() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertNull(this.appRoleRepository.findByAppRoleNameIgnoreCase("foo"));
	}

	@Test
	void testExistsByAppRoleNameIgnoreCase() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertFalse(this.appRoleRepository.existsByAppRoleNameIgnoreCase("foo"));
	}

	@Test
	void testExistsByAppRoleId() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertTrue(this.appRoleRepository.existsByAppRoleId(1L));
	}

	@Test
	void testFindByAppRoleId() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertNull(this.appRoleRepository.findByAppRoleId(1L));
	}

	@Test
	void testFindByIds() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertTrue(this.appRoleRepository.findByIds(new ArrayList<>()).isEmpty());
	}

	@Test
	void testFindByIds2() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertTrue(this.appRoleRepository.findByIds(new ArrayList<>()).isEmpty());
	}

	@Test
	void testFindByAppRoleNameignoreCaseOptional() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertFalse(
			this.appRoleRepository.findByAppRoleNameignoreCaseOptional("foo").isPresent()
		);
	}

	@Test
	void testFindByAppRoleNameAndPurpose() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertNull(this.appRoleRepository.findByAppRoleNameAndPurpose("foo", "foo"));
	}

	@Test
	void testExistsByAppRoleNameAndPurpose() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setPurpose("Purpose");
		appRole.setAppRoleName("42");

		AppRole appRole1 = new AppRole();
		appRole1.setAppPermissions(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appRole1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole1.setPurpose("Purpose");
		appRole1.setAppRoleName("App Role Name");
		this.appRoleRepository.save(appRole);
		this.appRoleRepository.save(appRole1);

		// Act and Assert
		assertFalse(this.appRoleRepository.existsByAppRoleNameAndPurpose("foo", "foo"));
		assertTrue(this.appRoleRepository.existsByAppRoleNameAndPurpose("42", "Purpose"));
	}
}
