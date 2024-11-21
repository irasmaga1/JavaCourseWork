package org.project.courseWork.exception;

import jakarta.persistence.EntityNotFoundException;

public class EmployeeNotFound extends EntityNotFoundException {
    public EmployeeNotFound(String message) {
        super(message);
    }
}
