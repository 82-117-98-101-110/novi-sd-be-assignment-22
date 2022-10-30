package com.ravel.backend.modules.customWaterschap.waterschap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ravel.backend.modules.customWaterschap.kernproces.KernprocesRepository;
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

@ContextConfiguration(classes = { WaterschapServiceImpl.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class WaterschapServiceImplTest {

	@MockBean
	private KernprocesRepository kernprocesRepository;

	@MockBean
	private WaterschapRepository waterschapRepository;

	@Autowired
	private WaterschapServiceImpl waterschapServiceImpl;

	@Test
	void testCreateWaterschap() {
		// Arrange
		when(this.waterschapRepository.existsByWaterschapName((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() ->
				this.waterschapServiceImpl.createWaterschap(
						"Waterschap Name",
						"https://example.org/example"
					)
		);
		verify(this.waterschapRepository).existsByWaterschapName((String) any());
	}

	@Test
	void testCreateWaterschap2() {
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
		when(this.waterschapRepository.save((Waterschap) any())).thenReturn(waterschap);
		when(this.waterschapRepository.existsByWaterschapName((String) any()))
			.thenReturn(false);

		// Act and Assert
		assertSame(
			waterschap,
			this.waterschapServiceImpl.createWaterschap(
					"Waterschap Name",
					"https://example.org/example"
				)
		);
		verify(this.waterschapRepository).existsByWaterschapName((String) any());
		verify(this.waterschapRepository).save((Waterschap) any());
		assertTrue(this.waterschapServiceImpl.findAll().isEmpty());
	}

	@Test
	void testFindWaterschap() {
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
		when(this.waterschapRepository.findWaterschapByWaterschapName((String) any()))
			.thenReturn(waterschap);
		when(this.waterschapRepository.existsByWaterschapName((String) any()))
			.thenReturn(true);

		// Act and Assert
		assertSame(
			waterschap,
			this.waterschapServiceImpl.findWaterschap("Waterschap Name")
		);
		verify(this.waterschapRepository).existsByWaterschapName((String) any());
		verify(this.waterschapRepository).findWaterschapByWaterschapName((String) any());
		assertTrue(this.waterschapServiceImpl.findAll().isEmpty());
	}

	@Test
	void testFindWaterschap2() {
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
		when(this.waterschapRepository.findWaterschapByWaterschapName((String) any()))
			.thenReturn(waterschap);
		when(this.waterschapRepository.existsByWaterschapName((String) any()))
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.waterschapServiceImpl.findWaterschap("Waterschap Name")
		);
		verify(this.waterschapRepository).existsByWaterschapName((String) any());
	}

	@Test
	void testFindAll() {
		// Arrange
		ArrayList<Waterschap> waterschapList = new ArrayList<>();
		when(this.waterschapRepository.findAll()).thenReturn(waterschapList);

		// Act
		List<Waterschap> actualFindAllResult = this.waterschapServiceImpl.findAll();

		// Assert
		assertSame(waterschapList, actualFindAllResult);
		assertTrue(actualFindAllResult.isEmpty());
		verify(this.waterschapRepository).findAll();
	}

	@Test
	void testUpdateWaterschap() {
		// Arrange, Act and Assert
		assertNull(
			this.waterschapServiceImpl.updateWaterschap(
					"Waterschap Name",
					"https://example.org/example"
				)
		);
	}
}
