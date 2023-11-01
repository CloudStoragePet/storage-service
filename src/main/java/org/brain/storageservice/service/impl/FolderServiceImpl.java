package org.brain.storageservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brain.storageservice.config.BasePathProperties;
import org.brain.storageservice.exceptionHandler.exceptions.FolderNotCreatedException;
import org.brain.storageservice.exceptionHandler.exceptions.FolderNotDeletedException;
import org.brain.storageservice.exceptionHandler.exceptions.FolderNotFoundException;
import org.brain.storageservice.model.Folder;
import org.brain.storageservice.model.MoveFolderTask;
import org.brain.storageservice.repository.FolderRepository;
import org.brain.storageservice.repository.FolderTaskRepository;
import org.brain.storageservice.service.FolderService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final FolderTaskRepository folderTaskRepository;
    private final BasePathProperties basePathProperties;
    private static final String FOLDER_NOT_CREATED_ERROR_MESSAGE = "Failed to create folder ";
    private static final String FOLDER_NOT_DELETE_ERROR_MESSAGE = "Failed to delete folder ";
    private static final String OBJECT_NOT_DELETE_ERROR_MESSAGE = "Failed to delete object ";
    private static final String FOLDER_ALREADY_EXISTS_WITH_NAME_ERROR_MESSAGE = "Folder already exists with name ";
    private static final String FOLDER_NOT_RENAMED_ERROR_MESSAGE = "Folder not renamed ";


    @Override
    public Folder createFolder(String folderName, Long parentFolderId, Long userId) {
        // get whole path by parent folder id
        log.info("Creating folder: " + folderName + " in parent folder with id: " + parentFolderId);
        Path newPath = getFullPathByFolderId(parentFolderId).resolve(folderName);
        // Create the folder in db
        // todo check if folder with the same name already exists
        if (checkIfFolderAlreadyExists(folderName, parentFolderId)) {
            log.error(FOLDER_ALREADY_EXISTS_WITH_NAME_ERROR_MESSAGE + folderName);
            throw new FolderNotCreatedException(FOLDER_ALREADY_EXISTS_WITH_NAME_ERROR_MESSAGE + folderName);
        }
        log.info("Creating folder in path: " + newPath);
        Folder newFolder = Folder.builder()
                .name(folderName)
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .parent(getFolderById(parentFolderId)).build();
        Folder createdFolder = folderRepository.save(newFolder);
        // Create the folder in the file system
        log.info("Creating folder in file system: " + newPath);
        createFolderInFilesystem(newPath);
        log.info("Folder created: " + createdFolder);
        return createdFolder;
    }

    @Override
    public void deleteFolder(Long folderId, Long userId) {
        // get whole path by folder id
        Path newPath = getFullPathByFolderId(folderId);
        // Delete folder in db
        log.info("Deleting folder in db: " + folderId);
        folderRepository.deleteById(folderId);
        // Delete folder in the file system
        log.info("Deleting folder in file system: " + newPath);
        deleteFolderInFilesystemWithFiles(newPath);
        log.info("Folder deleted: " + folderId);
    }


    @Override
    public Folder renameFolder(Long folderId, String newFolderName, Long userId) {
        // check if folder with the same name already exists
        if (checkIfFolderAlreadyExists(newFolderName, folderId)) {
            log.error(FOLDER_ALREADY_EXISTS_WITH_NAME_ERROR_MESSAGE + newFolderName);
            throw new FolderNotCreatedException(FOLDER_ALREADY_EXISTS_WITH_NAME_ERROR_MESSAGE + newFolderName);
        }
        // change name in db
        Folder folder = getFolderById(folderId);
        folder.setName(newFolderName);
        // change name in file system
        renameFolderInFilesystem(getFullPathByFolderId(folderId), newFolderName);
        return folder;
    }


    @Override
    public MoveFolderTask getMoveFolderTask(String id) {
        return folderTaskRepository.findById(id).orElseThrow(FolderNotFoundException::new);
    }


    private Path getFullPathByFolderId(Long folderId) {
        StringBuilder path = new StringBuilder();
        while (folderId != null) {
            Folder folder = getFolderById(folderId);
            path.insert(0, folder.getName() + "/");
            folderId = folder.getParent().getId();
        }
        return Path.of(basePathProperties.path() + "/" + path);
    }

    private Folder getFolderById(Long folderId) {
        return folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);
    }

    private void createFolderInFilesystem(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException ex) {
            log.error(FOLDER_NOT_CREATED_ERROR_MESSAGE + path);
            log.debug(FOLDER_NOT_CREATED_ERROR_MESSAGE + path, ex);
            throw new FolderNotCreatedException(FOLDER_NOT_CREATED_ERROR_MESSAGE + path);
        }
    }

    private boolean checkIfFolderAlreadyExists(String folderName, Long parentFolderId) {
        return folderRepository.existsByParent_IdAndName(parentFolderId, folderName);
    }

    //todo: delete folder with files
    //todo: backup folder if error occurs
    private void deleteFolderInFilesystemWithFiles(Path path) {
        try (Stream<Path> pathStream = Files.walk(path)) {
            pathStream.sorted(Comparator.reverseOrder())
                    .forEach(pathToDelete -> {
                        try {
                            log.debug("Deleting object: " + pathToDelete);
                            Files.deleteIfExists(pathToDelete);
                        } catch (IOException e) {
                            log.error(OBJECT_NOT_DELETE_ERROR_MESSAGE + pathToDelete);
                            throw new FolderNotDeletedException(OBJECT_NOT_DELETE_ERROR_MESSAGE + pathToDelete);
                        }
                    });
        } catch (IOException ex) {
            log.error(FOLDER_NOT_DELETE_ERROR_MESSAGE + path);
            log.debug(FOLDER_NOT_DELETE_ERROR_MESSAGE + path, ex);
            throw new FolderNotDeletedException(FOLDER_NOT_DELETE_ERROR_MESSAGE + path);
        }
    }

    private void renameFolderInFilesystem(Path sourcePath, String newName) {
        try {
            Files.move(sourcePath, sourcePath.resolveSibling(newName));
        } catch (IOException ex) {
            log.error(FOLDER_NOT_RENAMED_ERROR_MESSAGE + sourcePath);
            log.debug(FOLDER_NOT_RENAMED_ERROR_MESSAGE + sourcePath, ex);
            throw new FolderNotDeletedException(FOLDER_NOT_RENAMED_ERROR_MESSAGE + sourcePath);
        }
    }

}
