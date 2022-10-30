package com.ravel.backend.spaceLite.dto;

import com.ravel.backend.spaceLite.model.SpaceLite;
import java.util.List;
import java.util.Set;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpaceLiteMapper {
	@BeanMapping(
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
	SpaceLiteGetDto spaceLiteToSpaceLiteDto(SpaceLite spaceLite);

	@BeanMapping(
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
	Set<SpaceLiteGetDto> spaceLiteSetToSpaceLiteDtoSet(Set<SpaceLite> spaceLites);

	@BeanMapping(
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
	List<SpaceLiteGetDto> spaceLiteListToSpaceLiteDtoSet(List<SpaceLite> spaceLites);

	@BeanMapping(
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
	void updateSpaceLiteFromSpaceLiteDto(
		SpaceLitePostDto spaceLitePostDto,
		@MappingTarget SpaceLite spaceLite
	);
	//
	//    SpaceLiteOrganization spaceLiteOrganizationDtoToSpaceLiteOrganization(SpaceLiteOrganizationGetDto spaceLiteOrganizationGetDto);
	//
	//    SpaceLiteOrganizationGetDto spaceLiteOrganizationToSpaceLiteOrganizationDto(SpaceLiteOrganization spaceLiteOrganization);
	//
	//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	//    void updateSpaceLiteOrganizationFromSpaceLiteOrganizationDto(SpaceLiteOrganizationGetDto spaceLiteOrganizationGetDto, @MappingTarget SpaceLiteOrganization spaceLiteOrganization);
	//
	//    SpaceLite spaceLitePostDtoToSpaceLite(SpaceLitePostDto spaceLitePostDto);
	//
	//    SpaceLitePostDto spaceLiteToSpaceLitePostDto(SpaceLite spaceLite);
	//
	//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	//    void updateSpaceLiteFromSpaceLitePostDto(SpaceLitePostDto spaceLitePostDto, @MappingTarget SpaceLite spaceLite);
}
