package com.ravel.backend.modules.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ravel.backend.modules.model.AppModule;
import com.ravel.backend.modules.model.AppModuleOrganization;
import com.ravel.backend.modules.repository.AppModuleOrganizationRepository;
import com.ravel.backend.modules.repository.ModuleRepository;
import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.shared.exception.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { AppModuleOrganizationService.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppModuleOrganizationServiceTest {

	@MockBean
	private AppModuleOrganizationRepository appModuleOrganizationRepository;

	@Autowired
	private AppModuleOrganizationService appModuleOrganizationService;

	@MockBean
	private ModuleRepository moduleRepository;

	@MockBean
	private OrganizationService organizationService;

	@Test
	void testAddModuleToOrganization() {
		// Arrange
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		appModuleOrganization.setEnrolledModules(new HashSet<>());
		appModuleOrganization.setOrganizationId(UUID.randomUUID());
		Optional<AppModuleOrganization> ofResult = Optional.of(appModuleOrganization);

		AppModuleOrganization appModuleOrganization1 = new AppModuleOrganization();
		appModuleOrganization1.setActive(true);
		appModuleOrganization1.setCreatedAt(null);
		appModuleOrganization1.setEnrolledModules(new HashSet<>());
		appModuleOrganization1.setOrganizationId(UUID.randomUUID());
		when(this.appModuleOrganizationRepository.save((AppModuleOrganization) any()))
			.thenReturn(appModuleOrganization1);
		when(this.appModuleOrganizationRepository.findByOrganizationId((UUID) any()))
			.thenReturn(ofResult);
		when(this.appModuleOrganizationRepository.existsByOrganizationId((UUID) any()))
			.thenReturn(true);

		// Act and Assert
		assertNull(
			this.appModuleOrganizationService.addModuleToOrganization(
					"Organization Name",
					"Module Name"
				)
		);
		verify(this.organizationService).getOrganizationId((String) any());
		verify(this.moduleRepository)
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.appModuleOrganizationRepository).existsByOrganizationId((UUID) any());
		verify(this.appModuleOrganizationRepository).findByOrganizationId((UUID) any());
		verify(this.appModuleOrganizationRepository).save((AppModuleOrganization) any());
	}

	@Test
	void testAddModuleToOrganization2() {
		// Arrange
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		appModuleOrganization.setEnrolledModules(new HashSet<>());
		appModuleOrganization.setOrganizationId(UUID.randomUUID());
		when(this.appModuleOrganizationRepository.save((AppModuleOrganization) any()))
			.thenReturn(appModuleOrganization);
		when(this.appModuleOrganizationRepository.findByOrganizationId((UUID) any()))
			.thenReturn(Optional.empty());
		when(this.appModuleOrganizationRepository.existsByOrganizationId((UUID) any()))
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.appModuleOrganizationService.addModuleToOrganization(
						"Organization Name",
						"Module Name"
					)
		);
		verify(this.organizationService).getOrganizationId((String) any());
		verify(this.appModuleOrganizationRepository).existsByOrganizationId((UUID) any());
		verify(this.appModuleOrganizationRepository).findByOrganizationId((UUID) any());
	}

	@Test
	void testAddModuleToOrganization3() {
		// Arrange
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		appModuleOrganization.setEnrolledModules(new HashSet<>());
		appModuleOrganization.setOrganizationId(UUID.randomUUID());
		Optional<AppModuleOrganization> ofResult = Optional.of(appModuleOrganization);

		AppModuleOrganization appModuleOrganization1 = new AppModuleOrganization();
		appModuleOrganization1.setActive(true);
		appModuleOrganization1.setCreatedAt(null);
		appModuleOrganization1.setEnrolledModules(new HashSet<>());
		appModuleOrganization1.setOrganizationId(UUID.randomUUID());
		when(this.appModuleOrganizationRepository.save((AppModuleOrganization) any()))
			.thenReturn(appModuleOrganization1);
		when(this.appModuleOrganizationRepository.findByOrganizationId((UUID) any()))
			.thenReturn(ofResult);
		when(this.appModuleOrganizationRepository.existsByOrganizationId((UUID) any()))
			.thenReturn(false);

		// Act and Assert
		assertNull(
			this.appModuleOrganizationService.addModuleToOrganization(
					"Organization Name",
					"Module Name"
				)
		);
		verify(this.organizationService).getOrganizationId((String) any());
		verify(this.moduleRepository)
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.appModuleOrganizationRepository).existsByOrganizationId((UUID) any());
		verify(this.appModuleOrganizationRepository).save((AppModuleOrganization) any());
	}

	@Test
	void testGetOrganizationModules() {
		// Arrange
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		appModuleOrganization.setEnrolledModules(new HashSet<>());
		appModuleOrganization.setOrganizationId(UUID.randomUUID());
		Optional<AppModuleOrganization> ofResult = Optional.of(appModuleOrganization);
		when(this.appModuleOrganizationRepository.findByOrganizationId((UUID) any()))
			.thenReturn(ofResult);

		// Act and Assert
		assertSame(
			appModuleOrganization,
			this.appModuleOrganizationService.getOrganizationModules("Organization Name")
		);
		verify(this.organizationService).getOrganizationId((String) any());
		verify(this.appModuleOrganizationRepository).findByOrganizationId((UUID) any());
	}

	@Test
	void testGetOrganizationModules2() {
		// Arrange
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());
		when(this.appModuleOrganizationRepository.findByOrganizationId((UUID) any()))
			.thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.appModuleOrganizationService.getOrganizationModules(
						"Organization Name"
					)
		);
		verify(this.organizationService).getOrganizationId((String) any());
		verify(this.appModuleOrganizationRepository).findByOrganizationId((UUID) any());
	}

	@Test
	void testGetOrganizationModulesSet() {
		// Arrange
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		HashSet<AppModule> appModuleSet = new HashSet<>();
		appModuleOrganization.setEnrolledModules(appModuleSet);
		appModuleOrganization.setOrganizationId(UUID.randomUUID());
		Optional<AppModuleOrganization> ofResult = Optional.of(appModuleOrganization);
		when(this.appModuleOrganizationRepository.findByOrganizationId((UUID) any()))
			.thenReturn(ofResult);

		// Act
		Set<AppModule> actualOrganizationModulesSet =
			this.appModuleOrganizationService.getOrganizationModulesSet(
					"Organization Name"
				);

		// Assert
		assertSame(appModuleSet, actualOrganizationModulesSet);
		assertTrue(actualOrganizationModulesSet.isEmpty());
		verify(this.organizationService).getOrganizationId((String) any());
		verify(this.appModuleOrganizationRepository).findByOrganizationId((UUID) any());
	}

	@Test
	void testGetOrganizationModulesSet2() {
		// Arrange
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());
		when(this.appModuleOrganizationRepository.findByOrganizationId((UUID) any()))
			.thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.appModuleOrganizationService.getOrganizationModulesSet(
						"Organization Name"
					)
		);
		verify(this.organizationService).getOrganizationId((String) any());
		verify(this.appModuleOrganizationRepository).findByOrganizationId((UUID) any());
	}

	@Test
	void testGetModuleSetForOrganizations() {
		// Arrange
		when(
			this.appModuleOrganizationRepository.findByOrganizationIdAndIsActive(
					(List<UUID>) any()
				)
		)
			.thenReturn(new ArrayList<>());

		// Act and Assert
		assertTrue(
			this.appModuleOrganizationService.getModuleSetForOrganizations(
					new ArrayList<>()
				)
				.isEmpty()
		);
		verify(this.appModuleOrganizationRepository)
			.findByOrganizationIdAndIsActive((List<UUID>) any());
	}
}
