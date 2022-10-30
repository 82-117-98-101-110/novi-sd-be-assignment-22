package com.ravel.backend.modules.customAde;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AdeService {
	Ade createNewEntry(
		String username,
		Long countryCode,
		String countryName,
		String countryAlpha2,
		String countryAlpha3,
		String company
	);

	Ade getEntry(String username);

	Ade findEntry(String username);

	List<Ade> getEntries();

	List<Ade> getAllEntries();
}
