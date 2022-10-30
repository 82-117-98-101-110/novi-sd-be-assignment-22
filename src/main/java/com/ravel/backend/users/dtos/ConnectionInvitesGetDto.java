package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(
	name = "ConnectionInvitesGetDto",
	description = "Model to request the send connection invites"
)
public class ConnectionInvitesGetDto {

	private OffsetDateTime invitedAt;
	private UserDetailsGetDto sender;
}
