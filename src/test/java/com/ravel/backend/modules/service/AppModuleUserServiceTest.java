package com.ravel.backend.modules.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.modules.model.AppModule;
import com.ravel.backend.modules.model.AppModuleOrganization;
import com.ravel.backend.modules.model.AppModuleUser;
import com.ravel.backend.modules.repository.AppModuleOrganizationRepository;
import com.ravel.backend.modules.repository.AppModuleUserRepository;
import com.ravel.backend.modules.repository.ModuleRepository;
import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
	classes = { AppModuleUserService.class, AppModuleOrganizationService.class }
)
@ActiveProfiles({ "h2" })
@ExtendWith(SpringExtension.class)
class AppModuleUserServiceTest {

	@MockBean
	private AppModuleOrganizationRepository appModuleOrganizationRepository;

	@MockBean
	private AppModuleUserRepository appModuleUserRepository;

	@Autowired
	private AppModuleUserService appModuleUserService;

	@MockBean
	private ModuleRepository moduleRepository;

	@MockBean
	private OrganizationService organizationService;

	@MockBean
	private OrganizationUserRoleService organizationUserRoleService;

	@MockBean
	private UserService userService;

	@Test
	void testAddNewUserToModule() {
		// Arrange
		when(this.userService.getUserUuidFromActiveUser((String) any()))
			.thenReturn(UUID.randomUUID());

		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");

		AppModuleUser appModuleUser1 = new AppModuleUser();
		appModuleUser1.setActive(true);
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser1.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser1.setEnrolledModules(new HashSet<>());
		appModuleUser1.setUserUUID(UUID.randomUUID());
		appModuleUser1.setUsername("janedoe");
		when(this.appModuleUserRepository.save((AppModuleUser) any()))
			.thenReturn(appModuleUser1);
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);
		when(this.appModuleUserRepository.existsByUserUUID((UUID) any()))
			.thenReturn(true);

		// Act and Assert
		assertSame(
			appModuleUser,
			this.appModuleUserService.addNewUserToModule(
					"jane.doe@example.org",
					"Module Name"
				)
		);
		verify(this.userService).getUserUuidFromActiveUser((String) any());
		verify(this.moduleRepository)
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.appModuleUserRepository).existsByUserUUID((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleUserRepository).save((AppModuleUser) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testAddNewUserToModule2() {
		// Arrange
		when(this.userService.getUserUuidFromActiveUser((String) any()))
			.thenReturn(UUID.randomUUID());

		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");
		when(this.appModuleUserRepository.save((AppModuleUser) any()))
			.thenThrow(new NotFoundException("An error occurred"));
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);
		when(this.appModuleUserRepository.existsByUserUUID((UUID) any()))
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.appModuleUserService.addNewUserToModule(
						"jane.doe@example.org",
						"Module Name"
					)
		);
		verify(this.userService).getUserUuidFromActiveUser((String) any());
		verify(this.moduleRepository)
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.appModuleUserRepository).existsByUserUUID((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleUserRepository).save((AppModuleUser) any());
	}

	@Test
	void testAddNewUserToModule3() {
		// Arrange
		when(this.userService.getUserUuidFromActiveUser((String) any()))
			.thenReturn(UUID.randomUUID());

		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");

		AppModuleUser appModuleUser1 = new AppModuleUser();
		appModuleUser1.setActive(true);
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser1.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser1.setEnrolledModules(new HashSet<>());
		appModuleUser1.setUserUUID(UUID.randomUUID());
		appModuleUser1.setUsername("janedoe");
		when(this.appModuleUserRepository.save((AppModuleUser) any()))
			.thenReturn(appModuleUser1);
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);
		when(this.appModuleUserRepository.existsByUserUUID((UUID) any()))
			.thenReturn(false);

		// Act and Assert
		assertSame(
			appModuleUser,
			this.appModuleUserService.addNewUserToModule(
					"jane.doe@example.org",
					"Module Name"
				)
		);
		verify(this.userService).getUserUuidFromActiveUser((String) any());
		verify(this.moduleRepository, atLeast(1))
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.appModuleUserRepository, atLeast(1)).existsByUserUUID((UUID) any());
		verify(this.appModuleUserRepository, atLeast(1))
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleUserRepository, atLeast(1)).save((AppModuleUser) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testAddNewUserUuidToModule() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");

		AppModuleUser appModuleUser1 = new AppModuleUser();
		appModuleUser1.setActive(true);
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser1.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser1.setEnrolledModules(new HashSet<>());
		appModuleUser1.setUserUUID(UUID.randomUUID());
		appModuleUser1.setUsername("janedoe");
		when(this.appModuleUserRepository.save((AppModuleUser) any()))
			.thenReturn(appModuleUser1);
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);
		when(this.appModuleUserRepository.existsByUserUUID((UUID) any()))
			.thenReturn(true);

		// Act and Assert
		assertSame(
			appModuleUser,
			this.appModuleUserService.addNewUserUuidToModule(
					UUID.randomUUID(),
					"Module Name"
				)
		);
		verify(this.moduleRepository)
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.appModuleUserRepository).existsByUserUUID((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleUserRepository).save((AppModuleUser) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testAddNewUserUuidToModule2() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");
		when(this.appModuleUserRepository.save((AppModuleUser) any()))
			.thenThrow(new NotFoundException("An error occurred"));
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);
		when(this.appModuleUserRepository.existsByUserUUID((UUID) any()))
			.thenReturn(true);

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() ->
				this.appModuleUserService.addNewUserUuidToModule(
						UUID.randomUUID(),
						"Module Name"
					)
		);
		verify(this.moduleRepository)
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.appModuleUserRepository).existsByUserUUID((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleUserRepository).save((AppModuleUser) any());
	}

	@Test
	void testAddNewUserUuidToModule3() {
		// Arrange
		AppModule appModule = new AppModule();
		appModule.setActive(true);
		appModule.setAppModuleOrganizations(new HashSet<>());
		appModule.setAppModuleUsers(new HashSet<>());
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModule.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModule.setDescription("The characteristics of someone or something");
		appModule.setModuleColor("Module Color");
		appModule.setModuleImageUrl("https://example.org/example");
		appModule.setModuleName("Module Name");
		when(
			this.moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
					(String) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModule);

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");

		AppModuleUser appModuleUser1 = new AppModuleUser();
		appModuleUser1.setActive(true);
		LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser1.setCreatedAt(
			Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser1.setEnrolledModules(new HashSet<>());
		appModuleUser1.setUserUUID(UUID.randomUUID());
		appModuleUser1.setUsername("janedoe");
		when(this.appModuleUserRepository.save((AppModuleUser) any()))
			.thenReturn(appModuleUser1);
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);
		when(this.appModuleUserRepository.existsByUserUUID((UUID) any()))
			.thenReturn(false);

		// Act and Assert
		assertSame(
			appModuleUser,
			this.appModuleUserService.addNewUserUuidToModule(
					UUID.randomUUID(),
					"Module Name"
				)
		);
		verify(this.moduleRepository, atLeast(1))
			.findByModuleNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
		verify(this.appModuleUserRepository, atLeast(1)).existsByUserUUID((UUID) any());
		verify(this.appModuleUserRepository, atLeast(1))
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleUserRepository, atLeast(1)).save((AppModuleUser) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testGetModulesForUser() {
		// Arrange
		when(this.userService.getUserUuidFromActiveUser((String) any()))
			.thenReturn(UUID.randomUUID());
		when(this.organizationUserRoleService.getOrganizationIdsForUser((UUID) any()))
			.thenReturn(new ArrayList<>());

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);
		when(
			this.appModuleOrganizationRepository.findByOrganizationIdAndIsActive(
					(List<UUID>) any()
				)
		)
			.thenReturn(new ArrayList<>());

		// Act
		AppModuleUser actualModulesForUser =
			this.appModuleUserService.getModulesForUser("jane.doe@example.org");

		// Assert
		assertSame(appModuleUser, actualModulesForUser);
		assertTrue(actualModulesForUser.getEnrolledModules().isEmpty());
		verify(this.userService).getUserUuidFromActiveUser((String) any());
		verify(this.organizationUserRoleService).getOrganizationIdsForUser((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleOrganizationRepository)
			.findByOrganizationIdAndIsActive((List<UUID>) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testGetModulesForUser2() {
		// Arrange
		when(this.userService.getUserUuidFromActiveUser((String) any()))
			.thenReturn(UUID.randomUUID());
		when(this.organizationUserRoleService.getOrganizationIdsForUser((UUID) any()))
			.thenReturn(new ArrayList<>());

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		appModuleOrganization.setEnrolledModules(new HashSet<>());
		appModuleOrganization.setOrganizationId(UUID.randomUUID());

		ArrayList<AppModuleOrganization> appModuleOrganizationList = new ArrayList<>();
		appModuleOrganizationList.add(appModuleOrganization);
		when(
			this.appModuleOrganizationRepository.findByOrganizationIdAndIsActive(
					(List<UUID>) any()
				)
		)
			.thenReturn(appModuleOrganizationList);

		// Act
		AppModuleUser actualModulesForUser =
			this.appModuleUserService.getModulesForUser("jane.doe@example.org");

		// Assert
		assertSame(appModuleUser, actualModulesForUser);
		assertTrue(actualModulesForUser.getEnrolledModules().isEmpty());
		verify(this.userService).getUserUuidFromActiveUser((String) any());
		verify(this.organizationUserRoleService).getOrganizationIdsForUser((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleOrganizationRepository)
			.findByOrganizationIdAndIsActive((List<UUID>) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testGetModulesForUser3() {
		// Arrange
		when(this.userService.getUserUuidFromActiveUser((String) any()))
			.thenReturn(UUID.randomUUID());
		when(this.organizationUserRoleService.getOrganizationIdsForUser((UUID) any()))
			.thenReturn(new ArrayList<>());

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		appModuleOrganization.setEnrolledModules(new HashSet<>());
		appModuleOrganization.setOrganizationId(UUID.randomUUID());

		AppModuleOrganization appModuleOrganization1 = new AppModuleOrganization();
		appModuleOrganization1.setActive(true);
		appModuleOrganization1.setCreatedAt(null);
		appModuleOrganization1.setEnrolledModules(new HashSet<>());
		appModuleOrganization1.setOrganizationId(UUID.randomUUID());

		ArrayList<AppModuleOrganization> appModuleOrganizationList = new ArrayList<>();
		appModuleOrganizationList.add(appModuleOrganization1);
		appModuleOrganizationList.add(appModuleOrganization);
		when(
			this.appModuleOrganizationRepository.findByOrganizationIdAndIsActive(
					(List<UUID>) any()
				)
		)
			.thenReturn(appModuleOrganizationList);

		// Act
		AppModuleUser actualModulesForUser =
			this.appModuleUserService.getModulesForUser("jane.doe@example.org");

		// Assert
		assertSame(appModuleUser, actualModulesForUser);
		assertTrue(actualModulesForUser.getEnrolledModules().isEmpty());
		verify(this.userService).getUserUuidFromActiveUser((String) any());
		verify(this.organizationUserRoleService).getOrganizationIdsForUser((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleOrganizationRepository)
			.findByOrganizationIdAndIsActive((List<UUID>) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testGetModulesForUserUuid() {
		// Arrange
		when(this.organizationUserRoleService.getOrganizationIdsForUser((UUID) any()))
			.thenReturn(new ArrayList<>());

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);
		when(
			this.appModuleOrganizationRepository.findByOrganizationIdAndIsActive(
					(List<UUID>) any()
				)
		)
			.thenReturn(new ArrayList<>());

		// Act
		AppModuleUser actualModulesForUserUuid =
			this.appModuleUserService.getModulesForUserUuid(UUID.randomUUID());

		// Assert
		assertSame(appModuleUser, actualModulesForUserUuid);
		assertTrue(actualModulesForUserUuid.getEnrolledModules().isEmpty());
		verify(this.organizationUserRoleService).getOrganizationIdsForUser((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleOrganizationRepository)
			.findByOrganizationIdAndIsActive((List<UUID>) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testGetModulesForUserUuid2() {
		// Arrange
		when(this.organizationUserRoleService.getOrganizationIdsForUser((UUID) any()))
			.thenReturn(new ArrayList<>());

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		appModuleOrganization.setEnrolledModules(new HashSet<>());
		appModuleOrganization.setOrganizationId(UUID.randomUUID());

		ArrayList<AppModuleOrganization> appModuleOrganizationList = new ArrayList<>();
		appModuleOrganizationList.add(appModuleOrganization);
		when(
			this.appModuleOrganizationRepository.findByOrganizationIdAndIsActive(
					(List<UUID>) any()
				)
		)
			.thenReturn(appModuleOrganizationList);

		// Act
		AppModuleUser actualModulesForUserUuid =
			this.appModuleUserService.getModulesForUserUuid(UUID.randomUUID());

		// Assert
		assertSame(appModuleUser, actualModulesForUserUuid);
		assertTrue(actualModulesForUserUuid.getEnrolledModules().isEmpty());
		verify(this.organizationUserRoleService).getOrganizationIdsForUser((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleOrganizationRepository)
			.findByOrganizationIdAndIsActive((List<UUID>) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testGetModulesForUserUuid3() {
		// Arrange
		when(this.organizationUserRoleService.getOrganizationIdsForUser((UUID) any()))
			.thenReturn(new ArrayList<>());

		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setActive(true);
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		appModuleUser.setCreatedAt(
			Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())
		);
		appModuleUser.setEnrolledModules(new HashSet<>());
		appModuleUser.setUserUUID(UUID.randomUUID());
		appModuleUser.setUsername("janedoe");
		when(
			this.appModuleUserRepository.findByUserUUIDAndIsActive(
					(UUID) any(),
					anyBoolean()
				)
		)
			.thenReturn(appModuleUser);

		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(null);
		appModuleOrganization.setEnrolledModules(new HashSet<>());
		appModuleOrganization.setOrganizationId(UUID.randomUUID());

		AppModuleOrganization appModuleOrganization1 = new AppModuleOrganization();
		appModuleOrganization1.setActive(true);
		appModuleOrganization1.setCreatedAt(null);
		appModuleOrganization1.setEnrolledModules(new HashSet<>());
		appModuleOrganization1.setOrganizationId(UUID.randomUUID());

		ArrayList<AppModuleOrganization> appModuleOrganizationList = new ArrayList<>();
		appModuleOrganizationList.add(appModuleOrganization1);
		appModuleOrganizationList.add(appModuleOrganization);
		when(
			this.appModuleOrganizationRepository.findByOrganizationIdAndIsActive(
					(List<UUID>) any()
				)
		)
			.thenReturn(appModuleOrganizationList);

		// Act
		AppModuleUser actualModulesForUserUuid =
			this.appModuleUserService.getModulesForUserUuid(UUID.randomUUID());

		// Assert
		assertSame(appModuleUser, actualModulesForUserUuid);
		assertTrue(actualModulesForUserUuid.getEnrolledModules().isEmpty());
		verify(this.organizationUserRoleService).getOrganizationIdsForUser((UUID) any());
		verify(this.appModuleUserRepository)
			.findByUserUUIDAndIsActive((UUID) any(), anyBoolean());
		verify(this.appModuleOrganizationRepository)
			.findByOrganizationIdAndIsActive((List<UUID>) any());
		assertTrue(this.appModuleUserService.getAllModuleUsers().isEmpty());
	}

	@Test
	void testGetAllModuleUsers() {
		// Arrange
		ArrayList<AppModuleUser> appModuleUserList = new ArrayList<>();
		when(this.appModuleUserRepository.findAll()).thenReturn(appModuleUserList);

		// Act
		List<AppModuleUser> actualAllModuleUsers =
			this.appModuleUserService.getAllModuleUsers();

		// Assert
		assertSame(appModuleUserList, actualAllModuleUsers);
		assertTrue(actualAllModuleUsers.isEmpty());
		verify(this.appModuleUserRepository).findAll();
	}

	@Test
	void testGetAllModuleUsers2() {
		// Arrange
		when(this.appModuleUserRepository.findAll())
			.thenThrow(new NotFoundException("An error occurred"));

		// Act and Assert
		assertThrows(
			NotFoundException.class,
			() -> this.appModuleUserService.getAllModuleUsers()
		);
		verify(this.appModuleUserRepository).findAll();
	}
}
