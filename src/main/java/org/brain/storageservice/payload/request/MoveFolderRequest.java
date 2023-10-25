package org.brain.storageservice.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.brain.storageservice.validation.groups.ValidationNewFolder;
import org.brain.storageservice.validation.groups.ValidationUpdateFolder;

@Builder
@Data
public class MoveFolderRequest {
    @Schema(description = "sourceFolderId", example = "4")
    @NotNull(groups = {ValidationNewFolder.class, ValidationUpdateFolder.class})
    private Long sourceFolderId;
    @Schema(description = "name", example = "oldImages")
    @NotBlank(groups = {ValidationNewFolder.class, ValidationUpdateFolder.class})
    private String name;
    @Schema(description = "destinationFolderId", example = "5")
    @NotNull(groups = {ValidationNewFolder.class})
    private Long destinationFolderId;
}
