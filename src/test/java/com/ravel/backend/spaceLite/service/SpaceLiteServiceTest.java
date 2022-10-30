package com.ravel.backend.spaceLite.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spaceLite.dto.SpaceLiteGetDto;
import com.ravel.backend.spaceLite.dto.SpaceLiteMapper;
import com.ravel.backend.spaceLite.dto.SpaceLitePostDto;
import com.ravel.backend.spaceLite.model.SpaceLite;
import com.ravel.backend.spaceLite.repository.SpaceLiteOrganizationRepository;
import com.ravel.backend.spaceLite.repository.SpaceLiteRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
	classes = { SpaceLiteService.class, SpaceLiteOrganizationService.class }
)
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class SpaceLiteServiceTest {

	@MockBean
	private IAuthenticationFacade iAuthenticationFacade;

	@MockBean
	private OrganizationService organizationService;

	@MockBean
	private OrganizationUserRoleService organizationUserRoleService;

	@MockBean
	private SpaceLiteMapper spaceLiteMapper;

	@MockBean
	private SpaceLiteOrganizationRepository spaceLiteOrganizationRepository;

	@MockBean
	private SpaceLiteRepository spaceLiteRepository;

	@Autowired
	private SpaceLiteService spaceLiteService;

	@Test
	void testCreateNewSpaceLite() {
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
		when(this.spaceLiteRepository.findByName((String) any())).thenReturn(spaceLite);

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() ->
				this.spaceLiteService.createNewSpaceLite(
						new SpaceLitePostDto(
							"Name",
							"Src",
							"Room Code",
							"Invite Link",
							"Embedded Code"
						)
					)
		);
		verify(this.spaceLiteRepository).findByName((String) any());
	}

	@Test
	void testCreateNewSpaceLite2() {
		// Arrange
		when(this.spaceLiteRepository.findByName((String) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.spaceLiteService.createNewSpaceLite(
						new SpaceLitePostDto(
							"Name",
							"Src",
							"Room Code",
							"Invite Link",
							"Embedded Code"
						)
					)
		);
		verify(this.spaceLiteRepository).findByName((String) any());
	}

	@Test
	void testGetSpacesForLite() {
		// Arrange
		when(this.spaceLiteRepository.findAll()).thenReturn(new ArrayList<>());
		ArrayList<SpaceLiteGetDto> spaceLiteGetDtoList = new ArrayList<>();
		when(this.spaceLiteMapper.spaceLiteListToSpaceLiteDtoSet((List<SpaceLite>) any()))
			.thenReturn(spaceLiteGetDtoList);

		// Act
		List<SpaceLiteGetDto> actualSpacesForLite =
			this.spaceLiteService.getSpacesForLite();

		// Assert
		assertSame(spaceLiteGetDtoList, actualSpacesForLite);
		assertTrue(actualSpacesForLite.isEmpty());
		verify(this.spaceLiteRepository).findAll();
		verify(this.spaceLiteMapper)
			.spaceLiteListToSpaceLiteDtoSet((List<SpaceLite>) any());
	}

	@Test
	void testGetSpacesForLite2() {
		// Arrange
		when(this.spaceLiteRepository.findAll()).thenReturn(new ArrayList<>());
		when(this.spaceLiteMapper.spaceLiteListToSpaceLiteDtoSet((List<SpaceLite>) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.spaceLiteService.getSpacesForLite()
		);
		verify(this.spaceLiteRepository).findAll();
		verify(this.spaceLiteMapper)
			.spaceLiteListToSpaceLiteDtoSet((List<SpaceLite>) any());
	}

	@Test
	void testGetSpacesForLite3() {
		// Arrange
		when(this.spaceLiteRepository.findAll())
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.spaceLiteService.getSpacesForLite()
		);
		verify(this.spaceLiteRepository).findAll();
	}

	@Test
	void testUpdateSpaceLite() {
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

		SpaceLite spaceLite1 = new SpaceLite();
		spaceLite1.setActive(true);
		spaceLite1.setCreated_at(null);
		spaceLite1.setEmbeddedCode("Embedded Code");
		spaceLite1.setId(123L);
		spaceLite1.setInviteLink("Invite Link");
		spaceLite1.setName("Name");
		spaceLite1.setRoomCode("Room Code");
		spaceLite1.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite1.setSrc("Src");
		spaceLite1.setUpdatedAt(null);
		Optional<SpaceLite> ofResult = Optional.of(spaceLite1);

		SpaceLite spaceLite2 = new SpaceLite();
		spaceLite2.setActive(true);
		spaceLite2.setCreated_at(null);
		spaceLite2.setEmbeddedCode("Embedded Code");
		spaceLite2.setId(123L);
		spaceLite2.setInviteLink("Invite Link");
		spaceLite2.setName("Name");
		spaceLite2.setRoomCode("Room Code");
		spaceLite2.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite2.setSrc("Src");
		spaceLite2.setUpdatedAt(null);
		when(this.spaceLiteRepository.save((SpaceLite) any())).thenReturn(spaceLite2);
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);
		when(this.spaceLiteRepository.findByName((String) any())).thenReturn(spaceLite);
		SpaceLiteGetDto spaceLiteGetDto = new SpaceLiteGetDto(
			"Name",
			"Src",
			"Embedded Code"
		);

		when(this.spaceLiteMapper.spaceLiteToSpaceLiteDto((SpaceLite) any()))
			.thenReturn(spaceLiteGetDto);
		doNothing()
			.when(this.spaceLiteMapper)
			.updateSpaceLiteFromSpaceLiteDto((SpaceLitePostDto) any(), (SpaceLite) any());

		// Act and Assert
		assertSame(
			spaceLiteGetDto,
			this.spaceLiteService.updateSpaceLite(
					"Current Space Lite Name",
					new SpaceLitePostDto(
						"Name",
						"Src",
						"Room Code",
						"Invite Link",
						"Embedded Code"
					)
				)
		);
		verify(this.spaceLiteRepository, atLeast(1)).findByName((String) any());
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.spaceLiteRepository).save((SpaceLite) any());
		verify(this.spaceLiteMapper).spaceLiteToSpaceLiteDto((SpaceLite) any());
		verify(this.spaceLiteMapper)
			.updateSpaceLiteFromSpaceLiteDto((SpaceLitePostDto) any(), (SpaceLite) any());
		assertTrue(this.spaceLiteService.getSpacesForLite().isEmpty());
	}

	@Test
	void testUpdateSpaceLite2() {
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

		SpaceLite spaceLite1 = new SpaceLite();
		spaceLite1.setActive(true);
		spaceLite1.setCreated_at(null);
		spaceLite1.setEmbeddedCode("Embedded Code");
		spaceLite1.setId(123L);
		spaceLite1.setInviteLink("Invite Link");
		spaceLite1.setName("Name");
		spaceLite1.setRoomCode("Room Code");
		spaceLite1.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite1.setSrc("Src");
		spaceLite1.setUpdatedAt(null);
		when(this.spaceLiteRepository.save((SpaceLite) any())).thenReturn(spaceLite1);
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());
		when(this.spaceLiteRepository.findByName((String) any())).thenReturn(spaceLite);
		when(this.spaceLiteMapper.spaceLiteToSpaceLiteDto((SpaceLite) any()))
			.thenReturn(new SpaceLiteGetDto("Name", "Src", "Embedded Code"));
		doNothing()
			.when(this.spaceLiteMapper)
			.updateSpaceLiteFromSpaceLiteDto((SpaceLitePostDto) any(), (SpaceLite) any());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.spaceLiteService.updateSpaceLite(
						"Current Space Lite Name",
						new SpaceLitePostDto(
							"Name",
							"Src",
							"Room Code",
							"Invite Link",
							"Embedded Code"
						)
					)
		);
		verify(this.spaceLiteRepository, atLeast(1)).findByName((String) any());
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
	}

	@Test
	void testUpdateSpaceLite3() {
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

		SpaceLite spaceLite1 = new SpaceLite();
		spaceLite1.setActive(true);
		spaceLite1.setCreated_at(null);
		spaceLite1.setEmbeddedCode("Embedded Code");
		spaceLite1.setId(123L);
		spaceLite1.setInviteLink("Invite Link");
		spaceLite1.setName("Name");
		spaceLite1.setRoomCode("Room Code");
		spaceLite1.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite1.setSrc("Src");
		spaceLite1.setUpdatedAt(null);
		Optional<SpaceLite> ofResult = Optional.of(spaceLite1);

		SpaceLite spaceLite2 = new SpaceLite();
		spaceLite2.setActive(true);
		spaceLite2.setCreated_at(null);
		spaceLite2.setEmbeddedCode("Embedded Code");
		spaceLite2.setId(123L);
		spaceLite2.setInviteLink("Invite Link");
		spaceLite2.setName("Name");
		spaceLite2.setRoomCode("Room Code");
		spaceLite2.setSpaceLiteOrganizations(new HashSet<>());
		spaceLite2.setSrc("Src");
		spaceLite2.setUpdatedAt(null);
		when(this.spaceLiteRepository.save((SpaceLite) any())).thenReturn(spaceLite2);
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);
		when(this.spaceLiteRepository.findByName((String) any())).thenReturn(spaceLite);
		when(this.spaceLiteMapper.spaceLiteToSpaceLiteDto((SpaceLite) any()))
			.thenReturn(new SpaceLiteGetDto("Name", "Src", "Embedded Code"));
		doNothing()
			.when(this.spaceLiteMapper)
			.updateSpaceLiteFromSpaceLiteDto((SpaceLitePostDto) any(), (SpaceLite) any());

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() ->
				this.spaceLiteService.updateSpaceLite(
						null,
						new SpaceLitePostDto(
							"Name",
							"Src",
							"Room Code",
							"Invite Link",
							"Embedded Code"
						)
					)
		);
		verify(this.spaceLiteRepository).findByName((String) any());
	}

	@Test
	void testGetActiveSpaceLiteDto() {
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
		SpaceLiteGetDto spaceLiteGetDto = new SpaceLiteGetDto(
			"Name",
			"Src",
			"Embedded Code"
		);

		when(this.spaceLiteMapper.spaceLiteToSpaceLiteDto((SpaceLite) any()))
			.thenReturn(spaceLiteGetDto);

		// Act and Assert
		assertSame(
			spaceLiteGetDto,
			this.spaceLiteService.getActiveSpaceLiteDto("Space Lite Name")
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.spaceLiteMapper).spaceLiteToSpaceLiteDto((SpaceLite) any());
		assertTrue(this.spaceLiteService.getSpacesForLite().isEmpty());
	}

	@Test
	void testGetActiveSpaceLiteDto2() {
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
		when(this.spaceLiteMapper.spaceLiteToSpaceLiteDto((SpaceLite) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.spaceLiteService.getActiveSpaceLiteDto("Space Lite Name")
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.spaceLiteMapper).spaceLiteToSpaceLiteDto((SpaceLite) any());
	}

	@Test
	void testGetActiveSpaceLiteDto3() {
		// Arrange
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());
		when(this.spaceLiteMapper.spaceLiteToSpaceLiteDto((SpaceLite) any()))
			.thenReturn(new SpaceLiteGetDto("Name", "Src", "Embedded Code"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.spaceLiteService.getActiveSpaceLiteDto("Space Lite Name")
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
	}

	@Test
	void testGetActiveSpaceLite() {
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

		// Act and Assert
		assertSame(
			spaceLite,
			this.spaceLiteService.getActiveSpaceLite("Space Lite Name")
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		assertTrue(this.spaceLiteService.getSpacesForLite().isEmpty());
	}

	@Test
	void testGetActiveSpaceLite2() {
		// Arrange
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.spaceLiteService.getActiveSpaceLite("Space Lite Name")
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
	}

	@Test
	void testGetActiveSpaceLite3() {
		// Arrange
		when(
			this.spaceLiteRepository.findByNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.spaceLiteService.getActiveSpaceLite("Space Lite Name")
		);
		verify(this.spaceLiteRepository)
			.findByNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
	}

	@Test
	void testGetSpaceLiteForUser() {
		// Arrange
		when(this.iAuthenticationFacade.getAuthentication())
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.spaceLiteService.getSpaceLiteForUser()
		);
		verify(this.iAuthenticationFacade).getAuthentication();
	}
}
