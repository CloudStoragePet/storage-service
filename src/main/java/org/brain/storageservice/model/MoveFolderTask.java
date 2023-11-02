package org.brain.storageservice.model;

import lombok.*;
import org.brain.storageservice.model.enums.MoveFolderTaskStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "MoveFolderTask", timeToLive = 60 * 60 * 24) // 24 hours
public class MoveFolderTask {
    @Id
    private String id;
    private Long sourceFolderId;
    private Long destinationFolderId;
    @Indexed
    private Long userId;
    private MoveFolderTaskStatus status;
    private int progress;
}
