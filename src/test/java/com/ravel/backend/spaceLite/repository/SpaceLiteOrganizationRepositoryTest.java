package com.ravel.backend.spaceLite.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ravel.backend.spaceLite.model.SpaceLiteOrganization;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "h2" })
@DataJpaTest
class SpaceLiteOrganizationRepositoryTest {

	@Autowired
	private SpaceLiteOrganizationRepository spaceLiteOrganizationRepository;

	@Test
	void testFindByOrganizationIdAndIsActive() {
		// Arrange
		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setSpaceLites(new HashSet<>());
		spaceLiteOrganization.setCreated_at(null);
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setUpdatedAt(null);
		spaceLiteOrganization.setOrganizationId(UUID.randomUUID());

		SpaceLiteOrganization spaceLiteOrganization1 = new SpaceLiteOrganization();
		spaceLiteOrganization1.setSpaceLites(new HashSet<>());
		spaceLiteOrganization1.setCreated_at(null);
		spaceLiteOrganization1.setActive(true);
		spaceLiteOrganization1.setUpdatedAt(null);
		spaceLiteOrganization1.setOrganizationId(UUID.randomUUID());
		this.spaceLiteOrganizationRepository.save(spaceLiteOrganization);
		this.spaceLiteOrganizationRepository.save(spaceLiteOrganization1);

		// Act and Assert
		assertTrue(
			this.spaceLiteOrganizationRepository.findByOrganizationIdAndIsActive(
					new ArrayList<>()
				)
				.isEmpty()
		);
	}

	@Test
	void testFindByOrganizationIdAndIsActiveSingle() {
		// Arrange
		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setSpaceLites(new HashSet<>());
		spaceLiteOrganization.setCreated_at(null);
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setUpdatedAt(null);
		spaceLiteOrganization.setOrganizationId(UUID.randomUUID());

		SpaceLiteOrganization spaceLiteOrganization1 = new SpaceLiteOrganization();
		spaceLiteOrganization1.setSpaceLites(new HashSet<>());
		spaceLiteOrganization1.setCreated_at(null);
		spaceLiteOrganization1.setActive(true);
		spaceLiteOrganization1.setUpdatedAt(null);
		spaceLiteOrganization1.setOrganizationId(UUID.randomUUID());
		this.spaceLiteOrganizationRepository.save(spaceLiteOrganization);
		this.spaceLiteOrganizationRepository.save(spaceLiteOrganization1);

		// Act and Assert
		assertFalse(
			this.spaceLiteOrganizationRepository.findByOrganizationIdAndIsActiveSingle(
					UUID.randomUUID(),
					true
				)
				.isPresent()
		);
	}

	@Test
	void testExistsByOrganizationIdAndIsActive() {
		// Arrange
		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setSpaceLites(new HashSet<>());
		spaceLiteOrganization.setCreated_at(null);
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setUpdatedAt(null);
		spaceLiteOrganization.setOrganizationId(UUID.randomUUID());

		SpaceLiteOrganization spaceLiteOrganization1 = new SpaceLiteOrganization();
		spaceLiteOrganization1.setSpaceLites(new HashSet<>());
		spaceLiteOrganization1.setCreated_at(null);
		spaceLiteOrganization1.setActive(true);
		spaceLiteOrganization1.setUpdatedAt(null);
		spaceLiteOrganization1.setOrganizationId(UUID.randomUUID());
		this.spaceLiteOrganizationRepository.save(spaceLiteOrganization);
		this.spaceLiteOrganizationRepository.save(spaceLiteOrganization1);

		// Act and Assert
		assertFalse(
			this.spaceLiteOrganizationRepository.existsByOrganizationIdAndIsActive(
					UUID.randomUUID(),
					true
				)
		);
	}
}
