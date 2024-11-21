package org.project.courseWork.dto;

import org.project.courseWork.entity.Material;

import java.io.Serializable;

/**
 * DTO for {@link Material}
 */
public record MaterialDto(Long id, String name, int count, int price, String brand, Long suplierId,
                          Long categoryId) implements Serializable {

}