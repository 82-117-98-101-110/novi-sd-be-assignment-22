package com.ravel.backend.modules.customWaterschap.waterschap;

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
class WaterschapRepositoryTest {

	@Autowired
	private WaterschapRepository waterschapRepository;

	@Test
	void testFindWaterschapByWaterschapName() {
		// Arrange
		Waterschap waterschap = new Waterschap();
		waterschap.setWaterschapName("42");
		waterschap.setModuleWaterschappens(new HashSet<>());
		waterschap.setWaterschapImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap1 = new Waterschap();
		waterschap1.setWaterschapName("Waterschap Name");
		waterschap1.setModuleWaterschappens(new HashSet<>());
		waterschap1.setWaterschapImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		this.waterschapRepository.save(waterschap);
		this.waterschapRepository.save(waterschap1);

		// Act and Assert
		assertNull(this.waterschapRepository.findWaterschapByWaterschapName("foo"));
	}

	@Test
	void testFindByWaterschapName() {
		// Arrange
		Waterschap waterschap = new Waterschap();
		waterschap.setWaterschapName("42");
		waterschap.setModuleWaterschappens(new HashSet<>());
		waterschap.setWaterschapImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap1 = new Waterschap();
		waterschap1.setWaterschapName("Waterschap Name");
		waterschap1.setModuleWaterschappens(new HashSet<>());
		waterschap1.setWaterschapImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		this.waterschapRepository.save(waterschap);
		this.waterschapRepository.save(waterschap1);

		// Act and Assert
		assertNull(this.waterschapRepository.findByWaterschapName("foo"));
	}

	@Test
	void testExistsByWaterschapName() {
		// Arrange
		Waterschap waterschap = new Waterschap();
		waterschap.setWaterschapName("42");
		waterschap.setModuleWaterschappens(new HashSet<>());
		waterschap.setWaterschapImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setUpdatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		Waterschap waterschap1 = new Waterschap();
		waterschap1.setWaterschapName("Waterschap Name");
		waterschap1.setModuleWaterschappens(new HashSet<>());
		waterschap1.setWaterschapImageUrl("https://example.org/example");
		LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setUpdatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
		waterschap1.setCreatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		this.waterschapRepository.save(waterschap);
		this.waterschapRepository.save(waterschap1);

		// Act and Assert
		assertFalse(this.waterschapRepository.existsByWaterschapName("foo"));
	}
}
