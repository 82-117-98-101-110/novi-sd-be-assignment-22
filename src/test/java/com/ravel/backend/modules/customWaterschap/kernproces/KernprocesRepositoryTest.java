package com.ravel.backend.modules.customWaterschap.kernproces;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

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
class KernprocesRepositoryTest {

	@Autowired
	private KernprocesRepository kernprocesRepository;

	@Test
	void testFindByKernProcesName() {
		// Arrange
		Kernproces kernproces = new Kernproces();
		kernproces.setKernProcesName("42");
		kernproces.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces.setKernProcesImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Kernproces kernproces1 = new Kernproces();
		kernproces1.setKernProcesName("Kern Proces Name");
		kernproces1.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces1.setKernProcesImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		this.kernprocesRepository.save(kernproces);
		this.kernprocesRepository.save(kernproces1);

		// Act and Assert
		assertNull(this.kernprocesRepository.findByKernProcesName("foo"));
	}

	@Test
	void testExistsByKernProcesName() {
		// Arrange
		Kernproces kernproces = new Kernproces();
		kernproces.setKernProcesName("42");
		kernproces.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces.setKernProcesImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Kernproces kernproces1 = new Kernproces();
		kernproces1.setKernProcesName("Kern Proces Name");
		kernproces1.setModuleWaterschappens(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		kernproces1.setKernProcesImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		kernproces1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		this.kernprocesRepository.save(kernproces);
		this.kernprocesRepository.save(kernproces1);

		// Act and Assert
		assertFalse(this.kernprocesRepository.existsByKernProcesName("foo"));
	}
}
