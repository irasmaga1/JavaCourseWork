package org.project.courseWork.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.project.courseWork.entity.Material}
 */
public record MaterialCreationDto(String name, int count, int price, String brand) implements Serializable {
}