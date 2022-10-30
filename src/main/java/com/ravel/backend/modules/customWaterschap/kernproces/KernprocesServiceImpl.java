package com.ravel.backend.modules.customWaterschap.kernproces;

import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KernprocesServiceImpl implements KernprocesService {

	private final KernprocesRepository kernprocesRepository;

	@Override
	public Kernproces createKernproces(
		String kernProcesName,
		String kernProcessImageUrl
	) {
		Boolean existName = kernprocesRepository.existsByKernProcesName(kernProcesName);
		if (existName) {
			throw new AlreadyExistException(
				"Kernproces with name " + kernProcesName + " already exists"
			);
		}
		Kernproces kernProces = new Kernproces();
		kernProces.setKernProcesName(kernProcesName);
		kernProces.setKernProcesImageUrl(kernProcessImageUrl);
		kernProces.setCreatedAt(new Date());
		kernProces.setUpdatedAt(new Date());
		kernprocesRepository.save(kernProces);
		return kernProces;
	}

	@Override
	public Kernproces findKernproces(String kernProcesName) {
		if (
			!kernprocesRepository.existsByKernProcesName(kernProcesName)
		) throw new NotFoundException(
			"Entry for kernProces with " + kernProcesName + " does not exist"
		);
		return kernprocesRepository.findByKernProcesName(kernProcesName);
	}

	@Override
	public List<Kernproces> findAll() {
		return kernprocesRepository.findAll();
	}

	@Override
	public Kernproces updateKernproces(String kernProcesName, String kernProcesImageUrl) {
		return null;
	}
}
