package com.ravel.backend.userFiles.repository;

import com.ravel.backend.userFiles.model.UserFiles;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TODO write tests
@Repository
public interface UserFileRepository extends JpaRepository<UserFiles, UUID> {
	boolean existsByIdAndIsPrivate(UUID id, boolean isPrivate);

	boolean existsByUserUuidAndId(UUID userUuid, UUID id);

	@Transactional
	Stream<UserFiles> findByUserUuid(UUID userUuid);

	Optional<UserFiles> findByIdAndUserUuid(UUID id, UUID userUuid);

	@Transactional
	long deleteByUserUuidAndId(UUID userUuid, UUID id);
}
