package org.brain.storageservice.exceptionHandler.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.brain.storageservice.exceptionHandler.model.ErrorType;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class FolderNotCreatedException extends FolderException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ErrorType errorType = ErrorType.PROCESSING_ERROR;
    private static final String DEFAULT_MESSAGE = "Folder is not created!";

    public FolderNotCreatedException() {
        this(DEFAULT_MESSAGE);
    }

    public FolderNotCreatedException(String message) {
        super(message);
    }
}
