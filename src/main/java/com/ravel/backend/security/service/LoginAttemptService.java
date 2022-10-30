package com.ravel.backend.security.service;

import static java.util.concurrent.TimeUnit.MINUTES;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {

	private static final int maximum_number_of_attempts = 5;
	private static final int attempt_increment = 1;
	private LoadingCache<String, Integer> loginAttemptCache;

	public LoginAttemptService() {
		super();
		loginAttemptCache =
			CacheBuilder
				.newBuilder()
				.expireAfterWrite(15, MINUTES)
				.maximumSize(100)
				.build(
					new CacheLoader<String, Integer>() {
						public Integer load(String key) {
							return 0;
						}
					}
				);
	}

	public void evictUserFromLoginAttemptCache(String username) {
		loginAttemptCache.invalidate(username);
	}

	public void addUserToLoginAttemptCache(String username) {
		int attempts = 0;
		try {
			attempts = attempt_increment + loginAttemptCache.get(username);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		loginAttemptCache.put(username, attempts);
	}

	public boolean hasExceededMaxAttempts(String username) {
		try {
			return (loginAttemptCache.get(username) >= maximum_number_of_attempts);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
