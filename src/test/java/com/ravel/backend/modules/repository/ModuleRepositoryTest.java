package com.ravel.backend.modules.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.ravel.backend.modules.model.AppModule;
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
class ModuleRepositoryTest {

	@Autowired
	private ModuleRepository moduleRepository;

	@Test
	void testFindByModuleNameIgnoreCase() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setModuleColor("Module Color");
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setModuleName("42");
		appModule.setActive(true);
		appModule.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setAppModuleUsers(new HashSet<>());
		appModule.setModuleImageUrl("https://example.org/example");

		AppModule appModule1 = new AppModule();
		appModule1.setModuleColor("Module Color");
		appModule1.setAppModuleOrganizations(new HashSet<>());
		appModule1.setModuleName("Module Name");
		appModule1.setActive(true);
		appModule1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule1.setAppModuleUsers(new HashSet<>());
		appModule1.setModuleImageUrl("https://example.org/example");
		this.moduleRepository.save(appModule);
		this.moduleRepository.save(appModule1);

		// Act and Assert
		assertNull(this.moduleRepository.findByModuleNameIgnoreCase("foo"));
	}

	@Test
	void testFindByModuleNameIgnoreCaseAndIsActive() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setModuleColor("Module Color");
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setModuleName("42");
		appModule.setActive(true);
		appModule.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setAppModuleUsers(new HashSet<>());
		appModule.setModuleImageUrl("https://example.org/example");

		AppModule appModule1 = new AppModule();
		appModule1.setModuleColor("Module Color");
		appModule1.setAppModuleOrganizations(new HashSet<>());
		appModule1.setModuleName("Module Name");
		appModule1.setActive(true);
		appModule1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule1.setAppModuleUsers(new HashSet<>());
		appModule1.setModuleImageUrl("https://example.org/example");
		this.moduleRepository.save(appModule);
		this.moduleRepository.save(appModule1);

		// Act and Assert
		assertNull(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive("foo", true)
		);
	}

	@Test
	void testExistsByModuleNameIgnoreCase() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setModuleColor("Module Color");
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setModuleName("42");
		appModule.setActive(true);
		appModule.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setAppModuleUsers(new HashSet<>());
		appModule.setModuleImageUrl("https://example.org/example");

		AppModule appModule1 = new AppModule();
		appModule1.setModuleColor("Module Color");
		appModule1.setAppModuleOrganizations(new HashSet<>());
		appModule1.setModuleName("Module Name");
		appModule1.setActive(true);
		appModule1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule1.setAppModuleUsers(new HashSet<>());
		appModule1.setModuleImageUrl("https://example.org/example");
		this.moduleRepository.save(appModule);
		this.moduleRepository.save(appModule1);

		// Act and Assert
		assertFalse(this.moduleRepository.existsByModuleNameIgnoreCase("foo"));
	}

	@Test
	void testExistsByModuleNameIgnoreCaseAndIsActive() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setModuleColor("Module Color");
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setModuleName("42");
		appModule.setActive(true);
		appModule.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setAppModuleUsers(new HashSet<>());
		appModule.setModuleImageUrl("https://example.org/example");

		AppModule appModule1 = new AppModule();
		appModule1.setModuleColor("Module Color");
		appModule1.setAppModuleOrganizations(new HashSet<>());
		appModule1.setModuleName("Module Name");
		appModule1.setActive(true);
		appModule1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule1.setAppModuleUsers(new HashSet<>());
		appModule1.setModuleImageUrl("https://example.org/example");
		this.moduleRepository.save(appModule);
		this.moduleRepository.save(appModule1);

		// Act and Assert
		assertFalse(
			this.moduleRepository.existsByModuleNameIgnoreCaseAndIsActive("foo", true)
		);
	}

	@Test
	void testFindActiveModules() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setModuleColor("Module Color");
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setModuleName("42");
		appModule.setActive(true);
		appModule.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setAppModuleUsers(new HashSet<>());
		appModule.setModuleImageUrl("https://example.org/example");

		AppModule appModule1 = new AppModule();
		appModule1.setModuleColor("Module Color");
		appModule1.setAppModuleOrganizations(new HashSet<>());
		appModule1.setModuleName("Module Name");
		appModule1.setActive(true);
		appModule1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule1.setAppModuleUsers(new HashSet<>());
		appModule1.setModuleImageUrl("https://example.org/example");
		this.moduleRepository.save(appModule);
		this.moduleRepository.save(appModule1);

		// Act and Assert
		assertEquals(2, this.moduleRepository.findActiveModules().size());
	}
}
