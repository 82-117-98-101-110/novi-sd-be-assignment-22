package com.ravel.backend.modules.customWaterschap.waterschap;

import com.ravel.backend.modules.customWaterschap.kernproces.KernprocesRepository;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WaterschapServiceImpl implements WaterschapService {

	private final WaterschapRepository waterschapRepository;
	private final KernprocesRepository kernprocesRepository;

	@Override
	public Waterschap createWaterschap(String waterschapName, String waterschapImageUrl) {
		if (
			waterschapRepository.existsByWaterschapName(waterschapName)
		) throw new AlreadyExistException(
			"Entry for waterschap with " + waterschapName + " already exist"
		);
		Waterschap waterschap = new Waterschap();
		waterschap.setWaterschapName(waterschapName);
		waterschap.setWaterschapImageUrl(waterschapImageUrl);
		waterschap.setCreatedAt(new Date());
		waterschap.setUpdatedAt(new Date());
		return waterschapRepository.save(waterschap);
	}

	@Override
	public Waterschap findWaterschap(String waterschapName) {
		if (
			!waterschapRepository.existsByWaterschapName(waterschapName)
		) throw new NotFoundException(
			"Entry for waterschap with " + waterschapName + " does not exist"
		);
		return waterschapRepository.findWaterschapByWaterschapName(waterschapName);
	}

	@Override
	public List<Waterschap> findAll() {
		return waterschapRepository.findAll();
	}

	@Override
	public Waterschap updateWaterschap(String waterschapName, String waterschapImageUrl) {
		return null;
	}
}
