package org.project.courseWork.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.project.courseWork.entity.Category}
 */
public record CategoryDto(Long id, String name, String description) implements Serializable {
}