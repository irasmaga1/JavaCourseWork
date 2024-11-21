package org.project.courseWork.exception;

import jakarta.persistence.EntityNotFoundException;

public class StockLevelNotFound extends EntityNotFoundException {
    public StockLevelNotFound(String message) {
        super(message);
    }
}
