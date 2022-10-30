package com.ravel.backend.modules.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.modules.dto.CreateModuleDto;
import com.ravel.backend.modules.model.AppModule;
import com.ravel.backend.modules.repository.ModuleRepository;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { ModuleServiceImpl.class, ModuleAwsS3Service.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class ModuleServiceImplTest {

	@MockBean
	private ModuleRepository moduleRepository;

	@Autowired
	private ModuleServiceImpl moduleServiceImpl;

	//	@Test
	//	void testCreateActiveModule() {
	//		// Arrange
	//		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
	//			.thenReturn(true);
	//
	//		// Act and Assert
	//		assertThrows(
	//			AlreadyExistException.class,
	//			() ->
	//				this.moduleServiceImpl.createActiveModule(
	//						"Module Name",
	//						"The characteristics of someone or something",
	//						"https://example.org/example"
	//					)
	//		);
	//		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
	//	}
	//
	//	@Test
	//	void testCreateActiveModule2() {
	//		// Arrange
	//		AppModule appModule = new AppModule();
	//		appModule.setActive(true);
	//		appModule.setAppModuleOrganizations(new HashSet<>());
	//		appModule.setAppModuleUsers(new HashSet<>());
	//		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
	//		appModule.setCreatedAt(
	//			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
	//		);
	//		appModule.setDescription("The characteristics of someone or something");
	//		appModule.setModuleColor("Module Color");
	//		appModule.setModuleImageUrl("https://example.org/example");
	//		appModule.setModuleName("Module Name");
	//		when(this.moduleRepository.save((AppModule) any())).thenReturn(appModule);
	//		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
	//			.thenReturn(false);
	//
	//		// Act and Assert
	//		assertSame(
	//			appModule,
	//			this.moduleServiceImpl.createActiveModule(
	//					"Module Name",
	//					"The characteristics of someone or something",
	//					"https://example.org/example"
	//				)
	//		);
	//		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
	//		verify(this.moduleRepository).save((AppModule) any());
	//		assertTrue(this.moduleServiceImpl.findActiveModules().isEmpty());
	//	}

	@Test
	void testCreateActiveModule() {
		// Arrange
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(true);

		CreateModuleDto createModuleDto = new CreateModuleDto();
		createModuleDto.setDescription("The characteristics of someone or something");
		createModuleDto.setModuleName("Module Name");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() -> this.moduleServiceImpl.createActiveModule(createModuleDto)
		);
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
	}

	@Test
	void testCreateActiveModule2() {
		// Arrange
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenThrow(new AlreadyExistException("An error occurred"));

		CreateModuleDto createModuleDto = new CreateModuleDto();
		createModuleDto.setDescription("The characteristics of someone or something");
		createModuleDto.setModuleName("Module Name");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() -> this.moduleServiceImpl.createActiveModule(createModuleDto)
		);
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
	}

	@Test
	void testCreateActiveModule3() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(this.moduleRepository.save((AppModule) any())).thenReturn(appModule);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(false);

		CreateModuleDto createModuleDto = new CreateModuleDto();
		createModuleDto.setDescription("The characteristics of someone or something");
		createModuleDto.setModuleName("Module Name");

		// Act and Assert
		assertSame(appModule, this.moduleServiceImpl.createActiveModule(createModuleDto));
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
		verify(this.moduleRepository).save((AppModule) any());
		assertTrue(this.moduleServiceImpl.findActiveModules().isEmpty());
	}

	@Test
	void testCreateActiveModule4() {
		// Arrange
		when(this.moduleRepository.save((AppModule) any()))
			.thenThrow(new AlreadyExistException("An error occurred"));
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(false);

		CreateModuleDto createModuleDto = new CreateModuleDto();
		createModuleDto.setDescription("The characteristics of someone or something");
		createModuleDto.setModuleName("Module Name");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() -> this.moduleServiceImpl.createActiveModule(createModuleDto)
		);
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
		verify(this.moduleRepository).save((AppModule) any());
	}

	@Test
	void testGetModule() {
		// Arrange
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
		when(this.moduleRepository.findByModuleNameIgnoreCase((String) any()))
			.thenReturn(appModule);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertSame(appModule, this.moduleServiceImpl.getModule("Module Name"));
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
		verify(this.moduleRepository).findByModuleNameIgnoreCase((String) any());
		assertTrue(this.moduleServiceImpl.findActiveModules().isEmpty());
	}

	@Test
	void testGetModule2() {
		// Arrange
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
		when(this.moduleRepository.findByModuleNameIgnoreCase((String) any()))
			.thenReturn(appModule);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.moduleServiceImpl.getModule("Module Name")
		);
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
	}

	@Test
	void testFindActiveModule() {
		// Arrange
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
		when(
			this.moduleRepository.existsByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(true);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertSame(appModule, this.moduleServiceImpl.findActiveModule("Module Name"));
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
		verify(this.moduleRepository)
			.existsByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.moduleRepository)
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		assertTrue(this.moduleServiceImpl.findActiveModules().isEmpty());
	}

	@Test
	void testFindActiveModule2() {
		// Arrange
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
		when(
			this.moduleRepository.existsByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(false);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.moduleServiceImpl.findActiveModule("Module Name")
		);
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
		verify(this.moduleRepository)
			.existsByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
	}

	@Test
	void testFindActiveModule3() {
		// Arrange
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
		when(
			this.moduleRepository.existsByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(true);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.moduleServiceImpl.findActiveModule("Module Name")
		);
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
	}

	@Test
	void testFindActiveModules() {
		// Arrange
		ArrayList<AppModule> appModuleList = new ArrayList<>();
		when(this.moduleRepository.findActiveModules()).thenReturn(appModuleList);

		// Act
		List<AppModule> actualFindActiveModulesResult =
			this.moduleServiceImpl.findActiveModules();

		// Assert
		assertSame(appModuleList, actualFindActiveModulesResult);
		assertTrue(actualFindActiveModulesResult.isEmpty());
		verify(this.moduleRepository).findActiveModules();
		assertTrue(this.moduleServiceImpl.findAllModules().isEmpty());
	}

	@Test
	void testFindAllModules() {
		// Arrange
		ArrayList<AppModule> appModuleList = new ArrayList<>();
		when(this.moduleRepository.findAll()).thenReturn(appModuleList);

		// Act
		List<AppModule> actualFindAllModulesResult =
			this.moduleServiceImpl.findAllModules();

		// Assert
		assertSame(appModuleList, actualFindAllModulesResult);
		assertTrue(actualFindAllModulesResult.isEmpty());
		verify(this.moduleRepository).findAll();
		assertTrue(this.moduleServiceImpl.findActiveModules().isEmpty());
	}

	@Test
	void testDeactivateModule() {
		// Arrange
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

		AppModule appModule1 = new AppModule();
		appModule1.setActive(true);
		appModule1.setAppModuleOrganizations(new HashSet<>());
		appModule1.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule1.setDescription("The characteristics of someone or something");
		appModule1.setModuleColor("Module Color");
		appModule1.setModuleImageUrl("https://example.org/example");
		appModule1.setModuleName("Module Name");
		when(this.moduleRepository.save((AppModule) any())).thenReturn(appModule1);
		when(this.moduleRepository.findByModuleNameIgnoreCase((String) any()))
			.thenReturn(appModule);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(true);

		// Act
		AppModule actualDeactivateModuleResult =
			this.moduleServiceImpl.deactivateModule("Module Name", true);

		// Assert
		assertSame(appModule, actualDeactivateModuleResult);
		assertTrue(actualDeactivateModuleResult.isActive());
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
		verify(this.moduleRepository).findByModuleNameIgnoreCase((String) any());
		verify(this.moduleRepository).save((AppModule) any());
		assertTrue(this.moduleServiceImpl.findActiveModules().isEmpty());
	}

	@Test
	void testDeactivateModule2() {
		// Arrange
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

		AppModule appModule1 = new AppModule();
		appModule1.setActive(true);
		appModule1.setAppModuleOrganizations(new HashSet<>());
		appModule1.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		appModule1.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule1.setDescription("The characteristics of someone or something");
		appModule1.setModuleColor("Module Color");
		appModule1.setModuleImageUrl("https://example.org/example");
		appModule1.setModuleName("Module Name");
		when(this.moduleRepository.save((AppModule) any())).thenReturn(appModule1);
		when(this.moduleRepository.findByModuleNameIgnoreCase((String) any()))
			.thenReturn(appModule);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.moduleServiceImpl.deactivateModule("Module Name", true)
		);
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
	}

	@Test
	void testDeleteModule() {
		// Arrange
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
		doNothing().when(this.moduleRepository).deleteById((Long) any());
		when(this.moduleRepository.findByModuleNameIgnoreCase((String) any()))
			.thenReturn(appModule);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(true);

		// Act
		this.moduleServiceImpl.deleteModule("Module Name");

		// Assert
		verify(this.moduleRepository).deleteById((Long) any());
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
		verify(this.moduleRepository).findByModuleNameIgnoreCase((String) any());
		assertTrue(this.moduleServiceImpl.findActiveModules().isEmpty());
	}

	@Test
	void testDeleteModule2() {
		// Arrange
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
		doNothing().when(this.moduleRepository).deleteById((Long) any());
		when(this.moduleRepository.findByModuleNameIgnoreCase((String) any()))
			.thenReturn(appModule);
		when(this.moduleRepository.existsByModuleNameIgnoreCase((String) any()))
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.moduleServiceImpl.deleteModule("Module Name")
		);
		verify(this.moduleRepository).existsByModuleNameIgnoreCase((String) any());
	}
}
