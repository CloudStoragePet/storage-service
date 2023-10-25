package org.brain.storageservice.model;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MoveFolderTask {
    private String taskId;
    private Long sourceFolderId;
    private Long destinationFolderId;
    private Long userId;
    private String status; // Optional, if you want to track status in Redis or in Worker Service

}
