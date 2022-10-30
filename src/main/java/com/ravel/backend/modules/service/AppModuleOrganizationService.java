package com.ravel.backend.modules.service;

import com.ravel.backend.modules.model.AppModule;
import com.ravel.backend.modules.model.AppModuleOrganization;
import com.ravel.backend.modules.repository.AppModuleOrganizationRepository;
import com.ravel.backend.modules.repository.ModuleRepository;
import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.shared.exception.NotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppModuleOrganizationService {

	private final AppModuleOrganizationRepository appModuleOrganizationRepository;
	private final ModuleRepository moduleRepository;
	private OrganizationService organizationService;

	public AppModuleOrganization addModuleToOrganization(
		String organizationName,
		String moduleName
	) {
		UUID organizationId = organizationService.getOrganizationId(organizationName);
		if (!appModuleOrganizationRepository.existsByOrganizationId(organizationId)) {
			addNewOrgToModule(organizationId, moduleName);
			return null;
		}
		addExistingOrgToModule(organizationId, moduleName);
		return null;
	}

	public AppModuleOrganization getOrganizationModules(String organizationName) {
		return getModulesForOrganization(organizationName);
	}

	public Set<AppModule> getOrganizationModulesSet(String organizationName) {
		Set<AppModule> enrolledModules = getModulesForOrganization(organizationName)
			.getEnrolledModules();
		return enrolledModules;
	}

	public List<Set<AppModule>> getModuleSetForOrganizations(
		List<UUID> organizationIdList
	) {
		List<AppModuleOrganization> appModuleOrganizations = appModuleOrganizationRepository.findByOrganizationIdAndIsActive(
			organizationIdList
		);

		List<Set<AppModule>> listOfArrays = appModuleOrganizations
			.stream()
			.map(AppModuleOrganization::getEnrolledModules)
			.collect(Collectors.toList());
		return listOfArrays;
	}

	private AppModuleOrganization getModulesForOrganization(String organizationName) {
		UUID organizationId = organizationService.getOrganizationId(organizationName);
		AppModuleOrganization appModuleOrganization = appModuleOrganizationRepository
			.findByOrganizationId(organizationId)
			.orElseThrow(
				() ->
					new NotFoundException(
						"Organization has not been assigned to an Module"
					)
			);
		return appModuleOrganization;
	}

	private AppModuleOrganization addExistingOrgToModule(
		UUID organizationId,
		String moduleName
	) {
		AppModuleOrganization appModuleOrganization = appModuleOrganizationRepository
			.findByOrganizationId(organizationId)
			.orElseThrow(() -> new NotFoundException("Not Found"));
		AppModule appModule = moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
			moduleName,
			true
		);
		appModuleOrganization.enrolledModules.add(appModule);
		appModuleOrganizationRepository.save(appModuleOrganization);
		return null;
	}

	private AppModuleOrganization addNewOrgToModule(
		UUID organizationId,
		String moduleName
	) {
		AppModule appModule = moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
			moduleName,
			true
		);
		AppModuleOrganization appModuleOrganization = new AppModuleOrganization();
		appModuleOrganization.setOrganizationId(organizationId);
		appModuleOrganization.setActive(true);
		appModuleOrganization.setCreatedAt(OffsetDateTime.now());
		appModuleOrganization.getEnrolledModules().add(appModule);
		appModuleOrganizationRepository.save(appModuleOrganization);
		return null;
	}
}
