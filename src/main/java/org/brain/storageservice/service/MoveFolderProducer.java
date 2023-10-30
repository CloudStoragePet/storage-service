package org.brain.storageservice.service;

import org.brain.storageservice.model.MoveFolderTask;

public interface MoveFolderProducer {
    MoveFolderTask sendMoveFolderMessage(MoveFolderTask message);
}
