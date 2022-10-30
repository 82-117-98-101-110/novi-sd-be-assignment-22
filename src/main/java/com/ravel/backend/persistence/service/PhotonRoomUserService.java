package com.ravel.backend.persistence.service;

import com.ravel.backend.persistence.model.PhotonRoomUser;
import com.ravel.backend.persistence.model.PhotonRoomUserId;
import com.ravel.backend.persistence.repository.PhotonRoomUserRepository;
import com.ravel.backend.shared.exception.NotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service
public class PhotonRoomUserService {

    private final PhotonRoomUserRepository photonRoomUserRepository;

    public void userEntersPhotonRoom(Integer actorNr,boolean isHost, UUID userUuid, String photonRoomId, String sessionUserId) {
        PhotonRoomUserId photonRoomUserId = new PhotonRoomUserId(sessionUserId, photonRoomId);

       PhotonRoomUser photonRoomUser = photonRoomUserRepository.findPhotonRoomUserById(photonRoomUserId);

        if(photonRoomUser == null){
            PhotonRoomUser newPhotonRoomUser = new PhotonRoomUser();
            newPhotonRoomUser.setId(photonRoomUserId);
            newPhotonRoomUser.setUserUuid(userUuid);
//            newPhotonRoomUser.setActorNr(actorNr);
            newPhotonRoomUser.setCreated_at(OffsetDateTime.now());
            newPhotonRoomUser.setLastJoined(OffsetDateTime.now());
            newPhotonRoomUser.setOnline(true);
//            newPhotonRoomUser.setSessionUserId(sessionUserId);
            newPhotonRoomUser.setInActive(true);
            newPhotonRoomUser.setHost(isHost);
            photonRoomUserRepository.save(newPhotonRoomUser);
        } else{
            photonRoomUser.setLastJoined(OffsetDateTime.now());
            photonRoomUser.setOnline(true);
//            photonRoomUser.setActorNr(actorNr);
//            photonRoomUser.setSessionUserId(sessionUserId);
            photonRoomUser.setInActive(true);
            photonRoomUser.setHost(isHost);
            photonRoomUserRepository.save(photonRoomUser);
        }

    }

    //TODO - possible improvement would be to not delete but set is active to false, so we can re-use sessionUserId?
    public void userLeavesPhotonRoom(Integer actorNr, String sessionUserId, UUID userUuid, String photonRoomId, boolean isInactive){
        PhotonRoomUserId photonRoomUserId = new PhotonRoomUserId(sessionUserId, photonRoomId);
        PhotonRoomUser photonRoomUser = photonRoomUserRepository.findPhotonRoomUserById(photonRoomUserId);
        if(photonRoomUser != null){
            if (!isInactive) {
                photonRoomUserRepository.deleteById(photonRoomUserId);
            } else {
            photonRoomUser.setOnline(false);
            photonRoomUser.setInActive(isInactive);
            photonRoomUserRepository.save(photonRoomUser);
        }} else {
            throw new NotFoundException("PhotonRoomUser not found when leaving room");
        }
    }

    //TODO - possible improvement would be to first get isActive==true and then if null, return isActive==false
    public PhotonRoomUser getPhotonRoomUser(UUID userUuid ,String photonRoomId) {
        List<PhotonRoomUser> photonRoomUserList = photonRoomUserRepository.findById_PhotonRoomIdAndUserUuidAndIsInActive(photonRoomId, userUuid);
        if(photonRoomUserList.isEmpty()){
            return null;
        }else{
            PhotonRoomUser photonRoomUser = photonRoomUserList.get(0);
            return photonRoomUser;
        }
    }

    public List<PhotonRoomUser> getOnlinePhotonRoomUsers(List<String> photonRoomIds) {
        List<PhotonRoomUser> photonRoomUsers = photonRoomUserRepository.findById_PhotonRoomIdAndIsOnline(photonRoomIds);
        return photonRoomUsers;
    }




}
