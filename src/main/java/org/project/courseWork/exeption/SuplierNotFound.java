package org.project.courseWork.exeption;

import jakarta.persistence.EntityNotFoundException;

public class SuplierNotFound extends EntityNotFoundException {
    public SuplierNotFound(String message) {
        super(message);
    }
}
