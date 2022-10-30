package com.ravel.backend.modules.service;

import com.ravel.backend.modules.dto.CreateModuleDto;
import com.ravel.backend.modules.model.AppModule;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ModuleService {
	AppModule createActiveModule(CreateModuleDto createModuleDto);

	AppModule uploadModuleImage(String moduleName, MultipartFile moduleImage);

	AppModule getModule(String moduleName);
	AppModule findActiveModule(String moduleName);
	List<AppModule> findActiveModules();
	void deleteModule(String moduleName);
	List<AppModule> findAllModules();
	AppModule deactivateModule(String moduleName, Boolean bool);
}
