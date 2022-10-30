package com.ravel.backend.organization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ravel.backend.organization.dtos.OrganizationGetDto;
import com.ravel.backend.organization.dtos.OrganizationPostDto;
import com.ravel.backend.organization.mappers.OrganizationMapper;
import com.ravel.backend.organization.model.Organization;
import com.ravel.backend.organization.repository.OrganizationRepository;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { OrganizationServiceImpl.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class OrganizationServiceImplTest {

	@MockBean
	private OrganizationMapper organizationMapper;

	@MockBean
	private OrganizationRepository organizationRepository;

	@Autowired
	private OrganizationServiceImpl organizationServiceImpl;

	@Test
	void testCreateOrganization() {
		// Arrange
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(true);

		OrganizationPostDto organizationPostDto = new OrganizationPostDto();
		organizationPostDto.setOrganizationName("Organization Name");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() -> this.organizationServiceImpl.createOrganization(organizationPostDto)
		);
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
	}

	@Test
	void testCreateOrganization2() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);
		when(this.organizationRepository.save((Organization) any()))
			.thenReturn(organization);
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(false);

		OrganizationPostDto organizationPostDto = new OrganizationPostDto();
		organizationPostDto.setOrganizationName("Organization Name");

		// Act and Assert
		assertNull(this.organizationServiceImpl.createOrganization(organizationPostDto));
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
		verify(this.organizationRepository).save((Organization) any());
		assertTrue(this.organizationServiceImpl.getAllActiveOrganizations().isEmpty());
	}

	@Test
	void testUpdateOrganization() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);

		Organization organization1 = new Organization();
		organization1.setActive(true);
		organization1.setCreatedAt(null);
		organization1.setOrganizationId(UUID.randomUUID());
		organization1.setOrganizationName("Organization Name");
		organization1.setOrganizationUserRoles(new HashSet<>());
		organization1.setUpdatedAt(null);
		when(this.organizationRepository.save((Organization) any()))
			.thenReturn(organization1);
		when(this.organizationRepository.findByOrganizationNameIgnoreCase((String) any()))
			.thenReturn(organization);

		OrganizationPostDto organizationPostDto = new OrganizationPostDto();
		organizationPostDto.setOrganizationName("Organization Name");

		// Act and Assert
		assertNull(
			this.organizationServiceImpl.updateOrganization(
					"Current Organization Name",
					organizationPostDto
				)
		);
		verify(this.organizationRepository, atLeast(1))
			.findByOrganizationNameIgnoreCase((String) any());
		verify(this.organizationRepository).save((Organization) any());
		assertTrue(this.organizationServiceImpl.getAllActiveOrganizations().isEmpty());
	}

	@Test
	void testUpdateOrganization2() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);

		Organization organization1 = new Organization();
		organization1.setActive(true);
		organization1.setCreatedAt(null);
		organization1.setOrganizationId(UUID.randomUUID());
		organization1.setOrganizationName("Organization Name");
		organization1.setOrganizationUserRoles(new HashSet<>());
		organization1.setUpdatedAt(null);
		when(this.organizationRepository.save((Organization) any()))
			.thenReturn(organization1);
		when(this.organizationRepository.findByOrganizationNameIgnoreCase((String) any()))
			.thenReturn(organization);

		OrganizationPostDto organizationPostDto = new OrganizationPostDto();
		organizationPostDto.setOrganizationName("Organization Name");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() ->
				this.organizationServiceImpl.updateOrganization(null, organizationPostDto)
		);
		verify(this.organizationRepository)
			.findByOrganizationNameIgnoreCase((String) any());
	}

	//	@Test
	//	void testFindAllOrganizations() {
	//		// Arrange
	//		when(this.organizationRepository.findAll()).thenReturn(new ArrayList<>());
	//		ArrayList<OrganizationGetDto> organizationGetDtoList = new ArrayList<>();
	//		when(
	//			this.organizationMapper.organizationToOrganizationGetDtoList(
	//					(List<Organization>) any()
	//				)
	//		)
	//			.thenReturn(organizationGetDtoList);
	//
	//		// Act
	//		List<OrganizationGetDto> actualFindAllOrganizationsResult =
	//			this.organizationServiceImpl.findAllOrganizations();
	//
	//		// Assert
	//		assertSame(organizationGetDtoList, actualFindAllOrganizationsResult);
	//		assertTrue(actualFindAllOrganizationsResult.isEmpty());
	//		verify(this.organizationRepository).findAll();
	//		verify(this.organizationMapper)
	//			.organizationToOrganizationGetDtoList((List<Organization>) any());
	//		assertSame(
	//			actualFindAllOrganizationsResult,
	//			this.organizationServiceImpl.getAllActiveOrganizations()
	//		);
	//	}

	@Test
	void testGetAllActiveOrganizations() {
		// Arrange
		when(this.organizationRepository.findAllActiveOrganizations())
			.thenReturn(new ArrayList<>());
		ArrayList<OrganizationGetDto> organizationGetDtoList = new ArrayList<>();
		when(
			this.organizationMapper.organizationToOrganizationGetDtoList(
					(List<Organization>) any()
				)
		)
			.thenReturn(organizationGetDtoList);

		// Act
		List<OrganizationGetDto> actualAllActiveOrganizations =
			this.organizationServiceImpl.getAllActiveOrganizations();

		// Assert
		assertSame(organizationGetDtoList, actualAllActiveOrganizations);
		assertTrue(actualAllActiveOrganizations.isEmpty());
		verify(this.organizationRepository).findAllActiveOrganizations();
		verify(this.organizationMapper)
			.organizationToOrganizationGetDtoList((List<Organization>) any());
	}

	@Test
	void testFindOrganizationsByUuids() {
		// Arrange
		when(this.organizationRepository.findByMulIds((List<UUID>) any()))
			.thenReturn(new ArrayList<>());
		ArrayList<OrganizationGetDto> organizationGetDtoList = new ArrayList<>();
		when(
			this.organizationMapper.organizationToOrganizationGetDtoList(
					(List<Organization>) any()
				)
		)
			.thenReturn(organizationGetDtoList);

		// Act
		List<OrganizationGetDto> actualFindOrganizationsByUuidsResult =
			this.organizationServiceImpl.findOrganizationsByUuids(new ArrayList<>());

		// Assert
		assertSame(organizationGetDtoList, actualFindOrganizationsByUuidsResult);
		assertTrue(actualFindOrganizationsByUuidsResult.isEmpty());
		verify(this.organizationRepository).findByMulIds((List<UUID>) any());
		verify(this.organizationMapper)
			.organizationToOrganizationGetDtoList((List<Organization>) any());
		assertSame(
			actualFindOrganizationsByUuidsResult,
			this.organizationServiceImpl.getAllActiveOrganizations()
		);
	}

	@Test
	void testDeactivateOrganization() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);

		Organization organization1 = new Organization();
		organization1.setActive(true);
		organization1.setCreatedAt(null);
		organization1.setOrganizationId(UUID.randomUUID());
		organization1.setOrganizationName("Organization Name");
		organization1.setOrganizationUserRoles(new HashSet<>());
		organization1.setUpdatedAt(null);
		when(this.organizationRepository.save((Organization) any()))
			.thenReturn(organization1);
		when(this.organizationRepository.findByOrganizationNameIgnoreCase((String) any()))
			.thenReturn(organization);
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(true);

		// Act
		Organization actualDeactivateOrganizationResult =
			this.organizationServiceImpl.deactivateOrganization(
					"Organization Name",
					true
				);

		// Assert
		assertSame(organization, actualDeactivateOrganizationResult);
		assertTrue(actualDeactivateOrganizationResult.isActive());
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
		verify(this.organizationRepository)
			.findByOrganizationNameIgnoreCase((String) any());
		verify(this.organizationRepository).save((Organization) any());
		assertTrue(this.organizationServiceImpl.getAllActiveOrganizations().isEmpty());
	}

	@Test
	void testDeactivateOrganization2() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);

		Organization organization1 = new Organization();
		organization1.setActive(true);
		organization1.setCreatedAt(null);
		organization1.setOrganizationId(UUID.randomUUID());
		organization1.setOrganizationName("Organization Name");
		organization1.setOrganizationUserRoles(new HashSet<>());
		organization1.setUpdatedAt(null);
		when(this.organizationRepository.save((Organization) any()))
			.thenReturn(organization1);
		when(this.organizationRepository.findByOrganizationNameIgnoreCase((String) any()))
			.thenReturn(organization);
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.organizationServiceImpl.deactivateOrganization(
						"Organization Name",
						true
					)
		);
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
	}

	@Test
	void testGetOrganizationById() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);
		when(
			this.organizationRepository.findByOrganizationIdAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(organization);
		when(this.organizationRepository.existsByOrganizationId((UUID) any()))
			.thenReturn(true);

		OrganizationGetDto organizationGetDto = new OrganizationGetDto();
		organizationGetDto.setOrganizationId(UUID.randomUUID());
		organizationGetDto.setOrganizationName("Organization Name");
		when(
			this.organizationMapper.organizationToOrganizationGetDto((Organization) any())
		)
			.thenReturn(organizationGetDto);

		// Act and Assert
		assertSame(
			organizationGetDto,
			this.organizationServiceImpl.getOrganizationById(UUID.randomUUID())
		);
		verify(this.organizationRepository).existsByOrganizationId((UUID) any());
		verify(this.organizationRepository)
			.findByOrganizationIdAndIsActive((UUID) any(), anyBoolean());
		verify(this.organizationMapper)
			.organizationToOrganizationGetDto((Organization) any());
		assertTrue(this.organizationServiceImpl.getAllActiveOrganizations().isEmpty());
	}

	@Test
	void testGetOrganizationById2() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);
		when(
			this.organizationRepository.findByOrganizationIdAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(organization);
		when(this.organizationRepository.existsByOrganizationId((UUID) any()))
			.thenReturn(false);

		OrganizationGetDto organizationGetDto = new OrganizationGetDto();
		organizationGetDto.setOrganizationId(UUID.randomUUID());
		organizationGetDto.setOrganizationName("Organization Name");
		when(
			this.organizationMapper.organizationToOrganizationGetDto((Organization) any())
		)
			.thenReturn(organizationGetDto);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.organizationServiceImpl.getOrganizationById(UUID.randomUUID())
		);
		verify(this.organizationRepository).existsByOrganizationId((UUID) any());
	}

	@Test
	void testGetOrganizationId() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		UUID randomUUIDResult = UUID.randomUUID();
		organization.setOrganizationId(randomUUIDResult);
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);
		Optional<Organization> ofResult = Optional.of(organization);
		when(
			this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);

		// Act and Assert
		assertSame(
			randomUUIDResult,
			this.organizationServiceImpl.getOrganizationId("Organization Name")
		);
		verify(this.organizationRepository)
			.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		assertTrue(this.organizationServiceImpl.getAllActiveOrganizations().isEmpty());
	}

	@Test
	void testGetOrganizationId2() {
		// Arrange
		when(
			this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.organizationServiceImpl.getOrganizationId("Organization Name")
		);
		verify(this.organizationRepository)
			.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
	}

	@Test
	void testGetOrganizationByName() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);
		Optional<Organization> ofResult = Optional.of(organization);
		when(
			this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(true);

		OrganizationGetDto organizationGetDto = new OrganizationGetDto();
		organizationGetDto.setOrganizationId(UUID.randomUUID());
		organizationGetDto.setOrganizationName("Organization Name");
		when(
			this.organizationMapper.organizationToOrganizationGetDto((Organization) any())
		)
			.thenReturn(organizationGetDto);

		// Act and Assert
		assertSame(
			organizationGetDto,
			this.organizationServiceImpl.getOrganizationByName("Organization Name")
		);
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
		verify(this.organizationRepository)
			.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.organizationMapper)
			.organizationToOrganizationGetDto((Organization) any());
		assertTrue(this.organizationServiceImpl.getAllActiveOrganizations().isEmpty());
	}

	@Test
	void testGetOrganizationByName2() {
		// Arrange
		when(
			this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(true);

		OrganizationGetDto organizationGetDto = new OrganizationGetDto();
		organizationGetDto.setOrganizationId(UUID.randomUUID());
		organizationGetDto.setOrganizationName("Organization Name");
		when(
			this.organizationMapper.organizationToOrganizationGetDto((Organization) any())
		)
			.thenReturn(organizationGetDto);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.organizationServiceImpl.getOrganizationByName("Organization Name")
		);
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
		verify(this.organizationRepository)
			.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
	}

	@Test
	void testGetOrganizationByName3() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);
		Optional<Organization> ofResult = Optional.of(organization);
		when(
			this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(false);

		OrganizationGetDto organizationGetDto = new OrganizationGetDto();
		organizationGetDto.setOrganizationId(UUID.randomUUID());
		organizationGetDto.setOrganizationName("Organization Name");
		when(
			this.organizationMapper.organizationToOrganizationGetDto((Organization) any())
		)
			.thenReturn(organizationGetDto);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.organizationServiceImpl.getOrganizationByName("Organization Name")
		);
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
	}

	@Test
	void testGetOrganizationByName22() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);
		Optional<Organization> ofResult = Optional.of(organization);
		when(
			this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(true);

		// Act and Assert
		assertSame(
			organization,
			this.organizationServiceImpl.getOrganizationByName2("Organization Name")
		);
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
		verify(this.organizationRepository)
			.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		assertTrue(this.organizationServiceImpl.getAllActiveOrganizations().isEmpty());
	}

	@Test
	void testGetOrganizationByName23() {
		// Arrange
		when(
			this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(Optional.empty());
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.organizationServiceImpl.getOrganizationByName2("Organization Name")
		);
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
		verify(this.organizationRepository)
			.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
	}

	@Test
	void testGetOrganizationByName24() {
		// Arrange
		Organization organization = new Organization();
		organization.setActive(true);
		organization.setCreatedAt(null);
		organization.setOrganizationId(UUID.randomUUID());
		organization.setOrganizationName("Organization Name");
		organization.setOrganizationUserRoles(new HashSet<>());
		organization.setUpdatedAt(null);
		Optional<Organization> ofResult = Optional.of(organization);
		when(
			this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(ofResult);
		when(
			this.organizationRepository.existsByOrganizationNameIgnoreCase((String) any())
		)
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.organizationServiceImpl.getOrganizationByName2("Organization Name")
		);
		verify(this.organizationRepository)
			.existsByOrganizationNameIgnoreCase((String) any());
	}
}
