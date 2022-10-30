package com.ravel.backend.modules.customWaterschap.userEntry;

import com.ravel.backend.modules.customWaterschap.kernproces.Kernproces;
import com.ravel.backend.modules.customWaterschap.kernproces.KernprocesRepository;
import com.ravel.backend.modules.customWaterschap.waterschap.Waterschap;
import com.ravel.backend.modules.customWaterschap.waterschap.WaterschapRepository;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.service.UserService;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleWaterschappenServiceImpl implements ModuleWaterschappenService {

	private ModuleWaterschappenRepository moduleWaterschappenRepository;
	private KernprocesRepository kernprocesRepository;
	private WaterschapRepository waterschapRepository;
	private UserService userService;

	@Override
	public ModuleWaterschappen createNewEntry(
		String userEmail,
		String waterschappenName,
		String kernProcesName
	) {
		UUID userUuid = userService.getUserUuidFromActiveUser(userEmail);
		if (moduleWaterschappenRepository.existsByUserUuid(userUuid)) {
			Kernproces kernproces = kernprocesRepository.findByKernProcesName(
				kernProcesName
			);
			Waterschap waterschap = waterschapRepository.findByWaterschapName(
				waterschappenName
			);
			ModuleWaterschappen currentEntry = moduleWaterschappenRepository.findByUserUuid(
				userUuid
			);
			currentEntry.setWaterschap(waterschap);
			currentEntry.setKernproces(kernproces);
			currentEntry.setUpdatedAt(new Date());
			moduleWaterschappenRepository.save(currentEntry);
			return currentEntry;
		} else {
			Kernproces kernproces = kernprocesRepository.findByKernProcesName(
				kernProcesName
			);
			Waterschap waterschap = waterschapRepository.findByWaterschapName(
				waterschappenName
			);
			ModuleWaterschappen moduleWaterschappen = new ModuleWaterschappen();
			moduleWaterschappen.setUserUuid(userUuid);
			moduleWaterschappen.setCreatedAt(new Date());
			moduleWaterschappen.setUpdatedAt(new Date());
			moduleWaterschappen.setActive(true);
			moduleWaterschappen.setKernproces(kernproces);
			moduleWaterschappen.setWaterschap(waterschap);
			moduleWaterschappenRepository.save(moduleWaterschappen);
			return moduleWaterschappen;
		}
	}

	@Override
	public ModuleWaterschappen createNewEntryUuid(
		UUID userUuid,
		String waterschappenName,
		String kernProcesName
	) {
		if (moduleWaterschappenRepository.existsByUserUuid(userUuid)) {
			Kernproces kernproces = kernprocesRepository.findByKernProcesName(
				kernProcesName
			);
			Waterschap waterschap = waterschapRepository.findByWaterschapName(
				waterschappenName
			);
			ModuleWaterschappen currentEntry = moduleWaterschappenRepository.findByUserUuid(
				userUuid
			);
			currentEntry.setWaterschap(waterschap);
			currentEntry.setKernproces(kernproces);
			currentEntry.setUpdatedAt(new Date());
			moduleWaterschappenRepository.save(currentEntry);
			return currentEntry;
		} else {
			Kernproces kernproces = kernprocesRepository.findByKernProcesName(
				kernProcesName
			);
			Waterschap waterschap = waterschapRepository.findByWaterschapName(
				waterschappenName
			);
			ModuleWaterschappen moduleWaterschappen = new ModuleWaterschappen();
			moduleWaterschappen.setUserUuid(userUuid);
			moduleWaterschappen.setCreatedAt(new Date());
			moduleWaterschappen.setUpdatedAt(new Date());
			moduleWaterschappen.setActive(true);
			moduleWaterschappen.setKernproces(kernproces);
			moduleWaterschappen.setWaterschap(waterschap);
			moduleWaterschappenRepository.save(moduleWaterschappen);
			return moduleWaterschappen;
		}
	}

	@Override
	public ModuleWaterschappen getEntry(String userEmail) {
		UUID userUuid = userService.getUserUuidFromActiveUser(userEmail);
		if (
			!moduleWaterschappenRepository.existsByUserUuid(userUuid)
		) throw new NotFoundException(
			"Entry for user with " + userEmail + " does not exist"
		);
		return moduleWaterschappenRepository.findByUserUuid(userUuid);
	}

	@Override
	public ModuleWaterschappen getEntryUuid(UUID userUuid) {
		if (
			!moduleWaterschappenRepository.existsByUserUuid(userUuid)
		) throw new NotFoundException(
			"Entry for user with " + userUuid + " does not exist"
		);
		return moduleWaterschappenRepository.findByUserUuid(userUuid);
	}

	@Override
	public ModuleWaterschappen findEntry(String username) {
		return moduleWaterschappenRepository.findModuleWaterschappenByUsername(username);
	}

	@Override
	public List<ModuleWaterschappen> getEntries() {
		return moduleWaterschappenRepository.getEntries();
	}

	public List<ModuleWaterschappen> getAllEntries() {
		return moduleWaterschappenRepository.findAll();
	}
}
