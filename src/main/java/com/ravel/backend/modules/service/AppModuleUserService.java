package com.ravel.backend.modules.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.ravel.backend.modules.model.AppModule;
import com.ravel.backend.modules.model.AppModuleUser;
import com.ravel.backend.modules.repository.AppModuleUserRepository;
import com.ravel.backend.modules.repository.ModuleRepository;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.service.UserService;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppModuleUserService {

	private final AppModuleUserRepository appModuleUserRepository;
	private final ModuleRepository moduleRepository;
	private UserService userService;
	private AppModuleOrganizationService appModuleOrganizationService;
	private OrganizationUserRoleService organizationUserRoleService;

	public AppModuleUser addNewUserToModule(String userEmail, String moduleName) {
		UUID userUuid = userService.getUserUuidFromActiveUser(userEmail);
		if (!appModuleUserRepository.existsByUserUUID(userUuid)) {
			addNewUserToModule(userUuid, moduleName);
		}
		AppModuleUser appModuleUser = appModuleUserRepository.findByUserUUIDAndIsActive(
			userUuid,
			true
		);
		AppModule appModule = moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
			moduleName,
			true
		);
		appModuleUser.enrolledModules.add(appModule);
		appModuleUserRepository.save(appModuleUser);
		return appModuleUser;
	}

	public AppModuleUser addNewUserUuidToModule(UUID userUuid, String moduleName) {
		if (!appModuleUserRepository.existsByUserUUID(userUuid)) {
			addNewUserToModule(userUuid, moduleName);
		}
		AppModuleUser appModuleUser = appModuleUserRepository.findByUserUUIDAndIsActive(
			userUuid,
			true
		);
		AppModule appModule = moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
			moduleName,
			true
		);
		appModuleUser.enrolledModules.add(appModule);
		appModuleUserRepository.save(appModuleUser);
		return appModuleUser;
	}

	public AppModuleUser getModulesForUser(String userEmail) {
		UUID userUuid = userService.getUserUuidFromActiveUser(userEmail);
		AppModuleUser appModuleUser = findByUserUuidAndIsActive(userUuid);
		if (appModuleUser == null) {
			AppModuleUser tempAppModuleUser = new AppModuleUser();
			tempAppModuleUser.setUserUUID(userUuid);
			AppModuleUser withOrg = modulesForOrganizationUserRole(
				userUuid,
				tempAppModuleUser
			);
			return withOrg;
		}
		AppModuleUser withOrg2 = modulesForOrganizationUserRole(userUuid, appModuleUser);
		return withOrg2;
	}

	public AppModuleUser getModulesForUserUuid(UUID userUuid) {
		AppModuleUser appModuleUser = findByUserUuidAndIsActive(userUuid);
		if (appModuleUser == null) {
			AppModuleUser tempAppModuleUser = new AppModuleUser();
			tempAppModuleUser.setUserUUID(userUuid);
			AppModuleUser withOrg = modulesForOrganizationUserRole(
				userUuid,
				tempAppModuleUser
			);
			return withOrg;
		}
		AppModuleUser withOrg2 = modulesForOrganizationUserRole(userUuid, appModuleUser);
		//TODO modules: now only returns moduels assigend to organizationUser, implement userSpecific modules
		return withOrg2;
	}

	public List<AppModuleUser> getAllModuleUsers() {
		return appModuleUserRepository.findAll();
	}

	private AppModuleUser findByUserUuidAndIsActive(UUID userUuid) {
		AppModuleUser appModuleUser = appModuleUserRepository.findByUserUUIDAndIsActive(
			userUuid,
			true
		);
		return appModuleUser;
	}

	private AppModuleUser modulesForOrganizationUserRole(
		UUID userUuid,
		AppModuleUser appModuleUser
	) {
		//Create list of organizationIds of user belongs
		List<UUID> organizationIdList = organizationUserRoleService.getOrganizationIdsForUser(
			userUuid
		);

		//Create list of AppModuleOrganization
		List<Set<AppModule>> source = appModuleOrganizationService.getModuleSetForOrganizations(
			organizationIdList
		);

		Set<AppModule> collection = Sets.newHashSet();
		source
			.stream()
			.forEach(
				one -> {
					for (Set<AppModule> e : source) {
						Iterables.addAll(collection, e);
					}
				}
			);
		AppModuleUser target = appModuleUser;
		target.setEnrolledModules(collection);
		return target;
	}

	private AppModuleUser addNewUserToModule(UUID userUuid, String moduleName) {
		if (
			appModuleUserRepository.existsByUserUUID(userUuid)
		) throw new NotFoundException("User is not assigned to the Module");
		AppModule appModule = moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
			moduleName,
			true
		);
		AppModuleUser appModuleUser = new AppModuleUser();
		appModuleUser.setUserUUID(userUuid);
		appModuleUser.setActive(true);
		appModuleUser.setCreatedAt(new Date());
		appModuleUser.getEnrolledModules().add(appModule);
		appModuleUserRepository.save(appModuleUser);
		return appModuleUserRepository.findByUserUUIDAndIsActive(userUuid, true);
	}
}
