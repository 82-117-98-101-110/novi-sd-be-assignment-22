package com.ravel.backend.spacePro.mapper;

import com.ravel.backend.spacePro.dto.PortalGetDto;
import com.ravel.backend.spacePro.model.Portal;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PortalMapper {
    Portal portalGetDtoToPortal(PortalGetDto portalGetDto2);

    PortalGetDto portalToPortalGetDto(Portal portal);

    List<PortalGetDto> portalToPortalGetDtoList(List<Portal> portal);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePortalFromPortalGetDto(PortalGetDto portalGetDto2, @MappingTarget Portal portal);
}
