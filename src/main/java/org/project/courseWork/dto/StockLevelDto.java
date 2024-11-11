package org.project.courseWork.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.project.courseWork.entity.StockLevel}
 */
public record StockLevelDto(Long id, int currentCount, int minimalQuantity, Long matherialId) implements Serializable {
}