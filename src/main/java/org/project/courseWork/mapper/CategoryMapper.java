package org.project.courseWork.mapper;

import org.mapstruct.*;
import org.project.courseWork.dto.CategoryCreationDto;
import org.project.courseWork.dto.CategoryDto;
import org.project.courseWork.entity.Category;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryDto categoryDto, @MappingTarget Category category);

    Category toEntity(CategoryCreationDto categoryCreationDto);

    CategoryCreationDto toDto1(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryCreationDto categoryCreationDto, @MappingTarget Category category);
}