package org.project.courseWork.exeption;

import jakarta.persistence.EntityNotFoundException;

public class StockLevelNotFound extends EntityNotFoundException {
    public StockLevelNotFound(String message) {
        super(message);
    }
}
