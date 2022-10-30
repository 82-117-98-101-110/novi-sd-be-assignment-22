package com.ravel.backend.users.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConnectionId implements Serializable {

	@Column(name = "sender_uuid")
	private UUID senderUuid;

	@Column(name = "receiver_uuid")
	private UUID receiverUuid;

	public ConnectionId() {}

	public ConnectionId(UUID senderUuid, UUID receiverUuid) {
		this.senderUuid = senderUuid;
		this.receiverUuid = receiverUuid;
	}

	public UUID getSenderUuid() {
		return senderUuid;
	}

	public void setSenderUuid(UUID friendUuid) {
		this.senderUuid = friendUuid;
	}

	public UUID getReceiverUuid() {
		return receiverUuid;
	}

	public void setReceiverUuid(UUID friendOfUuid) {
		this.receiverUuid = friendOfUuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ConnectionId that = (ConnectionId) o;
		return (
			Objects.equals(senderUuid, that.senderUuid) &&
			Objects.equals(receiverUuid, that.receiverUuid)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(senderUuid, receiverUuid);
	}
}
