package com.ravel.backend.spacePro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ravel.backend.spacePro.dto.PortalGetDto;
import com.ravel.backend.spacePro.dto.PortalPostDto;
import com.ravel.backend.spacePro.dto.PortalUpdateDto;
import com.ravel.backend.spacePro.mapper.PortalMapper;
import com.ravel.backend.spacePro.mapper.SpaceProMapper;
import com.ravel.backend.spacePro.model.Portal;
import com.ravel.backend.spacePro.model.SpacePro;
import com.ravel.backend.spacePro.repository.PortalRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service
public class PortalService {

    private final PortalRepository portalRepository;
    private PortalMapper portalMapper;

    public List<Portal> getPortal(){
        List<Portal> portalList = portalRepository.findAll();
       return portalList;
    }

    public Portal createPortal(PortalPostDto portalPostDto, SpacePro spacePro, SpacePro spaceProDestination){
        Portal newPortal = Portal.builder()
                .created_at(OffsetDateTime.now())
                .isActive(true)
                .spacePro(spacePro)
                .destinationSpacePro(spaceProDestination)
                .destinationSpace(portalPostDto.getDestinationSpace())
                .metadata(portalPostDto.getMetadata())
                .portalUuid(UUID.randomUUID())
                .build();
        portalRepository.save(newPortal);
        return newPortal;
    }

    public void deletePortal(UUID portalUuid) {
        portalRepository.deleteByPortalUuid(portalUuid);
    }

    public void updatePortal(PortalUpdateDto portalUpdateDto, UUID portalUuid) {
        portalRepository.updateMetadataByPortalUuid(portalUpdateDto.getMetadata(), portalUuid);
    }

    public List<PortalGetDto> getPortalsForSpaceUuid(UUID spaceUuid){
        List<Portal> portalList = portalRepository.findBySpacePro_SpaceUuid(spaceUuid);
       return portalMapper.portalToPortalGetDtoList(portalList);
    };

}
