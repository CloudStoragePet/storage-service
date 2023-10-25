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
public class FolderRequest {
    @Schema(description = "id", example = "5")
    @NotNull(groups = {ValidationNewFolder.class, ValidationUpdateFolder.class})
    private Long id;
    @Schema(description = "name", example = "oldImages")
    @NotBlank(groups = {ValidationNewFolder.class, ValidationUpdateFolder.class})
    private String name;
    @Schema(description = "parentFolderId", example = "3")
    @NotNull(groups = {ValidationNewFolder.class})
    private Long parentFolderId;
}
