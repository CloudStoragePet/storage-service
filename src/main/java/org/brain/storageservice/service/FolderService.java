package org.brain.storageservice.service;

import org.brain.storageservice.exceptionHandler.exceptions.FolderNotFoundException;
import org.brain.storageservice.model.Folder;
import org.brain.storageservice.model.MoveFolderTask;

import java.util.List;

public interface FolderService {
    /**
     * Create a folder in the file system and in the database
     *
     * @param folderName     - name of the folder
     * @param parentFolderId - id of the parent folder
     * @param userId         - id of the user who creates the folder
     * @return Folder - created folder
     */
    Folder createFolder(String folderName, Long parentFolderId, Long userId);

    void deleteFolder(Long folderId, Long userId);

    Folder renameFolder(Long folderId, String newFolderName, Long userId);
    /**
     * Get MoveFolderTask by id in Redis
     *
     * @deprecated - new pub/sub implementation should be implemented in Redis
     * @param id - id of the MoveFolderTask
     * @return MoveFolderTask
     * @throws FolderNotFoundException if task not found or GET task conflicts with SAVE task in worker-storage-service
     */
    @Deprecated
    MoveFolderTask getMoveFolderTask(String id);

    /**
     * Get all MoveFolderTask by userId in Redis
     * @param userId - id of the user
     * @return List<MoveFolderTask> - list of MoveFolderTask
     */
    List<MoveFolderTask> getAllMoveFolderTask(Long userId);

    MoveFolderTask cancelMoveFolderTask(String taskId);

    MoveFolderTask stopMoveFolderTask(String taskId);

    MoveFolderTask resumeMoveFolderTask(String taskId);
}
