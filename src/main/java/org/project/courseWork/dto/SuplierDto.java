package org.project.courseWork.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.project.courseWork.entity.Suplier}
 */


public record SuplierDto(Long id, String name, String category, String phoneNumber,
                         String email) implements Serializable {
}