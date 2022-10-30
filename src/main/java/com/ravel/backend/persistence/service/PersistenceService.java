package com.ravel.backend.persistence.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.backend.persistence.dto.*;
import com.ravel.backend.persistence.model.PhotonRoom;
import com.ravel.backend.persistence.repository.PhotonRoomRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service
public class PersistenceService {
    private final PhotonRoomRepository photonRoomRepository;
    private PhotonRoomUserService photonRoomUserService;

    public DefaultResponse handleOnClose(ClosePost closePost) {
        DefaultResponse response = new DefaultResponse();
        response.setResultCode(0);
        if (photonRoomRepository.findByPhotonRoomIdIgnoreCase(closePost.getGameId()).isPresent()) {
            savePhotonRoomState(closePost.getState(), closePost.getGameId());
        } else {
            //TODO-return resultCode(1) because we should never create a new photonRoom with state on handleOnClose, it should already be stored during handleOnCreate
//            createNewPhotonRoom(closePost.getState(), closePost.getGameId());
            return response;
        }
        return response;
    }

    public CreateResponse handleOnCreate(CreatePost createPost) {
        CreateResponse response = new CreateResponse();
        response.setResultCode(0);

        if (photonRoomRepository.findByPhotonRoomIdIgnoreCase(createPost.getGameId()).isPresent()) {
            PhotonRoom photonRoom = photonRoomRepository.findByPhotonRoomIdIgnoreCasev2(createPost.getGameId());
            response.setState(photonRoom.getState());
            photonRoom.setRoomIsOnline(true);
            photonRoomRepository.save(photonRoom);

            photonRoomUserService.userEntersPhotonRoom(createPost.getActorNr(), false, UUID.fromString(createPost.getUserId().substring(0,36)), createPost.getGameId(), createPost.getUserId()); //TODO-take first part of string and convert to uuid

        } else {
            PhotonRoom photonRoom = createNewPhotonRoom(createPost);
            response.setState(photonRoom.getState());
            return response;
    }
        return response;
    }

    public DefaultResponse handleOnJoin(JoinPost joinPost){
        DefaultResponse response = new DefaultResponse();
        response.setResultCode(0);
        photonRoomUserService.userEntersPhotonRoom(joinPost.getActorNr(), false, UUID.fromString(joinPost.getUserId().substring(0,36)), joinPost.getGameId(), joinPost.getUserId());
        return response;
    }

    public DefaultResponse handleOnLeave(LeavePost leavePost) {
        DefaultResponse response = new DefaultResponse();
        response.setResultCode(0);
        photonRoomUserService.userLeavesPhotonRoom(leavePost.getActorNr(), leavePost.getUserId(),UUID.fromString(leavePost.getUserId().substring(0,36)), leavePost.getGameId(), leavePost.isInactive());
        return response;
    }

    private PhotonRoom savePhotonRoomState(JsonNode state, String gameId){
        PhotonRoom photonRoom = photonRoomRepository.findByPhotonRoomIdIgnoreCasev2(gameId);
        photonRoom.setState(state);
        photonRoom.setUpdatedAt(OffsetDateTime.now());
        photonRoom.setRoomIsOnline(false);
        photonRoomRepository.save(photonRoom);
        return null;
    }

    private PhotonRoom createNewPhotonRoom(CreatePost createPost){
        //TODO-need to validate actorNr==0? or what kind of error handling should be done?
        PhotonRoom newPhotonRoom = new PhotonRoom();
        newPhotonRoom.setPhotonRoomId(createPost.getGameId());
        newPhotonRoom.setPhotonAppId(createPost.getAppId());
        newPhotonRoom.setHostSessionUserId(createPost.getUserId());


        newPhotonRoom.setHostUserUuid(UUID.fromString(createPost.getUserId().substring(0,36)));
        newPhotonRoom.setCreated_at(OffsetDateTime.now());
        newPhotonRoom.setRoomIsOnline(true);
        String emptyState = "{}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(emptyState);
            newPhotonRoom.setState(node);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        photonRoomRepository.save(newPhotonRoom);
        photonRoomUserService.userEntersPhotonRoom(createPost.getActorNr(), true , UUID.fromString(createPost.getUserId().substring(0,36)), createPost.getGameId(), createPost.getUserId()); //TODO-take first part of string and convert to uuid
        return newPhotonRoom;
    }

}
