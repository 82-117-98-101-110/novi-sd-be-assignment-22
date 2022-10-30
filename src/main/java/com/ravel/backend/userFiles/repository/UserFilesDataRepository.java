package com.ravel.backend.userFiles.repository;

import com.ravel.backend.userFiles.model.UserFilesData;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFilesDataRepository extends JpaRepository<UserFilesData, UUID> {
	@Override
	@Cacheable("userFilesData")
	Optional<UserFilesData> findById(UUID uuid);

	@Override
	@Transactional
	void deleteById(UUID uuid);
}
