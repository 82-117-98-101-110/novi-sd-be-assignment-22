package com.ravel.backend.users.repository;

import com.ravel.backend.users.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u where upper(u.email) = upper(?1)")
	User findByEmailIgnoreCase(String email);

	@Query("select u from User u where upper(u.email) = upper(?1)")
	User findUserByEmail(String email);

	@Query("select u from User u where upper(u.email) = upper(?1)")
	Optional<User> findOptionalUserByEmail(String email);

	@Query("select u from User u where upper(u.email) = upper(?1) and u.isActive = ?2")
	Optional<User> findByEmailIgnoreCaseAndIsActive(String email, boolean isActive);

	@Query("select u from User u where u.email = ?1 and u.isActive = ?2")
	User findByEmailAndIsActive(String email, boolean isActive);

	@Query("select (count(u) > 0) from User u where u.email = ?1 and u.isActive = ?2")
	boolean existsByEmailAndIsActive(String email, boolean isActive);

	boolean existsByEmailIgnoreCaseAndIsActive(String email, boolean isActive);

	boolean existsByUserUUIDAndIsActive(UUID userUUID, boolean isActive);

	boolean existsByEmailIgnoreCase(String email);

	boolean existsByUserUUID(UUID userUUID);

	@Query("select u from User u where u.userUUID = ?1")
	User findByUserUUID(UUID userUUID);

	@Query("select u from User u where u.userUUID = ?1")
	Optional<User> findByUserUUIDOptional(UUID userUUID);

	@Query("select u from User u where u.userUUID = ?1 and u.isActive = ?2")
	Optional<User> findByUserUUIDAndIsActive(UUID userUUID, boolean isActive);

	@Query("select u from User u where u.email in :emails and u.isActive = true")
	List<User> findByEmails(@Param("emails") List<String> userListEmail);

	@Query("select o from User o where o.userUUID in :uuids and o.isActive = true")
	List<User> findByUuids(@Param("uuids") List<UUID> userListUuid);

	@Query(
		value = "SELECT * FROM user_account WHERE is_active = TRUE",
		nativeQuery = true
	)
	List<User> findAllActiveUsers();

	@Query(
		"select u from User u left join u.receivers senders where senders.sender.userUUID = ?1"
	)
	List<User> findBySenders_Id_SenderUuid(@Param("senderUuid") UUID senderUuid);

	@Query(
		"select u from User u left join u.senders receivers where receivers.receiver.userUUID = ?1"
	)
	List<User> findBySenders_Id_SenderUuidReceived(@Param("senderUuid") UUID senderUuid);
}
