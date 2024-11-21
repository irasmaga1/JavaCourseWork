package org.project.courseWork.exception;

import jakarta.persistence.EntityNotFoundException;

public class CategoryNotFound extends EntityNotFoundException {
    public CategoryNotFound(String message) {
        super(message);
    }
}
