package org.project.courseWork.exception;

import jakarta.persistence.EntityExistsException;

public class EmployeeAlreadyExistsException extends EntityExistsException {
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
