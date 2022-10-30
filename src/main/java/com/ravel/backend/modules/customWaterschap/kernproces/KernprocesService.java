package com.ravel.backend.modules.customWaterschap.kernproces;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface KernprocesService {
	Kernproces createKernproces(String kernProcessName, String kernProcessImageUrl);

	Kernproces findKernproces(String moduleName);

	List<Kernproces> findAll();

	Kernproces updateKernproces(String kernProcessName, String kernProcessImageUrl);
}
