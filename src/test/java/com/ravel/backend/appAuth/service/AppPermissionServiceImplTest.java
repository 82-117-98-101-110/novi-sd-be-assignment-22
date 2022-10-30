package com.ravel.backend.appAuth.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.appAuth.dto.PermissionGetDto;
import com.ravel.backend.appAuth.dto.PermissionPostDto;
import com.ravel.backend.appAuth.dto.PermissionRoleGetDto;
import com.ravel.backend.appAuth.mappers.AppAuthMapper;
import com.ravel.backend.appAuth.model.AppPermission;
import com.ravel.backend.appAuth.model.AppRole;
import com.ravel.backend.appAuth.repository.AppPermissionRepository;
import com.ravel.backend.appAuth.repository.AppRoleRepository;
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

@ContextConfiguration(classes = { AppPermissionServiceImpl.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppPermissionServiceImplTest {

	@MockBean
	private AppAuthMapper appAuthMapper;

	@MockBean
	private AppPermissionRepository appPermissionRepository;

	@Autowired
	private AppPermissionServiceImpl appPermissionServiceImpl;

	@MockBean
	private AppRoleRepository appRoleRepository;

	@Test
	void testGetAppPermission() {
		// Arrange
		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);
		when(
			this.appPermissionRepository.existsByAppPermissionNameIgnoreCase(
					(String) any()
				)
		)
			.thenReturn(true);

		PermissionGetDto permissionGetDto = new PermissionGetDto();
		permissionGetDto.setAppPermissionName("App Permission Name");
		permissionGetDto.setDescription("The characteristics of someone or something");
		when(this.appAuthMapper.permissionToPermissionGetDto((AppPermission) any()))
			.thenReturn(permissionGetDto);

		// Act and Assert
		assertSame(
			permissionGetDto,
			this.appPermissionServiceImpl.getAppPermission("App Permission Name")
		);
		verify(this.appPermissionRepository)
			.existsByAppPermissionNameIgnoreCase((String) any());
		verify(this.appPermissionRepository)
			.findByAppPermissionNameIgnoreCase((String) any());
		verify(this.appAuthMapper).permissionToPermissionGetDto((AppPermission) any());
		assertTrue(this.appPermissionServiceImpl.getAllAppPermissions().isEmpty());
	}

	@Test
	void testGetAppPermission2() {
		// Arrange
		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);
		when(
			this.appPermissionRepository.existsByAppPermissionNameIgnoreCase(
					(String) any()
				)
		)
			.thenReturn(true);
		when(this.appAuthMapper.permissionToPermissionGetDto((AppPermission) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appPermissionServiceImpl.getAppPermission("App Permission Name")
		);
		verify(this.appPermissionRepository)
			.existsByAppPermissionNameIgnoreCase((String) any());
		verify(this.appPermissionRepository)
			.findByAppPermissionNameIgnoreCase((String) any());
		verify(this.appAuthMapper).permissionToPermissionGetDto((AppPermission) any());
	}

	@Test
	void testGetAppPermission3() {
		// Arrange
		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);
		when(
			this.appPermissionRepository.existsByAppPermissionNameIgnoreCase(
					(String) any()
				)
		)
			.thenReturn(false);

		PermissionGetDto permissionGetDto = new PermissionGetDto();
		permissionGetDto.setAppPermissionName("App Permission Name");
		permissionGetDto.setDescription("The characteristics of someone or something");
		when(this.appAuthMapper.permissionToPermissionGetDto((AppPermission) any()))
			.thenReturn(permissionGetDto);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appPermissionServiceImpl.getAppPermission("App Permission Name")
		);
		verify(this.appPermissionRepository)
			.existsByAppPermissionNameIgnoreCase((String) any());
	}

	@Test
	void testCreateNewAppPermission() {
		// Arrange
		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);

		PermissionPostDto permissionPostDto = new PermissionPostDto();
		permissionPostDto.setAppPermissionName("App Permission Name");
		permissionPostDto.setDescription("The characteristics of someone or something");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() -> this.appPermissionServiceImpl.createNewAppPermission(permissionPostDto)
		);
		verify(this.appPermissionRepository)
			.findByAppPermissionNameIgnoreCase((String) any());
	}

	@Test
	void testCreateNewAppPermission2() {
		// Arrange
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenThrow(new NotFoundException("An error occurred"));

		PermissionPostDto permissionPostDto = new PermissionPostDto();
		permissionPostDto.setAppPermissionName("App Permission Name");
		permissionPostDto.setDescription("The characteristics of someone or something");

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appPermissionServiceImpl.createNewAppPermission(permissionPostDto)
		);
		verify(this.appPermissionRepository)
			.findByAppPermissionNameIgnoreCase((String) any());
	}

	@Test
	void testUpdatePermission() {
		// Arrange
		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		AppPermission appPermission1 = new AppPermission();
		appPermission1.setAppPermissionId(123L);
		appPermission1.setAppPermissionName("App Permission Name");
		appPermission1.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission1.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission1.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.appPermissionRepository.save((AppPermission) any()))
			.thenReturn(appPermission1);
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);

		PermissionPostDto permissionPostDto = new PermissionPostDto();
		permissionPostDto.setAppPermissionName("App Permission Name");
		permissionPostDto.setDescription("The characteristics of someone or something");

		// Act and Assert
		assertNull(
			this.appPermissionServiceImpl.updatePermission(
					"Current Permission Name",
					permissionPostDto
				)
		);
		verify(this.appPermissionRepository, atLeast(1))
			.findByAppPermissionNameIgnoreCase((String) any());
		verify(this.appPermissionRepository).save((AppPermission) any());
		assertTrue(this.appPermissionServiceImpl.getAllAppPermissions().isEmpty());
	}

	@Test
	void testUpdatePermission2() {
		// Arrange
		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);

		AppPermission appPermission1 = new AppPermission();
		appPermission1.setAppPermissionId(123L);
		appPermission1.setAppPermissionName("App Permission Name");
		appPermission1.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission1.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission1.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.appPermissionRepository.save((AppPermission) any()))
			.thenReturn(appPermission1);
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);

		PermissionPostDto permissionPostDto = new PermissionPostDto();
		permissionPostDto.setAppPermissionName("App Permission Name");
		permissionPostDto.setDescription("The characteristics of someone or something");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() -> this.appPermissionServiceImpl.updatePermission(null, permissionPostDto)
		);
		verify(this.appPermissionRepository)
			.findByAppPermissionNameIgnoreCase((String) any());
	}

	@Test
	void testAddRoleToPermission() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		appRole.setAppRoleId(123L);
		appRole.setAppRoleName("App Role Name");
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		appRole.setPurpose("Purpose");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.appRoleRepository.findByAppRoleNameIgnoreCase((String) any()))
			.thenReturn(appRole);
		when(this.appRoleRepository.existsByAppRoleNameIgnoreCase((String) any()))
			.thenReturn(true);

		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);

		AppPermission appPermission1 = new AppPermission();
		appPermission1.setAppPermissionId(123L);
		appPermission1.setAppPermissionName("App Permission Name");
		appPermission1.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission1.setCreatedAt(
			Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission1.setUpdatedAt(
			Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.appPermissionRepository.save((AppPermission) any()))
			.thenReturn(appPermission1);
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);
		when(
			this.appPermissionRepository.existsByAppPermissionNameIgnoreCase(
					(String) any()
				)
		)
			.thenReturn(true);

		// Act and Assert
		assertSame(
			appPermission,
			this.appPermissionServiceImpl.addRoleToPermission(
					"App Permission Name",
					"App Role Name"
				)
		);
		verify(this.appRoleRepository).existsByAppRoleNameIgnoreCase((String) any());
		verify(this.appRoleRepository).findByAppRoleNameIgnoreCase((String) any());
		verify(this.appPermissionRepository)
			.existsByAppPermissionNameIgnoreCase((String) any());
		verify(this.appPermissionRepository)
			.findByAppPermissionNameIgnoreCase((String) any());
		verify(this.appPermissionRepository).save((AppPermission) any());
		assertTrue(this.appPermissionServiceImpl.getAllAppPermissions().isEmpty());
	}

	@Test
	void testAddRoleToPermission2() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		appRole.setAppRoleId(123L);
		appRole.setAppRoleName("App Role Name");
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		appRole.setPurpose("Purpose");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.appRoleRepository.findByAppRoleNameIgnoreCase((String) any()))
			.thenReturn(appRole);
		when(this.appRoleRepository.existsByAppRoleNameIgnoreCase((String) any()))
			.thenReturn(true);

		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.appPermissionRepository.save((AppPermission) any()))
			.thenThrow(new NotFoundException("An error occurred"));
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);
		when(
			this.appPermissionRepository.existsByAppPermissionNameIgnoreCase(
					(String) any()
				)
		)
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.appPermissionServiceImpl.addRoleToPermission(
						"App Permission Name",
						"App Role Name"
					)
		);
		verify(this.appRoleRepository).existsByAppRoleNameIgnoreCase((String) any());
		verify(this.appRoleRepository).findByAppRoleNameIgnoreCase((String) any());
		verify(this.appPermissionRepository)
			.existsByAppPermissionNameIgnoreCase((String) any());
		verify(this.appPermissionRepository)
			.findByAppPermissionNameIgnoreCase((String) any());
		verify(this.appPermissionRepository).save((AppPermission) any());
	}

	@Test
	void testAddRoleToPermission3() {
		// Arrange
		AppRole appRole = new AppRole();
		appRole.setAppPermissions(new HashSet<>());
		appRole.setAppRoleId(123L);
		appRole.setAppRoleName("App Role Name");
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appRole.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appRole.setDescription("The characteristics of someone or something");
		appRole.setPurpose("Purpose");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appRole.setUpdatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.appRoleRepository.findByAppRoleNameIgnoreCase((String) any()))
			.thenReturn(appRole);
		when(this.appRoleRepository.existsByAppRoleNameIgnoreCase((String) any()))
			.thenReturn(false);

		AppPermission appPermission = new AppPermission();
		appPermission.setAppPermissionId(123L);
		appPermission.setAppPermissionName("App Permission Name");
		appPermission.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission.setUpdatedAt(
			Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant())
		);

		AppPermission appPermission1 = new AppPermission();
		appPermission1.setAppPermissionId(123L);
		appPermission1.setAppPermissionName("App Permission Name");
		appPermission1.setAppRoles(new HashSet<>());
		LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission1.setCreatedAt(
			Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant())
		);
		appPermission1.setDescription("The characteristics of someone or something");
		LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appPermission1.setUpdatedAt(
			Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant())
		);
		when(this.appPermissionRepository.save((AppPermission) any()))
			.thenReturn(appPermission1);
		when(
			this.appPermissionRepository.findByAppPermissionNameIgnoreCase((String) any())
		)
			.thenReturn(appPermission);
		when(
			this.appPermissionRepository.existsByAppPermissionNameIgnoreCase(
					(String) any()
				)
		)
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.appPermissionServiceImpl.addRoleToPermission(
						"App Permission Name",
						"App Role Name"
					)
		);
		verify(this.appRoleRepository).existsByAppRoleNameIgnoreCase((String) any());
	}

	@Test
	void testGetAllAppPermissions() {
		// Arrange
		when(this.appPermissionRepository.findAll()).thenReturn(new ArrayList<>());
		ArrayList<PermissionGetDto> permissionGetDtoList = new ArrayList<>();
		when(
			this.appAuthMapper.permissionsToPermissionGetDtoList(
					(List<AppPermission>) any()
				)
		)
			.thenReturn(permissionGetDtoList);

		// Act
		List<PermissionGetDto> actualAllAppPermissions =
			this.appPermissionServiceImpl.getAllAppPermissions();

		// Assert
		assertSame(permissionGetDtoList, actualAllAppPermissions);
		assertTrue(actualAllAppPermissions.isEmpty());
		verify(this.appPermissionRepository).findAll();
		verify(this.appAuthMapper)
			.permissionsToPermissionGetDtoList((List<AppPermission>) any());
		assertTrue(
			this.appPermissionServiceImpl.getAllAppPermissionsWithPermissions().isEmpty()
		);
	}

	@Test
	void testGetAllAppPermissions2() {
		// Arrange
		when(this.appPermissionRepository.findAll()).thenReturn(new ArrayList<>());
		when(
			this.appAuthMapper.permissionsToPermissionGetDtoList(
					(List<AppPermission>) any()
				)
		)
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appPermissionServiceImpl.getAllAppPermissions()
		);
		verify(this.appPermissionRepository).findAll();
		verify(this.appAuthMapper)
			.permissionsToPermissionGetDtoList((List<AppPermission>) any());
	}

	@Test
	void testGetAllAppPermissionsWithPermissions() {
		// Arrange
		when(this.appPermissionRepository.findAll()).thenReturn(new ArrayList<>());
		ArrayList<PermissionRoleGetDto> permissionRoleGetDtoList = new ArrayList<>();
		when(
			this.appAuthMapper.permissionToPermissionRolesDto((List<AppPermission>) any())
		)
			.thenReturn(permissionRoleGetDtoList);

		// Act
		List<PermissionRoleGetDto> actualAllAppPermissionsWithPermissions =
			this.appPermissionServiceImpl.getAllAppPermissionsWithPermissions();

		// Assert
		assertSame(permissionRoleGetDtoList, actualAllAppPermissionsWithPermissions);
		assertTrue(actualAllAppPermissionsWithPermissions.isEmpty());
		verify(this.appPermissionRepository).findAll();
		verify(this.appAuthMapper)
			.permissionToPermissionRolesDto((List<AppPermission>) any());
		assertTrue(this.appPermissionServiceImpl.getAllAppPermissions().isEmpty());
	}

	@Test
	void testGetAllAppPermissionsWithPermissions2() {
		// Arrange
		when(this.appPermissionRepository.findAll()).thenReturn(new ArrayList<>());
		when(
			this.appAuthMapper.permissionToPermissionRolesDto((List<AppPermission>) any())
		)
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appPermissionServiceImpl.getAllAppPermissionsWithPermissions()
		);
		verify(this.appPermissionRepository).findAll();
		verify(this.appAuthMapper)
			.permissionToPermissionRolesDto((List<AppPermission>) any());
	}
}
