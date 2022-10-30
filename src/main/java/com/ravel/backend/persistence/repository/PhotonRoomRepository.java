package com.ravel.backend.persistence.repository;

import com.ravel.backend.persistence.model.PhotonRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotonRoomRepository extends JpaRepository<PhotonRoom, Long> {
    @Query("select p from PhotonRoom p where upper(p.photonRoomId) = upper(?1)")
    Optional<PhotonRoom> findByPhotonRoomIdIgnoreCase(String photonRoomId);

    @Query("select p from PhotonRoom p where upper(p.photonRoomId) = upper(?1)")
    PhotonRoom findByPhotonRoomIdIgnoreCasev2(String photonRoomId);

    @Query("select (count(p) > 0) from PhotonRoom p where upper(p.photonRoomId) = upper(?1)")
    boolean existsByPhotonRoomIdIgnoreCase(String photonRoomId);

    @Query("select p from PhotonRoom p where p.photonRoomId in :photonRoomIds and p.roomIsOnline = true")
    List<PhotonRoom> findByPhotonRoomIdAndRoomIsOnline(@Param("photonRoomIds")List<String> photonRoomId);










}
