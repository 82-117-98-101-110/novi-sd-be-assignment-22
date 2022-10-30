package com.ravel.backend.modules.customWaterschap.userEntry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ravel.backend.modules.customWaterschap.kernproces.Kernproces;
import com.ravel.backend.modules.customWaterschap.kernproces.KernprocesRepository;
import com.ravel.backend.modules.customWaterschap.waterschap.Waterschap;
import com.ravel.backend.modules.customWaterschap.waterschap.WaterschapRepository;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.service.UserService;
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

@ContextConfiguration(classes = { ModuleWaterschappenServiceImpl.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class ModuleWaterschappenServiceImplTest {

	@MockBean
	private KernprocesRepository kernprocesRepository;

	@MockBean
	private ModuleWaterschappenRepository moduleWaterschappenRepository;

	@Autowired
	private ModuleWaterschappenServiceImpl moduleWaterschappenServiceImpl;

	@MockBean
	private UserService userService;

	@MockBean
	private WaterschapRepository waterschapRepository;

	@Test
	void testCreateNewEntry() {
		// Arrange
		Waterschap waterschap = new Waterschap();
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setWaterschapImageUrl("https://example.org/example");
		waterschap.setWaterschapName("Waterschap Name");
		when(this.waterschapRepository.findByWaterschapName((String) any()))
			.thenReturn(waterschap);
		when(this.userService.getUserUuidFromActiveUser((String) any()))
			.thenReturn(UUID.randomUUID());

		Kernproces kernproces = new Kernproces();
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces.setKernProcesImageUrl("https://example.org/example");
		kernproces.setKernProcesName("Kern Proces Name");
		kernproces.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap1 = new Waterschap();
		LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setCreatedAt(
			Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap1.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setUpdatedAt(
			Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap1.setWaterschapImageUrl("https://example.org/example");
		waterschap1.setWaterschapName("Waterschap Name");

		ModuleWaterschappen moduleWaterschappen = new ModuleWaterschappen();
		moduleWaterschappen.setActive(true);
		LocalDateTime atStartOfDayResult6 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setCreatedAt(
			Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setKernproces(kernproces);
		LocalDateTime atStartOfDayResult7 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setUpdatedAt(
			Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setUserUuid(UUID.randomUUID());
		moduleWaterschappen.setUsername("janedoe");
		moduleWaterschappen.setWaterschap(waterschap1);

		Kernproces kernproces1 = new Kernproces();
		LocalDateTime atStartOfDayResult8 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces1.setCreatedAt(
			Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces1.setKernProcesImageUrl("https://example.org/example");
		kernproces1.setKernProcesName("Kern Proces Name");
		kernproces1.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult9 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces1.setUpdatedAt(
			Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap2 = new Waterschap();
		LocalDateTime atStartOfDayResult10 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap2.setCreatedAt(
			Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap2.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult11 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap2.setUpdatedAt(
			Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap2.setWaterschapImageUrl("https://example.org/example");
		waterschap2.setWaterschapName("Waterschap Name");

		ModuleWaterschappen moduleWaterschappen1 = new ModuleWaterschappen();
		moduleWaterschappen1.setActive(true);
		LocalDateTime atStartOfDayResult12 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen1.setCreatedAt(
			Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen1.setKernproces(kernproces1);
		LocalDateTime atStartOfDayResult13 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen1.setUpdatedAt(
			Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen1.setUserUuid(UUID.randomUUID());
		moduleWaterschappen1.setUsername("janedoe");
		moduleWaterschappen1.setWaterschap(waterschap2);
		when(this.moduleWaterschappenRepository.save((ModuleWaterschappen) any()))
			.thenReturn(moduleWaterschappen1);
		when(this.moduleWaterschappenRepository.findByUserUuid((UUID) any()))
			.thenReturn(moduleWaterschappen);
		when(this.moduleWaterschappenRepository.existsByUserUuid((UUID) any()))
			.thenReturn(true);

		Kernproces kernproces2 = new Kernproces();
		LocalDateTime atStartOfDayResult14 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces2.setCreatedAt(
			Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces2.setKernProcesImageUrl("https://example.org/example");
		kernproces2.setKernProcesName("Kern Proces Name");
		kernproces2.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult15 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces2.setUpdatedAt(
			Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.kernprocesRepository.findByKernProcesName((String) any()))
			.thenReturn(kernproces2);

		// Act
		ModuleWaterschappen actualCreateNewEntryResult =
			this.moduleWaterschappenServiceImpl.createNewEntry(
					"jane.doe@example.org",
					"Waterschappen Name",
					"Kern Proces Name"
				);

		// Assert
		assertSame(moduleWaterschappen, actualCreateNewEntryResult);
		assertSame(waterschap, actualCreateNewEntryResult.getWaterschap());
		assertSame(kernproces2, actualCreateNewEntryResult.getKernproces());
		verify(this.waterschapRepository).findByWaterschapName((String) any());
		verify(this.userService).getUserUuidFromActiveUser((String) any());
		verify(this.moduleWaterschappenRepository).existsByUserUuid((UUID) any());
		verify(this.moduleWaterschappenRepository).findByUserUuid((UUID) any());
		verify(this.moduleWaterschappenRepository).save((ModuleWaterschappen) any());
		verify(this.kernprocesRepository).findByKernProcesName((String) any());
		assertTrue(this.moduleWaterschappenServiceImpl.getAllEntries().isEmpty());
	}

	@Test
	void testCreateNewEntryUuid() {
		// Arrange
		Waterschap waterschap = new Waterschap();
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setWaterschapImageUrl("https://example.org/example");
		waterschap.setWaterschapName("Waterschap Name");
		when(this.waterschapRepository.findByWaterschapName((String) any()))
			.thenReturn(waterschap);

		Kernproces kernproces = new Kernproces();
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces.setKernProcesImageUrl("https://example.org/example");
		kernproces.setKernProcesName("Kern Proces Name");
		kernproces.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap1 = new Waterschap();
		LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setCreatedAt(
			Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap1.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setUpdatedAt(
			Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap1.setWaterschapImageUrl("https://example.org/example");
		waterschap1.setWaterschapName("Waterschap Name");

		ModuleWaterschappen moduleWaterschappen = new ModuleWaterschappen();
		moduleWaterschappen.setActive(true);
		LocalDateTime atStartOfDayResult6 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setCreatedAt(
			Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setKernproces(kernproces);
		LocalDateTime atStartOfDayResult7 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setUpdatedAt(
			Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setUserUuid(UUID.randomUUID());
		moduleWaterschappen.setUsername("janedoe");
		moduleWaterschappen.setWaterschap(waterschap1);

		Kernproces kernproces1 = new Kernproces();
		LocalDateTime atStartOfDayResult8 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces1.setCreatedAt(
			Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces1.setKernProcesImageUrl("https://example.org/example");
		kernproces1.setKernProcesName("Kern Proces Name");
		kernproces1.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult9 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces1.setUpdatedAt(
			Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap2 = new Waterschap();
		LocalDateTime atStartOfDayResult10 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap2.setCreatedAt(
			Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap2.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult11 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap2.setUpdatedAt(
			Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap2.setWaterschapImageUrl("https://example.org/example");
		waterschap2.setWaterschapName("Waterschap Name");

		ModuleWaterschappen moduleWaterschappen1 = new ModuleWaterschappen();
		moduleWaterschappen1.setActive(true);
		LocalDateTime atStartOfDayResult12 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen1.setCreatedAt(
			Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen1.setKernproces(kernproces1);
		LocalDateTime atStartOfDayResult13 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen1.setUpdatedAt(
			Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen1.setUserUuid(UUID.randomUUID());
		moduleWaterschappen1.setUsername("janedoe");
		moduleWaterschappen1.setWaterschap(waterschap2);
		when(this.moduleWaterschappenRepository.save((ModuleWaterschappen) any()))
			.thenReturn(moduleWaterschappen1);
		when(this.moduleWaterschappenRepository.findByUserUuid((UUID) any()))
			.thenReturn(moduleWaterschappen);
		when(this.moduleWaterschappenRepository.existsByUserUuid((UUID) any()))
			.thenReturn(true);

		Kernproces kernproces2 = new Kernproces();
		LocalDateTime atStartOfDayResult14 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces2.setCreatedAt(
			Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces2.setKernProcesImageUrl("https://example.org/example");
		kernproces2.setKernProcesName("Kern Proces Name");
		kernproces2.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult15 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces2.setUpdatedAt(
			Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.kernprocesRepository.findByKernProcesName((String) any()))
			.thenReturn(kernproces2);

		// Act
		ModuleWaterschappen actualCreateNewEntryUuidResult =
			this.moduleWaterschappenServiceImpl.createNewEntryUuid(
					UUID.randomUUID(),
					"Waterschappen Name",
					"Kern Proces Name"
				);

		// Assert
		assertSame(moduleWaterschappen, actualCreateNewEntryUuidResult);
		assertSame(waterschap, actualCreateNewEntryUuidResult.getWaterschap());
		assertSame(kernproces2, actualCreateNewEntryUuidResult.getKernproces());
		verify(this.waterschapRepository).findByWaterschapName((String) any());
		verify(this.moduleWaterschappenRepository).existsByUserUuid((UUID) any());
		verify(this.moduleWaterschappenRepository).findByUserUuid((UUID) any());
		verify(this.moduleWaterschappenRepository).save((ModuleWaterschappen) any());
		verify(this.kernprocesRepository).findByKernProcesName((String) any());
		assertTrue(this.moduleWaterschappenServiceImpl.getAllEntries().isEmpty());
	}

	@Test
	void testGetEntry() {
		// Arrange
		when(this.userService.getUserUuidFromActiveUser((String) any()))
			.thenReturn(UUID.randomUUID());

		Kernproces kernproces = new Kernproces();
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces.setKernProcesImageUrl("https://example.org/example");
		kernproces.setKernProcesName("Kern Proces Name");
		kernproces.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap = new Waterschap();
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setWaterschapImageUrl("https://example.org/example");
		waterschap.setWaterschapName("Waterschap Name");

		ModuleWaterschappen moduleWaterschappen = new ModuleWaterschappen();
		moduleWaterschappen.setActive(true);
		LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setCreatedAt(
			Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setKernproces(kernproces);
		LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setUpdatedAt(
			Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setUserUuid(UUID.randomUUID());
		moduleWaterschappen.setUsername("janedoe");
		moduleWaterschappen.setWaterschap(waterschap);
		when(this.moduleWaterschappenRepository.findByUserUuid((UUID) any()))
			.thenReturn(moduleWaterschappen);
		when(this.moduleWaterschappenRepository.existsByUserUuid((UUID) any()))
			.thenReturn(true);

		// Act and Assert
		assertSame(
			moduleWaterschappen,
			this.moduleWaterschappenServiceImpl.getEntry("jane.doe@example.org")
		);
		verify(this.userService).getUserUuidFromActiveUser((String) any());
		verify(this.moduleWaterschappenRepository).existsByUserUuid((UUID) any());
		verify(this.moduleWaterschappenRepository).findByUserUuid((UUID) any());
		assertTrue(this.moduleWaterschappenServiceImpl.getAllEntries().isEmpty());
	}

	@Test
	void testGetEntryUuid() {
		// Arrange
		Kernproces kernproces = new Kernproces();
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces.setKernProcesImageUrl("https://example.org/example");
		kernproces.setKernProcesName("Kern Proces Name");
		kernproces.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap = new Waterschap();
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setWaterschapImageUrl("https://example.org/example");
		waterschap.setWaterschapName("Waterschap Name");

		ModuleWaterschappen moduleWaterschappen = new ModuleWaterschappen();
		moduleWaterschappen.setActive(true);
		LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setCreatedAt(
			Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setKernproces(kernproces);
		LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setUpdatedAt(
			Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setUserUuid(UUID.randomUUID());
		moduleWaterschappen.setUsername("janedoe");
		moduleWaterschappen.setWaterschap(waterschap);
		when(this.moduleWaterschappenRepository.findByUserUuid((UUID) any()))
			.thenReturn(moduleWaterschappen);
		when(this.moduleWaterschappenRepository.existsByUserUuid((UUID) any()))
			.thenReturn(true);

		// Act and Assert
		assertSame(
			moduleWaterschappen,
			this.moduleWaterschappenServiceImpl.getEntryUuid(UUID.randomUUID())
		);
		verify(this.moduleWaterschappenRepository).existsByUserUuid((UUID) any());
		verify(this.moduleWaterschappenRepository).findByUserUuid((UUID) any());
		assertTrue(this.moduleWaterschappenServiceImpl.getAllEntries().isEmpty());
	}

	@Test
	void testGetEntryUuid2() {
		// Arrange
		Kernproces kernproces = new Kernproces();
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces.setKernProcesImageUrl("https://example.org/example");
		kernproces.setKernProcesName("Kern Proces Name");
		kernproces.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap = new Waterschap();
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setWaterschapImageUrl("https://example.org/example");
		waterschap.setWaterschapName("Waterschap Name");

		ModuleWaterschappen moduleWaterschappen = new ModuleWaterschappen();
		moduleWaterschappen.setActive(true);
		LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setCreatedAt(
			Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setKernproces(kernproces);
		LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setUpdatedAt(
			Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setUserUuid(UUID.randomUUID());
		moduleWaterschappen.setUsername("janedoe");
		moduleWaterschappen.setWaterschap(waterschap);
		when(this.moduleWaterschappenRepository.findByUserUuid((UUID) any()))
			.thenReturn(moduleWaterschappen);
		when(this.moduleWaterschappenRepository.existsByUserUuid((UUID) any()))
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.moduleWaterschappenServiceImpl.getEntryUuid(UUID.randomUUID())
		);
		verify(this.moduleWaterschappenRepository).existsByUserUuid((UUID) any());
	}

	@Test
	void testFindEntry() {
		// Arrange
		Kernproces kernproces = new Kernproces();
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces.setKernProcesImageUrl("https://example.org/example");
		kernproces.setKernProcesName("Kern Proces Name");
		kernproces.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap = new Waterschap();
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		waterschap.setWaterschapImageUrl("https://example.org/example");
		waterschap.setWaterschapName("Waterschap Name");

		ModuleWaterschappen moduleWaterschappen = new ModuleWaterschappen();
		moduleWaterschappen.setActive(true);
		LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setCreatedAt(
			Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setKernproces(kernproces);
		LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
		moduleWaterschappen.setUpdatedAt(
			Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant())
		);
		moduleWaterschappen.setUserUuid(UUID.randomUUID());
		moduleWaterschappen.setUsername("janedoe");
		moduleWaterschappen.setWaterschap(waterschap);
		when(
			this.moduleWaterschappenRepository.findModuleWaterschappenByUsername(
					(String) any()
				)
		)
			.thenReturn(moduleWaterschappen);

		// Act and Assert
		assertSame(
			moduleWaterschappen,
			this.moduleWaterschappenServiceImpl.findEntry("janedoe")
		);
		verify(this.moduleWaterschappenRepository)
			.findModuleWaterschappenByUsername((String) any());
		assertTrue(this.moduleWaterschappenServiceImpl.getAllEntries().isEmpty());
	}

	@Test
	void testGetEntries() {
		// Arrange
		ArrayList<ModuleWaterschappen> moduleWaterschappenList = new ArrayList<>();
		when(this.moduleWaterschappenRepository.getEntries())
			.thenReturn(moduleWaterschappenList);

		// Act
		List<ModuleWaterschappen> actualEntries =
			this.moduleWaterschappenServiceImpl.getEntries();

		// Assert
		assertSame(moduleWaterschappenList, actualEntries);
		assertTrue(actualEntries.isEmpty());
		verify(this.moduleWaterschappenRepository).getEntries();
		assertTrue(this.moduleWaterschappenServiceImpl.getAllEntries().isEmpty());
	}

	@Test
	void testGetAllEntries() {
		// Arrange
		ArrayList<ModuleWaterschappen> moduleWaterschappenList = new ArrayList<>();
		when(this.moduleWaterschappenRepository.findAll())
			.thenReturn(moduleWaterschappenList);

		// Act
		List<ModuleWaterschappen> actualAllEntries =
			this.moduleWaterschappenServiceImpl.getAllEntries();

		// Assert
		assertSame(moduleWaterschappenList, actualAllEntries);
		assertTrue(actualAllEntries.isEmpty());
		verify(this.moduleWaterschappenRepository).findAll();
		assertTrue(this.moduleWaterschappenServiceImpl.getEntries().isEmpty());
	}
}
