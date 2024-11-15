package org.project.courseWork.mapper;

import org.mapstruct.*;
import org.project.courseWork.dto.SuplierCreationDto;
import org.project.courseWork.dto.SuplierDto;
import org.project.courseWork.entity.Suplier;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SuplierMapper {
    Suplier toEntity(SuplierDto suplierDto);

    SuplierDto toDto(Suplier suplier);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Suplier partialUpdate(SuplierDto suplierDto, @MappingTarget Suplier suplier);

    Suplier toEntity(SuplierCreationDto suplierCreationDto);

    SuplierCreationDto toDto1(Suplier suplier);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Suplier partialUpdate(SuplierCreationDto suplierCreationDto, @MappingTarget Suplier suplier);
}