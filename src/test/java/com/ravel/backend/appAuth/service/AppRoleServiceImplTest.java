package com.ravel.backend.appAuth.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.appAuth.dto.RoleGetDto;
import com.ravel.backend.appAuth.dto.RolePermissionGetDto;
import com.ravel.backend.appAuth.dto.RolePostDto;
import com.ravel.backend.appAuth.mappers.AppAuthMapper;
import com.ravel.backend.appAuth.model.AppRole;
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

@ContextConfiguration(classes = { AppRoleServiceImpl.class })
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppRoleServiceImplTest {

	@MockBean
	private AppAuthMapper appAuthMapper;

	@MockBean
	private AppRoleRepository appRoleRepository;

	@Autowired
	private AppRoleServiceImpl appRoleServiceImpl;

	@Test
	void testCreateNewAppRole() {
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

		RolePostDto rolePostDto = new RolePostDto();
		rolePostDto.setAppRoleName("App Role Name");
		rolePostDto.setDescription("The characteristics of someone or something");
		rolePostDto.setPurpose("Purpose");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() -> this.appRoleServiceImpl.createNewAppRole(rolePostDto)
		);
		verify(this.appRoleRepository).findByAppRoleNameIgnoreCase((String) any());
	}

	@Test
	void testCreateNewAppRole2() {
		// Arrange
		when(this.appRoleRepository.findByAppRoleNameIgnoreCase((String) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		RolePostDto rolePostDto = new RolePostDto();
		rolePostDto.setAppRoleName("App Role Name");
		rolePostDto.setDescription("The characteristics of someone or something");
		rolePostDto.setPurpose("Purpose");

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.createNewAppRole(rolePostDto)
		);
		verify(this.appRoleRepository).findByAppRoleNameIgnoreCase((String) any());
	}

	@Test
	void testUpdateRole() {
		// Arrange
		when(this.appRoleRepository.findByAppRoleNameIgnoreCase((String) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		RolePostDto rolePostDto = new RolePostDto();
		rolePostDto.setAppRoleName("App Role Name");
		rolePostDto.setDescription("The characteristics of someone or something");
		rolePostDto.setPurpose("Purpose");

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.updateRole("Current App Role Name", rolePostDto)
		);
		verify(this.appRoleRepository).findByAppRoleNameIgnoreCase((String) any());
	}

	@Test
	void testUpdateRole2() {
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

		RolePostDto rolePostDto = new RolePostDto();
		rolePostDto.setAppRoleName("App Role Name");
		rolePostDto.setDescription("The characteristics of someone or something");
		rolePostDto.setPurpose("Purpose");

		// Act and Assert
		assertThrows(
			AlreadyExistException.class,
			() -> this.appRoleServiceImpl.updateRole(null, rolePostDto)
		);
		verify(this.appRoleRepository).findByAppRoleNameIgnoreCase((String) any());
	}

	@Test
	void testGetAppRole() {
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

		RoleGetDto roleGetDto = new RoleGetDto();
		roleGetDto.setAppRoleName("App Role Name");
		roleGetDto.setDescription("The characteristics of someone or something");
		roleGetDto.setPurpose("Purpose");
		when(this.appAuthMapper.roleToRoleGetDto((AppRole) any())).thenReturn(roleGetDto);

		// Act and Assert
		assertSame(roleGetDto, this.appRoleServiceImpl.getAppRole("App Role Name"));
		verify(this.appRoleRepository).existsByAppRoleNameIgnoreCase((String) any());
		verify(this.appRoleRepository).findByAppRoleNameIgnoreCase((String) any());
		verify(this.appAuthMapper).roleToRoleGetDto((AppRole) any());
		assertTrue(this.appRoleServiceImpl.getAllAppRole().isEmpty());
	}

	@Test
	void testGetAppRole2() {
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
		when(this.appAuthMapper.roleToRoleGetDto((AppRole) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.getAppRole("App Role Name")
		);
		verify(this.appRoleRepository).existsByAppRoleNameIgnoreCase((String) any());
		verify(this.appRoleRepository).findByAppRoleNameIgnoreCase((String) any());
		verify(this.appAuthMapper).roleToRoleGetDto((AppRole) any());
	}

	@Test
	void testGetAppRole3() {
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

		RoleGetDto roleGetDto = new RoleGetDto();
		roleGetDto.setAppRoleName("App Role Name");
		roleGetDto.setDescription("The characteristics of someone or something");
		roleGetDto.setPurpose("Purpose");
		when(this.appAuthMapper.roleToRoleGetDto((AppRole) any())).thenReturn(roleGetDto);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.getAppRole("App Role Name")
		);
		verify(this.appRoleRepository).existsByAppRoleNameIgnoreCase((String) any());
	}

	@Test
	void testGetAppRoleById() {
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
		when(this.appRoleRepository.findByAppRoleId(anyLong())).thenReturn(appRole);
		when(this.appRoleRepository.existsByAppRoleId(anyLong())).thenReturn(true);

		RoleGetDto roleGetDto = new RoleGetDto();
		roleGetDto.setAppRoleName("App Role Name");
		roleGetDto.setDescription("The characteristics of someone or something");
		roleGetDto.setPurpose("Purpose");
		when(this.appAuthMapper.roleToRoleGetDto((AppRole) any())).thenReturn(roleGetDto);

		// Act and Assert
		assertSame(roleGetDto, this.appRoleServiceImpl.getAppRoleById(123L));
		verify(this.appRoleRepository).existsByAppRoleId(anyLong());
		verify(this.appRoleRepository).findByAppRoleId(anyLong());
		verify(this.appAuthMapper).roleToRoleGetDto((AppRole) any());
		assertTrue(this.appRoleServiceImpl.getAllAppRole().isEmpty());
	}

	@Test
	void testGetAppRoleById2() {
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
		when(this.appRoleRepository.findByAppRoleId(anyLong())).thenReturn(appRole);
		when(this.appRoleRepository.existsByAppRoleId(anyLong())).thenReturn(true);
		when(this.appAuthMapper.roleToRoleGetDto((AppRole) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.getAppRoleById(123L)
		);
		verify(this.appRoleRepository).existsByAppRoleId(anyLong());
		verify(this.appRoleRepository).findByAppRoleId(anyLong());
		verify(this.appAuthMapper).roleToRoleGetDto((AppRole) any());
	}

	@Test
	void testGetAppRoleById3() {
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
		when(this.appRoleRepository.findByAppRoleId(anyLong())).thenReturn(appRole);
		when(this.appRoleRepository.existsByAppRoleId(anyLong())).thenReturn(false);

		RoleGetDto roleGetDto = new RoleGetDto();
		roleGetDto.setAppRoleName("App Role Name");
		roleGetDto.setDescription("The characteristics of someone or something");
		roleGetDto.setPurpose("Purpose");
		when(this.appAuthMapper.roleToRoleGetDto((AppRole) any())).thenReturn(roleGetDto);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.getAppRoleById(123L)
		);
		verify(this.appRoleRepository).existsByAppRoleId(anyLong());
	}

	@Test
	void testGetAllAppRole() {
		// Arrange
		when(this.appRoleRepository.findAll()).thenReturn(new ArrayList<>());
		ArrayList<RoleGetDto> roleGetDtoList = new ArrayList<>();
		when(this.appAuthMapper.roleToRoleGetDtoList((List<AppRole>) any()))
			.thenReturn(roleGetDtoList);

		// Act
		List<RoleGetDto> actualAllAppRole = this.appRoleServiceImpl.getAllAppRole();

		// Assert
		assertSame(roleGetDtoList, actualAllAppRole);
		assertTrue(actualAllAppRole.isEmpty());
		verify(this.appRoleRepository).findAll();
		verify(this.appAuthMapper).roleToRoleGetDtoList((List<AppRole>) any());
		assertTrue(this.appRoleServiceImpl.getAllAppRolesWithPermissions().isEmpty());
	}

	@Test
	void testGetAllAppRole2() {
		// Arrange
		when(this.appRoleRepository.findAll()).thenReturn(new ArrayList<>());
		when(this.appAuthMapper.roleToRoleGetDtoList((List<AppRole>) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.getAllAppRole()
		);
		verify(this.appRoleRepository).findAll();
		verify(this.appAuthMapper).roleToRoleGetDtoList((List<AppRole>) any());
	}

	@Test
	void testGetAllAppRolesWithPermissions() {
		// Arrange
		when(this.appRoleRepository.findAll()).thenReturn(new ArrayList<>());
		ArrayList<RolePermissionGetDto> rolePermissionGetDtoList = new ArrayList<>();
		when(this.appAuthMapper.roleToRolesPermissionsDto((List<AppRole>) any()))
			.thenReturn(rolePermissionGetDtoList);

		// Act
		List<RolePermissionGetDto> actualAllAppRolesWithPermissions =
			this.appRoleServiceImpl.getAllAppRolesWithPermissions();

		// Assert
		assertSame(rolePermissionGetDtoList, actualAllAppRolesWithPermissions);
		assertTrue(actualAllAppRolesWithPermissions.isEmpty());
		verify(this.appRoleRepository).findAll();
		verify(this.appAuthMapper).roleToRolesPermissionsDto((List<AppRole>) any());
		assertTrue(this.appRoleServiceImpl.getAllAppRole().isEmpty());
	}

	@Test
	void testGetAllAppRolesWithPermissions2() {
		// Arrange
		when(this.appRoleRepository.findAll()).thenReturn(new ArrayList<>());
		when(this.appAuthMapper.roleToRolesPermissionsDto((List<AppRole>) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.getAllAppRolesWithPermissions()
		);
		verify(this.appRoleRepository).findAll();
		verify(this.appAuthMapper).roleToRolesPermissionsDto((List<AppRole>) any());
	}

	@Test
	void testFindRolesByIds() {
		// Arrange
		when(this.appRoleRepository.findByIds((List<Long>) any()))
			.thenReturn(new ArrayList<>());
		ArrayList<RolePermissionGetDto> rolePermissionGetDtoList = new ArrayList<>();
		when(this.appAuthMapper.roleToRolesPermissionsDto((List<AppRole>) any()))
			.thenReturn(rolePermissionGetDtoList);

		// Act
		List<RolePermissionGetDto> actualFindRolesByIdsResult =
			this.appRoleServiceImpl.findRolesByIds(new ArrayList<>());

		// Assert
		assertSame(rolePermissionGetDtoList, actualFindRolesByIdsResult);
		assertTrue(actualFindRolesByIdsResult.isEmpty());
		verify(this.appRoleRepository).findByIds((List<Long>) any());
		verify(this.appAuthMapper).roleToRolesPermissionsDto((List<AppRole>) any());
		assertTrue(this.appRoleServiceImpl.getAllAppRole().isEmpty());
	}

	@Test
	void testFindRolesByIds2() {
		// Arrange
		when(this.appRoleRepository.findByIds((List<Long>) any()))
			.thenReturn(new ArrayList<>());
		when(this.appAuthMapper.roleToRolesPermissionsDto((List<AppRole>) any()))
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.findRolesByIds(new ArrayList<>())
		);
		verify(this.appRoleRepository).findByIds((List<Long>) any());
		verify(this.appAuthMapper).roleToRolesPermissionsDto((List<AppRole>) any());
	}

	@Test
	void testValidatePurposeOrganization() {
		// Arrange
		when(
			this.appRoleRepository.existsByAppRoleNameAndPurpose(
					(String) any(),
					(String) any()
				)
		)
			.thenReturn(true);

		// Act and Assert
		assertNull(this.appRoleServiceImpl.validatePurposeOrganization("App Role Name"));
		verify(this.appRoleRepository)
			.existsByAppRoleNameAndPurpose((String) any(), (String) any());
		assertTrue(this.appRoleServiceImpl.getAllAppRole().isEmpty());
	}

	@Test
	void testValidatePurposeOrganization2() {
		// Arrange
		when(
			this.appRoleRepository.existsByAppRoleNameAndPurpose(
					(String) any(),
					(String) any()
				)
		)
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.validatePurposeOrganization("App Role Name")
		);
		verify(this.appRoleRepository)
			.existsByAppRoleNameAndPurpose((String) any(), (String) any());
	}

	@Test
	void testValidatePurposeOrganization3() {
		// Arrange
		when(
			this.appRoleRepository.existsByAppRoleNameAndPurpose(
					(String) any(),
					(String) any()
				)
		)
			.thenReturn(false);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appRoleServiceImpl.validatePurposeOrganization("App Role Name")
		);
		verify(this.appRoleRepository)
			.existsByAppRoleNameAndPurpose((String) any(), (String) any());
	}
}
