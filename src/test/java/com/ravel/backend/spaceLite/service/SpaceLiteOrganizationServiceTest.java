package com.ravel.backend.spaceLite.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spaceLite.model.SpaceLite;
import com.ravel.backend.spaceLite.model.SpaceLiteOrganization;
import com.ravel.backend.spaceLite.repository.SpaceLiteOrganizationRepository;
import com.ravel.backend.spaceLite.repository.SpaceLiteRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { SpaceLiteOrganizationService.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class SpaceLiteOrganizationServiceTest {

	@MockBean
	private IAuthenticationFacade iAuthenticationFacade;

	@MockBean
	private OrganizationService organizationService;

	@MockBean
	private OrganizationUserRoleService organizationUserRoleService;

	@MockBean
	private SpaceLiteOrganizationRepository spaceLiteOrganizationRepository;

	@Autowired
	private SpaceLiteOrganizationService spaceLiteOrganizationService;

	@MockBean
	private SpaceLiteRepository spaceLiteRepository;

	@Test
	void testGetSpacesLiteForOrganizations() {
		// Arrange
		when(
			this.spaceLiteOrganizationRepository.findByOrganizationIdAndIsActive(
					(List<UUID>) any()
				)
		)
			.thenReturn(new ArrayList<>());

		// Act and Assert
		assertTrue(
			this.spaceLiteOrganizationService.getSpacesLiteForOrganizations(
					new ArrayList<>()
				)
				.isEmpty()
		);
		verify(this.spaceLiteOrganizationRepository)
			.findByOrganizationIdAndIsActive((List<UUID>) any());
	}

	@Test
	void testAddSpaceLiteToOrganization() {
		// Arrange
		SpaceLite spaceLite = new SpaceLite();
		spaceLite.setActive(true);
		spaceLite.setCreated_at(null);
		spaceLite.setEmbeddedCode("Embedded Code");
		spaceLite.setId(123L);
		spaceLite.setInviteLink("Invite Link");
		spaceLite.setName("Name");
		spaceLite.setRoomCode("Room Code");
		spaceLite.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite.setSrc("Src");
		spaceLite.setUpdatedAt(null);
		Optional<SpaceLite> ofResult = Optional.of(spaceLite);
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);

		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setCreated_at(null);
		spaceLiteOrganization.setId(123L);
		spaceLiteOrganization.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization.setSpaceLites(new HashSet<>());
		spaceLiteOrganization.setUpdatedAt(null);
		Optional<SpaceLiteOrganization> ofResult1 = Optional.of(spaceLiteOrganization);

		SpaceLiteOrganization spaceLiteOrganization1 = new SpaceLiteOrganization();
		spaceLiteOrganization1.setActive(true);
		spaceLiteOrganization1.setCreated_at(null);
		spaceLiteOrganization1.setId(123L);
		spaceLiteOrganization1.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization1.setSpaceLites(new HashSet<>());
		spaceLiteOrganization1.setUpdatedAt(null);
		when(this.spaceLiteOrganizationRepository.save((SpaceLiteOrganization) any()))
			.thenReturn(spaceLiteOrganization1);
		when(
			this.spaceLiteOrganizationRepository.findByOrganizationIdAndIsActiveSingle(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult1);
		when(
			this.spaceLiteOrganizationRepository.existsByOrganizationIdAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(true);
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		// Act and Assert
		assertNull(
			this.spaceLiteOrganizationService.addSpaceLiteToOrganization(
					"Organization Name",
					"Space Lite Name"
				)
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository)
			.existsByOrganizationIdAndIsActive((UUID) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository)
			.findByOrganizationIdAndIsActiveSingle((UUID) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository).save((SpaceLiteOrganization) any());
		verify(this.organizationService).getOrganizationId((String) any());
	}

	@Test
	void testAddSpaceLiteToOrganization2() {
		// Arrange
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());

		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setCreated_at(null);
		spaceLiteOrganization.setId(123L);
		spaceLiteOrganization.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization.setSpaceLites(new HashSet<>());
		spaceLiteOrganization.setUpdatedAt(null);
		Optional<SpaceLiteOrganization> ofResult = Optional.of(spaceLiteOrganization);

		SpaceLiteOrganization spaceLiteOrganization1 = new SpaceLiteOrganization();
		spaceLiteOrganization1.setActive(true);
		spaceLiteOrganization1.setCreated_at(null);
		spaceLiteOrganization1.setId(123L);
		spaceLiteOrganization1.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization1.setSpaceLites(new HashSet<>());
		spaceLiteOrganization1.setUpdatedAt(null);
		when(this.spaceLiteOrganizationRepository.save((SpaceLiteOrganization) any()))
			.thenReturn(spaceLiteOrganization1);
		when(
			this.spaceLiteOrganizationRepository.findByOrganizationIdAndIsActiveSingle(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);
		when(
			this.spaceLiteOrganizationRepository.existsByOrganizationIdAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(true);
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.spaceLiteOrganizationService.addSpaceLiteToOrganization(
						"Organization Name",
						"Space Lite Name"
					)
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository)
			.existsByOrganizationIdAndIsActive((UUID) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository)
			.findByOrganizationIdAndIsActiveSingle((UUID) any(), anyBoolean());
		verify(this.organizationService).getOrganizationId((String) any());
	}

	@Test
	void testAddSpaceLiteToOrganization3() {
		// Arrange
		SpaceLite spaceLite = new SpaceLite();
		spaceLite.setActive(true);
		spaceLite.setCreated_at(null);
		spaceLite.setEmbeddedCode("Embedded Code");
		spaceLite.setId(123L);
		spaceLite.setInviteLink("Invite Link");
		spaceLite.setName("Name");
		spaceLite.setRoomCode("Room Code");
		spaceLite.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite.setSrc("Src");
		spaceLite.setUpdatedAt(null);
		Optional<SpaceLite> ofResult = Optional.of(spaceLite);
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);

		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setCreated_at(null);
		spaceLiteOrganization.setId(123L);
		spaceLiteOrganization.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization.setSpaceLites(new HashSet<>());
		spaceLiteOrganization.setUpdatedAt(null);
		when(this.spaceLiteOrganizationRepository.save((SpaceLiteOrganization) any()))
			.thenReturn(spaceLiteOrganization);
		when(
			this.spaceLiteOrganizationRepository.findByOrganizationIdAndIsActiveSingle(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());
		when(
			this.spaceLiteOrganizationRepository.existsByOrganizationIdAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(true);
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.spaceLiteOrganizationService.addSpaceLiteToOrganization(
						"Organization Name",
						"Space Lite Name"
					)
		);
		verify(this.spaceLiteOrganizationRepository)
			.existsByOrganizationIdAndIsActive((UUID) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository)
			.findByOrganizationIdAndIsActiveSingle((UUID) any(), anyBoolean());
		verify(this.organizationService).getOrganizationId((String) any());
	}

	@Test
	void testAddSpaceLiteToOrganization4() {
		// Arrange
		SpaceLite spaceLite = new SpaceLite();
		spaceLite.setActive(true);
		spaceLite.setCreated_at(null);
		spaceLite.setEmbeddedCode("Embedded Code");
		spaceLite.setId(123L);
		spaceLite.setInviteLink("Invite Link");
		spaceLite.setName("Name");
		spaceLite.setRoomCode("Room Code");
		spaceLite.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite.setSrc("Src");
		spaceLite.setUpdatedAt(null);
		Optional<SpaceLite> ofResult = Optional.of(spaceLite);
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);

		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setCreated_at(null);
		spaceLiteOrganization.setId(123L);
		spaceLiteOrganization.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization.setSpaceLites(new HashSet<>());
		spaceLiteOrganization.setUpdatedAt(null);
		Optional<SpaceLiteOrganization> ofResult1 = Optional.of(spaceLiteOrganization);

		SpaceLiteOrganization spaceLiteOrganization1 = new SpaceLiteOrganization();
		spaceLiteOrganization1.setActive(true);
		spaceLiteOrganization1.setCreated_at(null);
		spaceLiteOrganization1.setId(123L);
		spaceLiteOrganization1.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization1.setSpaceLites(new HashSet<>());
		spaceLiteOrganization1.setUpdatedAt(null);
		when(this.spaceLiteOrganizationRepository.save((SpaceLiteOrganization) any()))
			.thenReturn(spaceLiteOrganization1);
		when(
			this.spaceLiteOrganizationRepository.findByOrganizationIdAndIsActiveSingle(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult1);
		when(
			this.spaceLiteOrganizationRepository.existsByOrganizationIdAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(false);
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		// Act and Assert
		assertNull(
			this.spaceLiteOrganizationService.addSpaceLiteToOrganization(
					"Organization Name",
					"Space Lite Name"
				)
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository)
			.existsByOrganizationIdAndIsActive((UUID) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository).save((SpaceLiteOrganization) any());
		verify(this.organizationService).getOrganizationId((String) any());
	}

	@Test
	void testAddSpaceLiteToOrganization5() {
		// Arrange
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());

		SpaceLiteOrganization spaceLiteOrganization = new SpaceLiteOrganization();
		spaceLiteOrganization.setActive(true);
		spaceLiteOrganization.setCreated_at(null);
		spaceLiteOrganization.setId(123L);
		spaceLiteOrganization.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization.setSpaceLites(new HashSet<>());
		spaceLiteOrganization.setUpdatedAt(null);
		Optional<SpaceLiteOrganization> ofResult = Optional.of(spaceLiteOrganization);

		SpaceLiteOrganization spaceLiteOrganization1 = new SpaceLiteOrganization();
		spaceLiteOrganization1.setActive(true);
		spaceLiteOrganization1.setCreated_at(null);
		spaceLiteOrganization1.setId(123L);
		spaceLiteOrganization1.setOrganizationId(UUID.randomUUID());
		spaceLiteOrganization1.setSpaceLites(new HashSet<>());
		spaceLiteOrganization1.setUpdatedAt(null);
		when(this.spaceLiteOrganizationRepository.save((SpaceLiteOrganization) any()))
			.thenReturn(spaceLiteOrganization1);
		when(
			this.spaceLiteOrganizationRepository.findByOrganizationIdAndIsActiveSingle(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);
		when(
			this.spaceLiteOrganizationRepository.existsByOrganizationIdAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(false);
		when(this.organizationService.getOrganizationId((String) any()))
			.thenReturn(UUID.randomUUID());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.spaceLiteOrganizationService.addSpaceLiteToOrganization(
						"Organization Name",
						"Space Lite Name"
					)
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.spaceLiteOrganizationRepository)
			.existsByOrganizationIdAndIsActive((UUID) any(), anyBoolean());
		verify(this.organizationService).getOrganizationId((String) any());
	}
}
