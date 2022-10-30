package com.ravel.backend.users.service;

import com.ravel.backend.users.dtos.ConnectionInvitesGetDto;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.model.Connection;
import java.util.List;

public interface ConnectionService {
	//Send Connection Invite
	Connection sendConnectionInvite(String toUserEmail);

	//Accept Connection Invite
	Connection acceptConnectionInvite(String fromUserEmail);

	//Decline Connection Invite
	Connection declineConnectionInvite(String fromUserEmail);

	//Remove Connection
	Connection removeConnection(String userEmail);

	//Get Invites for User
	List<ConnectionInvitesGetDto> getConnectionInvites();

	//Get Invites send by User
	List<UserDetailsGetDto> getConnections();
}
