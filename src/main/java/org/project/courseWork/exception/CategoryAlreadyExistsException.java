package org.project.courseWork.exception;

import jakarta.persistence.EntityExistsException;

public class CategoryAlreadyExistsException extends EntityExistsException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
