package com.ravel.backend.security.repository;

import com.ravel.backend.security.domain.AuthCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
	Optional<AuthCode> findByCodeIgnoreCaseAndIsActive(String code, boolean isActive);

	Optional<AuthCode> findByCodeAndIsActiveAndIsVerified(
		String code,
		boolean isActive,
		boolean isVerified
	);
}
