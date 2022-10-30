package com.ravel.backend.security.service;

import com.ravel.backend.users.model.User;
import com.ravel.backend.users.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	private LoginAttemptService loginAttemptService;

	@Autowired
	public UserDetailsServiceImpl(
		UserRepository userRepository,
		LoginAttemptService loginAttemptService
	) {
		this.userRepository = userRepository;
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws BadCredentialsException {
		if (
			!userRepository.existsByEmailIgnoreCase(email)
		) throw new BadCredentialsException("Cannot authenticate with credentials");
		User user = userRepository.findUserByEmail(email);
		validateLoginAttempt(user);
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
		return userDetailsImpl;
	}

	private void validateLoginAttempt(User user) {
		if (user.isNotLocked()) {
			if (loginAttemptService.hasExceededMaxAttempts(user.getEmail())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
		}
	}
}
