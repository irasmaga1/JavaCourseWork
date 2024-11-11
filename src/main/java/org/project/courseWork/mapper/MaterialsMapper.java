package org.project.courseWork.mapper;

import org.mapstruct.*;
import org.project.courseWork.dto.MaterialsDto;
import org.project.courseWork.entity.Material;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MaterialsMapper {
    Material toEntity(MaterialsDto materialsDto);

    MaterialsDto toDto(Material material);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Material partialUpdate(MaterialsDto materialsDto, @MappingTarget Material material);
}