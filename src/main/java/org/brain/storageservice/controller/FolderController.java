package org.brain.storageservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brain.storageservice.api.FolderApi;
import org.brain.storageservice.model.MoveFolderTask;
import org.brain.storageservice.payload.request.FolderRequest;
import org.brain.storageservice.payload.request.MoveFolderRequest;
import org.brain.storageservice.payload.response.FolderResponse;
import org.brain.storageservice.service.FolderService;
import org.brain.storageservice.service.MoveFolderProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class FolderController implements FolderApi {
    private final FolderService folderService;
    private final MoveFolderProducer moveFolderProducer;
    // todo check if folders are owned by userId
    @Override
    public ResponseEntity<FolderResponse> baseFolder(Long userId) {
        log.info("base folder creation for {}", userId);
        folderService.createFolder(userId.toString(), null, userId);
        log.info("base folder created for {}", userId);
        return null;
    }

    @Override
    public ResponseEntity<FolderResponse> nestedFolder(Long userId, FolderRequest folderRequest)  {
        log.info("new folder creation {}", folderRequest);
        // todo check if parent folder exists with userId
        folderService.createFolder(folderRequest.getName(), folderRequest.getParentFolderId(), userId);
        log.info("new folder created {}", folderRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteFolder(Long userId, Integer folderId) {
        log.info("folder deletion {}", folderId);
        // todo check if folder exists with userId
        folderService.deleteFolder(folderId.longValue(), userId);
        log.info("folder deleted {}", folderId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<FolderResponse> renameFolder(Long userId, FolderRequest folderRequest) {
        log.info("rename folder {}", folderRequest);
        // todo check if folder exists with userId
        folderService.renameFolder(folderRequest.getId(), folderRequest.getName(), userId);
        log.info("folder renamed {}", folderRequest);
        return ResponseEntity.ok().build();    }

    @Override
    public ResponseEntity<MoveFolderTask> moveFolder(Long userId, MoveFolderRequest moveFolderRequest) {
        // todo check if folder exists with userId
        MoveFolderTask moveFolderTask = MoveFolderTask.builder()
                .id(UUID.randomUUID().toString())
                .sourceFolderId(moveFolderRequest.getSourceFolderId())
                .destinationFolderId(moveFolderRequest.getDestinationFolderId())
                .userId(userId)
                .build();
        // delegate to worker-service
        log.info("moveFolder - delegates work to work-storage");
        moveFolderProducer.sendMoveFolderMessage(moveFolderTask);
        return ResponseEntity.ok(moveFolderTask);
    }

    @Override
    public ResponseEntity<MoveFolderTask> getFolderTask(Long userId, String taskId){
        log.info("getFolderTask - get MoveFolderTask");
        return ResponseEntity.ok(folderService.getMoveFolderTask(taskId));
    }

    @Override
    public ResponseEntity<List<MoveFolderTask>> getAllFolderTask(Long userId) {
        log.info("getAllFolderTask - get all MoveFolderTask");
        return ResponseEntity.ok(folderService.getAllMoveFolderTask(userId));
    }
}
