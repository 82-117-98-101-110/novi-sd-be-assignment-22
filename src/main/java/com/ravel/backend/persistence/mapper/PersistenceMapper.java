package com.ravel.backend.persistence.mapper;

import com.ravel.backend.persistence.dto.PhotonRoomUserDto;
import com.ravel.backend.persistence.model.PhotonRoomUser;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {
    @Mapping(source = "idSessionUserId", target = "id.sessionUserId")
    @Mapping(source = "idPhotonRoomId", target = "id.photonRoomId")
    PhotonRoomUser photonRoomUserDtoToPhotonRoomUser(PhotonRoomUserDto photonRoomUserDto);

    @InheritInverseConfiguration(name = "photonRoomUserDtoToPhotonRoomUser")
    PhotonRoomUserDto photonRoomUserToPhotonRoomUserDto(PhotonRoomUser photonRoomUser);

    @InheritInverseConfiguration(name = "photonRoomUserDtoToPhotonRoomUser")
    List<PhotonRoomUserDto> photonRoomUserToPhotonRoomUserDtoList(List<PhotonRoomUser> photonRoomUser);

    @InheritConfiguration(name = "photonRoomUserDtoToPhotonRoomUser")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePhotonRoomUserFromPhotonRoomUserDto(PhotonRoomUserDto photonRoomUserDto, @MappingTarget PhotonRoomUser photonRoomUser);


}
