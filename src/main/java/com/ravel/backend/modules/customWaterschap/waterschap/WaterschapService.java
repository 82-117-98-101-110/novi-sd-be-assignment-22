package com.ravel.backend.modules.customWaterschap.waterschap;

import java.util.List;

public interface WaterschapService {
	Waterschap createWaterschap(String waterschapName, String waterschapImageUrl);

	Waterschap findWaterschap(String waterschapName);

	List<Waterschap> findAll();

	Waterschap updateWaterschap(String waterschapName, String waterschapImageUrl);
}
