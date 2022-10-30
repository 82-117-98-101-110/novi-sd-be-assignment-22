package com.ravel.backend.modules.customWaterschap.userEntry;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface ModuleWaterschappenService {
	ModuleWaterschappen createNewEntry(
		String email,
		String waterschappenName,
		String kernProcessName
	);

	ModuleWaterschappen createNewEntryUuid(
		UUID userUuid,
		String waterschappenName,
		String kernProcesName
	);

	ModuleWaterschappen getEntry(String email);

	ModuleWaterschappen getEntryUuid(UUID userUuid);

	ModuleWaterschappen findEntry(String email);
	List<ModuleWaterschappen> getEntries();
	List<ModuleWaterschappen> getAllEntries();
}
