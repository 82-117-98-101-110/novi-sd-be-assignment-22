package com.ravel.backend.modules.service;

import com.ravel.backend.modules.dto.CreateModuleDto;
import com.ravel.backend.modules.model.AppModule;
import com.ravel.backend.modules.repository.ModuleRepository;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {

	private final ModuleRepository moduleRepository;

	private ModuleAwsS3Service moduleAwsS3Service;

	@Override
	public AppModule createActiveModule(CreateModuleDto createModuleDto) {
		if (
			moduleRepository.existsByModuleNameIgnoreCase(createModuleDto.getModuleName())
		) throw new AlreadyExistException(
			"Module with name " + createModuleDto.getModuleName() + " already exist"
		);
		AppModule appModule = new AppModule();
		appModule.setModuleName(createModuleDto.getModuleName());
		appModule.setDescription(createModuleDto.getDescription());
		appModule.setCreatedAt(new Date());
		appModule.setActive(true);

		return moduleRepository.save(appModule);
	}

	@Override
	public AppModule uploadModuleImage(String moduleName, MultipartFile moduleImage) {
		if (
			!moduleRepository.existsByModuleNameIgnoreCase(moduleName)
		) throw new AlreadyExistException(
			"Module with name " + moduleName + " does not exist"
		);
		AppModule appModule = moduleRepository.findByModuleNameIgnoreCaseAndIsActive(
			moduleName,
			true
		);
		String moduleImageUrl = moduleAwsS3Service.createModuleImageUpload(
			appModule.getModuleName(),
			appModule.getModuleImageUrl(),
			moduleImage
		);
		appModule.setModuleImageUrl(moduleImageUrl);
		return moduleRepository.save(appModule);
	}

	/**
	 * NOTE : this method will get the details of a module when providing a name. It will get both active as non-active modules.
	 */
	@Override
	public AppModule getModule(String moduleName) {
		if (
			!moduleRepository.existsByModuleNameIgnoreCase(moduleName)
		) throw new NotFoundException(
			"Module with name " + moduleName + " does not exist"
		);
		return moduleRepository.findByModuleNameIgnoreCase(moduleName);
	}

	@Override
	public AppModule findActiveModule(String moduleName) {
		if (
			!moduleRepository.existsByModuleNameIgnoreCase(moduleName)
		) throw new NotFoundException(
			"Module with name " + moduleName + " does not exist"
		);
		if (
			!moduleRepository.existsByModuleNameIgnoreCaseAndIsActive(moduleName, true)
		) throw new NotFoundException(
			"Module with name " + moduleName + " is not active"
		);
		return moduleRepository.findByModuleNameIgnoreCaseAndIsActive(moduleName, true);
	}

	@Override
	public List<AppModule> findActiveModules() {
		return moduleRepository.findActiveModules();
	}

	public List<AppModule> findAllModules() {
		return moduleRepository.findAll();
	}

	@Override
	public AppModule deactivateModule(String moduleName, Boolean bool) {
		if (
			!moduleRepository.existsByModuleNameIgnoreCase(moduleName)
		) throw new NotFoundException(
			"Module with name " + moduleName + " does not exist"
		);
		AppModule deActivateModule = moduleRepository.findByModuleNameIgnoreCase(
			moduleName
		);
		deActivateModule.setActive(bool);
		moduleRepository.save(deActivateModule);
		return deActivateModule;
	}

	@Override
	public void deleteModule(String moduleName) {
		if (
			!moduleRepository.existsByModuleNameIgnoreCase(moduleName)
		) throw new NotFoundException(
			"Module with name " + moduleName + " does not exist"
		);
		AppModule appModule = moduleRepository.findByModuleNameIgnoreCase(moduleName);
		moduleRepository.deleteById(appModule.getModuleId());
	}
}
