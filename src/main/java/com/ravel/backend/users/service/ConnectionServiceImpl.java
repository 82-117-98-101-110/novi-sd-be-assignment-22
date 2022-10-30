package com.ravel.backend.users.service;

import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.ConnectionException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.dtos.ConnectionInvitesGetDto;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.mapper.ConnectionMapper;
import com.ravel.backend.users.mapper.UserMapper;
import com.ravel.backend.users.model.Connection;
import com.ravel.backend.users.model.ConnectionId;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.repository.ConnectionRepository;
import com.ravel.backend.users.repository.UserRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Transactional
@Service
@AllArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

	private final ConnectionRepository connectionRepository;
	private UserService userService;
	private ConnectionMapper connectionMapper;
	private final UserRepository userRepository;
	private UserMapper userMapper;
	private IAuthenticationFacade authenticationFacade;

	@Override
	public Connection sendConnectionInvite(String toUserEmail) {
		Authentication authentication = authenticationFacade.getAuthentication();
		User fromUser = userService.getUserByUUIDAndIsActive(
			UUID.fromString(authentication.getPrincipal().toString())
		);
		User toUser = userService.getUserByEmailAndIsActive(toUserEmail);
		if (fromUser == toUser) {
			throw new ConnectionException("User cannot invite itself");
		}
		ConnectionId id = generateConnectionId(
			fromUser.getUserUUID(),
			toUser.getUserUUID()
		);
		if (connectionExists(id)) {
			throw new AlreadyExistException("Users are already connected to each other");
		}
		Connection newConnectionInvite = newConnectionInvite(id, fromUser, toUser);
		return newConnectionInvite;
	}

	@Override
	public List<UserDetailsGetDto> getConnections() {
		Authentication authentication = authenticationFacade.getAuthentication();
		UUID uuid = UUID.fromString(authentication.getPrincipal().toString());
		List<User> userListSend = userRepository.findBySenders_Id_SenderUuid(uuid);
		List<User> userListReceived = userRepository.findBySenders_Id_SenderUuidReceived(
			uuid
		);
		List<User> combinedList = Stream
			.of(userListSend, userListReceived)
			.flatMap(x -> x.stream())
			.collect(Collectors.toList());
		List<UserDetailsGetDto> connectionUserDetailList = userMapper.userToUserDetailsGetDtoList(
			combinedList
		);
		return connectionUserDetailList;
	}

	@Override
	public List<ConnectionInvitesGetDto> getConnectionInvites() {
		Authentication authentication = authenticationFacade.getAuthentication();
		UUID uuid = UUID.fromString(authentication.getPrincipal().toString());

		User toUser = userService.getUserByUUIDAndIsActive(
			UUID.fromString(authentication.getPrincipal().toString())
		);
		List<ConnectionInvitesGetDto> connectionsGetDtoList = createGetConnectionInviteList(
			toUser.getEmail()
		);
		return connectionsGetDtoList;
	}

	@Override
	public Connection acceptConnectionInvite(String toUserEmail) {
		Authentication authentication = authenticationFacade.getAuthentication();
		User toUser = userService.getUserByUUIDAndIsActive(
			UUID.fromString(authentication.getPrincipal().toString())
		);
		User fromUser = userService.getUserByEmailAndIsActive(toUserEmail);
		if (fromUser == toUser) {
			throw new ConnectionException("User cannot be connected to itself");
		}
		ConnectionId id = generateConnectionId(
			fromUser.getUserUUID(),
			toUser.getUserUUID()
		);
		if (!connectionExists(id)) {
			throw new NotFoundException("Invite is not valid");
		}
		Connection acceptConnectionInvite = connectionRepository.findById(id);
		acceptConnectionInvite.setAcceptedAt(OffsetDateTime.now());
		acceptConnectionInvite.setInviteOpen(false);
		connectionRepository.save(acceptConnectionInvite);
		return null;
	}

	@Override
	public Connection declineConnectionInvite(String fromUserEmail) {
		Authentication authentication = authenticationFacade.getAuthentication();
		User toUser = userService.getUserByUUIDAndIsActive(
			UUID.fromString(authentication.getPrincipal().toString())
		);
		User fromUser = userService.getUserByEmailAndIsActive(fromUserEmail);
		if (fromUser == toUser) {
			throw new ConnectionException("Cannot unlink connection from itself");
		}
		ConnectionId id = generateConnectionId(
			fromUser.getUserUUID(),
			toUser.getUserUUID()
		);
		deleteConnection(id);
		return null;
	}

	@Override
	public Connection removeConnection(String UserEmail) {
		Authentication authentication = authenticationFacade.getAuthentication();
		User toUser = userService.getUserByUUIDAndIsActive(
			UUID.fromString(authentication.getPrincipal().toString())
		);
		User fromUser = userService.getUserByEmailAndIsActive(UserEmail);
		if (fromUser == toUser) {
			throw new ConnectionException("Cannot unlink connection from itself");
		}
		ConnectionId id = generateConnectionId(
			fromUser.getUserUUID(),
			toUser.getUserUUID()
		);
		deleteConnection(id);
		return null;
	}

	private void deleteConnection(ConnectionId id) {
		if (!connectionExists(id)) {
			throw new NotFoundException("Connection is not existing");
		}
		connectionRepository.deleteById(id);
	}

	private List<ConnectionInvitesGetDto> createGetConnectionInviteList(
		String userEmail
	) {
		if (!userService.existsByEmailAndIsActive(userEmail)) {
			throw new NotFoundException("User not found");
		}
		List<Connection> connectionList = connectionRepository.findByReceiver_EmailAndInviteOpen(
			userEmail
		);
		List<ConnectionInvitesGetDto> connectionInvitesGetDtoList = connectionMapper.connectionsToConnectionInvitesGetDto(
			connectionList
		);
		return connectionInvitesGetDtoList;
	}

	private Connection newConnectionInvite(ConnectionId id, User fromUser, User toUser) {
		Connection newConnectionInvite = new Connection();
		newConnectionInvite.setId(id);
		newConnectionInvite.setInvitedAt(OffsetDateTime.now());
		newConnectionInvite.setInviteOpen(true);
		newConnectionInvite.setReceiver(toUser);
		newConnectionInvite.setSender(fromUser);
		connectionRepository.save(newConnectionInvite);
		return newConnectionInvite;
	}

	private ConnectionId generateConnectionId(UUID senderUuid, UUID recieverUuid) {
		ConnectionId id = new ConnectionId(senderUuid, recieverUuid);
		return id;
	}

	private boolean connectionExists(ConnectionId id) {
		boolean existing = connectionRepository.existsById(id);
		return existing;
	}
}
