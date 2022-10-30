package com.ravel.backend.modules.customAde;

import com.ravel.backend.shared.exception.NotFoundException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdeServiceImpl implements AdeService {

	private AdeRepository adeRepository;

	@Autowired
	public AdeServiceImpl(AdeRepository adeRepository) {
		this.adeRepository = adeRepository;
	}

	@Override
	public Ade createNewEntry(
		String username,
		Long countryCode,
		String countryName,
		String countryAlpha2,
		String countryAlpha3,
		String company
	) {
		if (adeRepository.existsByUsernameIgnoreCase(username)) {
			Ade currentEntry = adeRepository.findByUsernameIgnoreCaseAndIsActive(
				username,
				true
			);
			currentEntry.setCountryCode(countryCode);
			currentEntry.setCountryAlpha2(countryAlpha2);
			currentEntry.setCountryAlpha3(countryAlpha3);
			currentEntry.setCountryName(countryName);
			currentEntry.setCompany(company);
			currentEntry.setUpdatedAt(new Date());
			adeRepository.save(currentEntry);
			return currentEntry;
		} else {
			Ade ade = new Ade();
			ade.setUsername(username);
			ade.setCompany(company);
			ade.setCountryCode(countryCode);
			ade.setCountryAlpha3(countryName);
			ade.setCountryAlpha2(countryAlpha2);
			ade.setCountryAlpha3(countryAlpha3);
			ade.setCreatedAt(new Date());
			ade.setUpdatedAt(new Date());
			ade.setActive(true);
			adeRepository.save(ade);
			return ade;
		}
	}

	@Override
	public Ade getEntry(String username) {
		if (
			!adeRepository.existsByUsernameIgnoreCase(username)
		) throw new NotFoundException(
			"Entry for user with " + username + " does not exist"
		);
		return adeRepository.findByUsernameIgnoreCaseAndIsActive(username, true);
	}

	@Override
	public Ade findEntry(String username) {
		return adeRepository.findByUsernameIgnoreCaseAndIsActive(username, true);
	}

	@Override
	public List<Ade> getEntries() {
		return adeRepository.getEntries();
	}

	public List<Ade> getAllEntries() {
		return adeRepository.findAll();
	}
}
