package com.ravel.backend.spacePro.mapper;

import com.ravel.backend.spacePro.dto.*;
import com.ravel.backend.spacePro.model.*;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SpaceProMapper {
    EnvironmentPro environmentProGetDtoToEnvironmentPro(
            EnvironmentProGetDto environmentProGetDto
    );

    EnvironmentProGetDto environmentProToEnvironmentProGetDto(
            EnvironmentPro environmentPro
    );

    List<EnvironmentProGetDto> environmentProListToEnvironmentProGetDtoList(
            List<EnvironmentPro> environmentProList
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateEnvironmentProFromEnvironmentProGetDto(
            EnvironmentProGetDto environmentProGetDto,
            @MappingTarget EnvironmentPro environmentPro
    );

    EnvironmentPro environmentProPostDtoToEnvironmentPro(
            EnvironmentProPostDto environmentProPostDto
    );

    EnvironmentProPostDto environmentProToEnvironmentProPostDto(
            EnvironmentPro environmentPro
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateEnvironmentProFromEnvironmentProPostDto(
            EnvironmentProPostDto environmentProPostDto,
            @MappingTarget EnvironmentPro environmentPro
    );

    SpacePro spaceProPostDtoToSpacePro(SpaceProPostDto spaceProPostDto);

    SpaceProPostDto spaceProToSpaceProPostDto(SpacePro spacePro);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateSpaceProFromSpaceProPostDto(
            SpaceProPostDto spaceProPostDto,
            @MappingTarget SpacePro spacePro
    );

    SpacePro spaceProGetDtoToSpacePro(SpaceProGetDto spaceProGetDto);

    SpaceProGetDto spaceProToSpaceProGetDto(SpacePro spacePro);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateSpaceProFromSpaceProGetDto(
            SpaceProGetDto spaceProGetDto,
            @MappingTarget SpacePro spacePro
    );

    SpaceRole spaceRoleGetDtoToSpaceRole(SpaceRoleGetDto spaceRoleGetDto);

    SpaceRoleGetDto spaceRoleToSpaceRoleGetDto(SpaceRole spaceRole);

    List<SpaceRoleGetDto> spaceRoleListToSpaceRoleGetDtoList(List<SpaceRole> spaceRole);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateSpaceRoleFromSpaceRoleGetDto(
            SpaceRoleGetDto spaceRoleGetDto,
            @MappingTarget SpaceRole spaceRole
    );

    SpacePro spaceProGetEnvDtoToSpacePro(SpaceProGetEnvDto spaceProGetEnvDto);

    @Mapping(source = "sessionSpaceId", target = "photonRoomId")
    SpaceProGetEnvDto spaceProToSpaceProGetEnvDto(SpacePro spacePro);


    @Mapping(source = "sessionSpaceId", target = "photonRoomId")
    List<SpaceProGetEnvDto> spaceProListToSpaceProGetEnvDtoList(List<SpacePro> spacePro);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateSpaceProFromSpaceProGetEnvDto(
            SpaceProGetEnvDto spaceProGetEnvDto,
            @MappingTarget SpacePro spacePro
    );

    SpaceProUserDetails spaceProUserDetailsGetDtoToSpaceProUserDetails(
            SpaceProUserDetailsGetDto spaceProUserDetailsGetDto
    );

    SpaceProUserDetailsGetDto spaceProUserDetailsToSpaceProUserDetailsGetDto(
            SpaceProUserDetails spaceProUserDetails
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateSpaceProUserDetailsFromSpaceProUserDetailsGetDto(
            SpaceProUserDetailsGetDto spaceProUserDetailsGetDto,
            @MappingTarget SpaceProUserDetails spaceProUserDetails
    );

    SpaceProUserDetails spaceProUserDetailsDtoToSpaceProUserDetails(
            SpaceProUserDetailsDto spaceProUserDetailsDto
    );

    SpaceProUserDetailsDto spaceProUserDetailsToSpaceProUserDetailsDto(
            SpaceProUserDetails spaceProUserDetails
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateSpaceProUserDetailsFromSpaceProUserDetailsDto(
            SpaceProUserDetailsDto spaceProUserDetailsDto,
            @MappingTarget SpaceProUserDetails spaceProUserDetails
    );

    SpaceProUser spaceProUserDtoToSpaceProUser(SpaceProUserDto spaceProUserDto);

    SpaceProUserDto spaceProUserToSpaceProUserDto(SpaceProUser spaceProUser);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateSpaceProUserFromSpaceProUserDto(
            SpaceProUserDto spaceProUserDto,
            @MappingTarget SpaceProUser spaceProUser
    );

//
//    Portal portalGetDtoToPortal(PortalGetDto portalGetDto);
//
//    PortalGetDto portalToPortalGetDto(Portal portal);
    List<PortalGetDto> portalToPortalGetDtoList(List<Portal> portal);


//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updatePortalFromPortalGetDto(PortalGetDto portalGetDto, @MappingTarget Portal portal);
//
//    SpacePro joinSpaceGetDtoV2ToSpacePro(JoinSpaceGetDtoV2 joinSpaceGetDtoV2);
//
//    JoinSpaceGetDtoV2 spaceProToJoinSpaceGetDtoV2(SpacePro spacePro);
//
//    List<JoinSpaceGetDtoV2> spaceProToJoinSpaceGetDtoV2List(List<SpacePro> spacePro);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateSpaceProFromJoinSpaceGetDtoV2(JoinSpaceGetDtoV2 joinSpaceGetDtoV2, @MappingTarget SpacePro spacePro);
}
