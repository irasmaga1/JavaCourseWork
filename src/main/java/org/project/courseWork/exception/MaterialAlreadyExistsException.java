package org.project.courseWork.exception;

import jakarta.persistence.EntityExistsException;

public class MaterialAlreadyExistsException extends EntityExistsException {
    public MaterialAlreadyExistsException(String message) {
        super(message);
    }
}
