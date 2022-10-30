package com.ravel.backend.modules.customWaterschap.kernproces;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

@ContextConfiguration(classes = { KernprocesServiceImpl.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class KernprocesServiceImplTest {

	@MockBean
	private KernprocesRepository kernprocesRepository;

	@Autowired
	private KernprocesServiceImpl kernprocesServiceImpl;

	@Test
	void testCreateKernproces() {
		// Arrange
		when(this.kernprocesRepository.existsByKernProcesName((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() ->
				this.kernprocesServiceImpl.createKernproces(
						"Kern Proces Name",
						"https://example.org/example"
					)
		);
		verify(this.kernprocesRepository).existsByKernProcesName((String) any());
	}

	@Test
	void testCreateKernproces2() {
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
		when(this.kernprocesRepository.save((Kernproces) any())).thenReturn(kernproces);
		when(this.kernprocesRepository.existsByKernProcesName((String) any()))
			.thenReturn(false);

		// Act
		Kernproces actualCreateKernprocesResult =
			this.kernprocesServiceImpl.createKernproces(
					"Kern Proces Name",
					"https://example.org/example"
				);

		// Assert
		assertEquals(
			"Kern Proces Name",
			actualCreateKernprocesResult.getKernProcesName()
		);
		assertEquals(
			"https://example.org/example",
			actualCreateKernprocesResult.getKernProcesImageUrl()
		);
		verify(this.kernprocesRepository).existsByKernProcesName((String) any());
		verify(this.kernprocesRepository).save((Kernproces) any());
		assertTrue(this.kernprocesServiceImpl.findAll().isEmpty());
	}

	@Test
	void testFindKernproces() {
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
		when(this.kernprocesRepository.findByKernProcesName((String) any()))
			.thenReturn(kernproces);
		when(this.kernprocesRepository.existsByKernProcesName((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertSame(
			kernproces,
			this.kernprocesServiceImpl.findKernproces("Kern Proces Name")
		);
		verify(this.kernprocesRepository).existsByKernProcesName((String) any());
		verify(this.kernprocesRepository).findByKernProcesName((String) any());
		assertTrue(this.kernprocesServiceImpl.findAll().isEmpty());
	}

	@Test
	void testFindKernproces2() {
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
		when(this.kernprocesRepository.findByKernProcesName((String) any()))
			.thenReturn(kernproces);
		when(this.kernprocesRepository.existsByKernProcesName((String) any()))
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.kernprocesServiceImpl.findKernproces("Kern Proces Name")
		);
		verify(this.kernprocesRepository).existsByKernProcesName((String) any());
	}

	@Test
	void testFindAll() {
		// Arrange
		ArrayList<Kernproces> kernprocesList = new ArrayList<>();
		when(this.kernprocesRepository.findAll()).thenReturn(kernprocesList);

		// Act
		List<Kernproces> actualFindAllResult = this.kernprocesServiceImpl.findAll();

		// Assert
		assertSame(kernprocesList, actualFindAllResult);
		assertTrue(actualFindAllResult.isEmpty());
		verify(this.kernprocesRepository).findAll();
	}

	@Test
	void testUpdateKernproces() {
		// Arrange, Act and Assert
		assertNull(
			this.kernprocesServiceImpl.updateKernproces(
					"Kern Proces Name",
					"https://example.org/example"
				)
		);
	}
}
