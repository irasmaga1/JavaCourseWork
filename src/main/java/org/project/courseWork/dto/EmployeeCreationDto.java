package org.project.courseWork.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.project.courseWork.entity.Employee}
 */
public record EmployeeCreationDto(String name, String position, String contactNumber,
                                  String email) implements Serializable {
}