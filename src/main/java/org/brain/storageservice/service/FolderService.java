package org.brain.storageservice.service;

import org.brain.storageservice.model.Folder;

public interface FolderService {
    Folder createFolder(String folderName, Long parentFolderId, Long userId);

    void deleteFolder(Long folderId, Long userId);

    Folder renameFolder(Long folderId, String newFolderName, Long userId);

}
