package com.ravel.backend.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.OffsetDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "connection")
public class Connection {

	@JsonIgnore
	@EmbeddedId
	private ConnectionId id;

	@MapsId("senderUuid")
	@ManyToOne(cascade = { CascadeType.DETACH })
	@JoinColumn(name = "sender_user_uuid")
	private User sender;

	@MapsId("receiverUuid")
	@ManyToOne(cascade = { CascadeType.DETACH })
	@JoinColumn(name = "receiver_user_uuid")
	private User receiver;

	@Column(name = "invited_at")
	private OffsetDateTime invitedAt;

	@Column(name = "accepted_at")
	private OffsetDateTime acceptedAt;

	@Column(name = "declined_at")
	private OffsetDateTime declinedAt;

	@Column(name = "invite_open")
	private boolean inviteOpen;
}
