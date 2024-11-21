package org.project.courseWork.mapper;

import org.mapstruct.*;
import org.project.courseWork.dto.MaterialCreationDto;
import org.project.courseWork.dto.MaterialDto;
import org.project.courseWork.entity.Material;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MaterialMapper {

    @Mapping(source = "suplier.id", target = "suplierId")
    @Mapping(source = "category.id", target = "categoryId")
    MaterialDto toDto(Material material);

    Material toEntity(MaterialDto materialDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Material partialUpdate(MaterialDto materialDto, @MappingTarget Material material);

    Material toEntity(MaterialCreationDto materialCreationDto);
}
