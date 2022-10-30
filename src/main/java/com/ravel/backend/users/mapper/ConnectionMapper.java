package com.ravel.backend.users.mapper;

import com.ravel.backend.users.dtos.ConnectionInvitesGetDto;
import com.ravel.backend.users.dtos.ConnectionsGetDto;
import com.ravel.backend.users.model.Connection;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConnectionMapper {
	List<ConnectionsGetDto> connectionsToConnectionsGetDto(
		List<Connection> connectionList
	);

	List<ConnectionInvitesGetDto> connectionsToConnectionInvitesGetDto(
		List<Connection> connectionList
	);
}
