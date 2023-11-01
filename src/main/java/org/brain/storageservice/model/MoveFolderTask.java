package org.brain.storageservice.model;

import lombok.*;
import org.brain.storageservice.model.enums.MoveFolderTaskStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("MoveFolderTask")
public class MoveFolderTask {
    @Id
    private String id;
    private Long sourceFolderId;
    private Long destinationFolderId;
    private Long userId;
    private MoveFolderTaskStatus status;
    private int progress;
}
