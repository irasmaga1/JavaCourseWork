package org.project.courseWork.mapper;

import org.mapstruct.*;
import org.project.courseWork.dto.MaterialDto;
import org.project.courseWork.entity.Material;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MaterialMapper {
    Material toEntity(MaterialDto materialDto);

    MaterialDto toDto(Material material);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Material partialUpdate(MaterialDto materialDto, @MappingTarget Material material);
}