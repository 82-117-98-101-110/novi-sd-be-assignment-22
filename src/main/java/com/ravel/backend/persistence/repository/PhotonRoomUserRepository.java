package com.ravel.backend.persistence.repository;

import com.ravel.backend.persistence.model.PhotonRoomUser;
import com.ravel.backend.persistence.model.PhotonRoomUserId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotonRoomUserRepository extends JpaRepository<PhotonRoomUser, PhotonRoomUserId> {


    @Override
    Optional<PhotonRoomUser> findById(PhotonRoomUserId photonRoomUserId);

    @Query("select p from PhotonRoomUser p where p.id = ?1")
    PhotonRoomUser findPhotonRoomUserById(PhotonRoomUserId id);

    @Query("select p from PhotonRoomUser p where p.id.photonRoomId = ?1 and p.userUuid = ?2 and p.isOnline = false and p.isInActive = true and p.isHost = false")
    List<PhotonRoomUser> findById_PhotonRoomIdAndUserUuidAndIsInActive(String photonRoomId, UUID userUuid);





    @Query("select p from PhotonRoomUser p where p.id.photonRoomId in :photonRoomIds and p.isOnline = true")
    List<PhotonRoomUser> findById_PhotonRoomIdAndIsOnline(@Param("photonRoomIds") List<String> photonRoomId);







}