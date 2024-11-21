package org.project.courseWork.exception;

import jakarta.persistence.EntityExistsException;

public class SuplierAlreadyExistsExeption extends EntityExistsException {
    public SuplierAlreadyExistsExeption(String message) {
        super(message);
    }
}
