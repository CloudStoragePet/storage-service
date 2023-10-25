package org.brain.storageservice.exceptionHandler.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.brain.storageservice.exceptionHandler.model.ErrorType;

import java.io.Serial;

@EqualsAndHashCode(callSuper = false)
@Data
public class FolderException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Folder exception!";

    private ErrorType errorType = ErrorType.PROCESSING_ERROR;
    public FolderException() {
        this(DEFAULT_MESSAGE);
    }
    FolderException(String message) {
        super(message);
    }

}
