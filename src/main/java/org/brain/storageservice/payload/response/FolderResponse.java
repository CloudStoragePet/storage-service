package org.brain.storageservice.payload.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FolderResponse {

    private Long id;

    private String name;

    private Long parentFolderId;
}
