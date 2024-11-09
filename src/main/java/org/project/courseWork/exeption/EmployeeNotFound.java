package org.project.courseWork.exeption;

import jakarta.persistence.EntityNotFoundException;

public class EmployeeNotFound extends EntityNotFoundException {
    public EmployeeNotFound(String message) {
        super(message);
    }
}
