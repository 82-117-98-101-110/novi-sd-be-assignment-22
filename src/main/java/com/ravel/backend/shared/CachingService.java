package com.ravel.backend.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@Component
@EnableScheduling
public class CachingService {

	@Autowired
	CacheManager cacheManager;

	public void evictAllCaches() {
		cacheManager
			.getCacheNames()
			.parallelStream()
			.forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

	@Scheduled(fixedRate = 60000)
	public void evictAllCachesAtIntervals() {
		evictAllCaches();
	}
}
