package com.ravel.backend.users.repository;

import com.ravel.backend.users.model.ResetPassword;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
	Optional<ResetPassword> findByToken(String token);

	@Transactional
	@Modifying
	@Query("UPDATE ResetPassword u " + "SET u.confirmedAt = ?2 " + "WHERE u.token = ?1")
	int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
