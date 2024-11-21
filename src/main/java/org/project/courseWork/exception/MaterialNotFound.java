package org.project.courseWork.exception;

import jakarta.persistence.EntityNotFoundException;

public class MaterialNotFound extends EntityNotFoundException {
    public MaterialNotFound(String message) {
        super(message);
    }
}
