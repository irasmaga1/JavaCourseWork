package org.project.courseWork.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.project.courseWork.entity.StockLevel}
 */
public record StockLevelCreationDto(int currentCount, int minimalQuantity) implements Serializable {
}