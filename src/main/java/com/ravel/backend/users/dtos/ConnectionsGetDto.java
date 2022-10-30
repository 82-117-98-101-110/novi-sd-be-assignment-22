package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "ConnectionsGetDto", description = "")
public class ConnectionsGetDto {

	private OffsetDateTime invitedAt;
	private OffsetDateTime acceptedAt;
	private boolean inviteOpen;
	private UserDetailsGetDto receiver;
}
