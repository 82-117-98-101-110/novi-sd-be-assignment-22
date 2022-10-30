package com.ravel.backend.persistence.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PhotonRoomUserId implements Serializable {

    @Column(name = "session_user_id", nullable = false)
    private String sessionUserId;

    @Column(name = "photon_room_id", nullable = false)
    private String photonRoomId;

    public PhotonRoomUserId(String sessionUserId, String photonRoomId) {
        this.sessionUserId = sessionUserId;
        this.photonRoomId = photonRoomId;
    }

    public PhotonRoomUserId() {
    }

    public String getSessionUserId() {
        return sessionUserId;
    }

    public void setSessionUserId(String userId) {
        this.sessionUserId = userId;
    }

    public String getPhotonRoomId() {
        return photonRoomId;
    }

    public void setPhotonRoomId(String gameId) {
        this.photonRoomId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotonRoomUserId that = (PhotonRoomUserId) o;
        return Objects.equals(sessionUserId, that.sessionUserId) && Objects.equals(photonRoomId, that.photonRoomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionUserId, photonRoomId);
    }
}