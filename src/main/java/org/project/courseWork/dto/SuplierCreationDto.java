package org.project.courseWork.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.project.courseWork.entity.Suplier}
 */
public record SuplierCreationDto(String name, String phoneNumber, String email) implements Serializable {
}