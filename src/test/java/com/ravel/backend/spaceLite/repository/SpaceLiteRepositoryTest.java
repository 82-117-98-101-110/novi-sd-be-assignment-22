package com.ravel.backend.spaceLite.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ravel.backend.spaceLite.model.SpaceLite;
import java.util.HashSet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "h2" })
@DataJpaTest
class SpaceLiteRepositoryTest {

	@Autowired
	private SpaceLiteRepository spaceLiteRepository;

	@Test
	void testExistsByNameIgnoreCase2() {
		// Arrange
		SpaceLite spaceLite = new SpaceLite();
		spaceLite.setEmbeddedCode("Embedded Code");
		spaceLite.setInviteLink("Invite Link");
		spaceLite.setCreated_at(null);
		spaceLite.setName("l.UlUlUlUlUl.U");
		spaceLite.setActive(true);
		spaceLite.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite.setUpdatedAt(null);
		spaceLite.setSrc("Src");
		spaceLite.setRoomCode("Room Code");

		SpaceLite spaceLite1 = new SpaceLite();
		spaceLite1.setEmbeddedCode("Embedded Code");
		spaceLite1.setInviteLink("Invite Link");
		spaceLite1.setCreated_at(null);
		spaceLite1.setName("Name");
		spaceLite1.setActive(true);
		spaceLite1.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite1.setUpdatedAt(null);
		spaceLite1.setSrc("Src");
		spaceLite1.setRoomCode("Room Code");
		this.spaceLiteRepository.save(spaceLite);
		this.spaceLiteRepository.save(spaceLite1);

		// Act and Assert
		assertFalse(this.spaceLiteRepository.existsByNameIgnoreCase("foo"));
	}
}
