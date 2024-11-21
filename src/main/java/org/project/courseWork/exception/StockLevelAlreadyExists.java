package org.project.courseWork.exception;

import jakarta.persistence.EntityExistsException;

public class StockLevelAlreadyExists extends EntityExistsException {
    public StockLevelAlreadyExists(String message) {
        super(message);
    }
}
