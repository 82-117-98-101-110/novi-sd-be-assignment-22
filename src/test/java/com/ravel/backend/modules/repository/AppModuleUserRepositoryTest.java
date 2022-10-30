package com.ravel.backend.modules.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ravel.backend.modules.model.AppModuleUser;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "h2" })
@DataJpaTest
class AppModuleUserRepositoryTest {

	@Autowired
	private AppModuleUserRepository appModuleUserRepository;

	@Test
	void testExistsByUsernameIgnoreCase() {
		// Arrange
		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setUsername("janedoe");
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setUserUUID(UUID.randomUUID());

		AppModuleUser appModuleUser1 = new AppModuleUser();
		appModuleUser1.setUsername("janedoe");
		appModuleUser1.setEnrolledModules(new HashSet<>());
		appModuleUser1.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModuleUser1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser1.setUserUUID(UUID.randomUUID());
		this.appModuleUserRepository.save(appModuleUser);
		this.appModuleUserRepository.save(appModuleUser1);

		// Act and Assert
		assertFalse(this.appModuleUserRepository.existsByUsernameIgnoreCase("foo"));
	}

	@Test
	void testFindByUsernameIgnoreCaseAndIsActive() {
		// Arrange
		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setUsername("janedoe");
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setUserUUID(UUID.randomUUID());

		AppModuleUser appModuleUser1 = new AppModuleUser();
		appModuleUser1.setUsername("janedoe");
		appModuleUser1.setEnrolledModules(new HashSet<>());
		appModuleUser1.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModuleUser1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser1.setUserUUID(UUID.randomUUID());
		this.appModuleUserRepository.save(appModuleUser);
		this.appModuleUserRepository.save(appModuleUser1);

		// Act and Assert
		assertNull(
			this.appModuleUserRepository.findByUsernameIgnoreCaseAndIsActive("foo", true)
		);
	}

	@Test
	void testExistsByUserUUID() {
		// Arrange
		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setUsername("janedoe");
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setUserUUID(UUID.randomUUID());

		AppModuleUser appModuleUser1 = new AppModuleUser();
		appModuleUser1.setUsername("janedoe");
		appModuleUser1.setEnrolledModules(new HashSet<>());
		appModuleUser1.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModuleUser1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser1.setUserUUID(UUID.randomUUID());
		this.appModuleUserRepository.save(appModuleUser);
		this.appModuleUserRepository.save(appModuleUser1);

		// Act and Assert
		assertFalse(this.appModuleUserRepository.existsByUserUUID(UUID.randomUUID()));
	}

	@Test
	void testFindByUserUUIDAndIsActive() {
		// Arrange
		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setUsername("janedoe");
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setUserUUID(UUID.randomUUID());

		AppModuleUser appModuleUser1 = new AppModuleUser();
		appModuleUser1.setUsername("janedoe");
		appModuleUser1.setEnrolledModules(new HashSet<>());
		appModuleUser1.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModuleUser1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser1.setUserUUID(UUID.randomUUID());
		this.appModuleUserRepository.save(appModuleUser);
		this.appModuleUserRepository.save(appModuleUser1);

		// Act and Assert
		assertNull(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					UUID.randomUUID(),
					true
				)
		);
	}
}
