package org.brain.storageservice.exceptionHandler.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.brain.storageservice.exceptionHandler.model.ErrorType;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class FolderTaskNotFoundException extends EntityNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ErrorType errorType = ErrorType.DATABASE_ERROR;
    private static final String DEFAULT_MESSAGE = "Folder task is not found!";

    public FolderTaskNotFoundException() {
        this(DEFAULT_MESSAGE);
    }

    public FolderTaskNotFoundException(String message) {
        super(message);
    }
}
