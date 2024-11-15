package org.project.courseWork.mapper;

import org.mapstruct.*;
import org.project.courseWork.dto.StockLevelCreationDto;
import org.project.courseWork.dto.StockLevelDto;
import org.project.courseWork.entity.StockLevel;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StockLevelMapper {
    StockLevel toEntity(StockLevelDto stockLevelDto);

    StockLevelDto toDto(StockLevel stockLevel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StockLevel partialUpdate(StockLevelDto stockLevelDto, @MappingTarget StockLevel stockLevel);

    StockLevel toEntity(StockLevelCreationDto stockLevelCreationDto);

    StockLevelCreationDto toDto1(StockLevel stockLevel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StockLevel partialUpdate(StockLevelCreationDto stockLevelCreationDto, @MappingTarget StockLevel stockLevel);
}