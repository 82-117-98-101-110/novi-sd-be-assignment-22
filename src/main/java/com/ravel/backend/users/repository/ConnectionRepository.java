package com.ravel.backend.users.repository;

import com.ravel.backend.users.model.Connection;
import com.ravel.backend.users.model.ConnectionId;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
	boolean existsById(ConnectionId aLong);

	@Query(
		"select c from Connection c where c.sender.email = ?1 and c.inviteOpen = false"
	)
	List<Connection> findBySender_Email(String email);

	@Query(
		"select c from Connection c where c.receiver.email = ?1 and c.inviteOpen = false"
	)
	List<Connection> findByReceiver_Email(String email);

	@Query(
		"select c from Connection c where c.receiver.email = ?1 and c.inviteOpen = true"
	)
	List<Connection> findByReceiver_EmailAndInviteOpen(String email);

	Connection findById(ConnectionId aLong);

	@Transactional
	void deleteById(ConnectionId aLong);
}
