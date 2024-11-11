package org.project.courseWork.mapper;

import org.mapstruct.*;
import org.project.courseWork.dto.EmployeeDto;
import org.project.courseWork.entity.Employee;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    Employee toEntity(EmployeeDto employeeDto);

    EmployeeDto toDto(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee partialUpdate(EmployeeDto employeeDto, @MappingTarget Employee employee);
}