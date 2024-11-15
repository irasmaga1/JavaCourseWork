package org.project.courseWork.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.project.courseWork.entity.Category}
 */
public record CategoryCreationDto(String name, String description) implements Serializable {
  }