package com.ravel.backend.persistence.service;

import com.ravel.backend.persistence.model.PhotonRoom;
import com.ravel.backend.persistence.model.PhotonRoomUser;
import com.ravel.backend.persistence.repository.PhotonRoomRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service
public class PhotonRoomService {
    private final PhotonRoomRepository photonRoomRepository;

    private PhotonRoomUserService photonRoomUserService;

    public PhotonRoom findByPhotonRoomId (String photonRoomId) {
        PhotonRoom photonRoom = photonRoomRepository.findByPhotonRoomIdIgnoreCasev2(photonRoomId);
        return photonRoom;
    }

    public List<PhotonRoom> getOnlineRooms(List<String> roomIds) {
        List<PhotonRoom> photonRooms = photonRoomRepository.findByPhotonRoomIdAndRoomIsOnline(roomIds);
        return photonRooms;
    }

}
