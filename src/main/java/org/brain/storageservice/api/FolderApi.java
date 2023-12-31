package org.brain.storageservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.PositiveOrZero;
import org.brain.storageservice.model.MoveFolderTask;
import org.brain.storageservice.payload.request.FolderRequest;
import org.brain.storageservice.payload.request.MoveFolderRequest;
import org.brain.storageservice.payload.response.FolderResponse;
import org.brain.storageservice.validation.groups.ValidationUpdateFolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * This is the API for the folder service.
 * This API used to create base folder for user and manage nested user folders.
 *
 */
@Tag(name = "Storage service", description = "Foler management API")
@RequestMapping("/api/v1/storage")
@Validated
public interface FolderApi {
    @Operation(summary = "Create base user folder")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "application/json")})})
    @PostMapping(value = "/folder/base/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<FolderResponse> baseFolder(@PathVariable Long userId);

    @Operation(summary = "Create nested user folder")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = FolderResponse.class), mediaType = "application/json")})})
    @PostMapping(value = "/folder/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<FolderResponse> nestedFolder(@PathVariable Long userId, @Validated({ValidationUpdateFolder.class}) @RequestBody FolderRequest folderRequest);

    @Operation(summary = "Delete user folder")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "application/json")})})
    @DeleteMapping(value = "/folder/{userId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> deleteFolder(@PathVariable Long userId, @PositiveOrZero @RequestParam Integer folderId);

    @Operation(summary = "Rename folder")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = FolderResponse.class), mediaType = "application/json")})})
    @PatchMapping(value = "/folder/{userId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<FolderResponse> renameFolder(@PathVariable Long userId, @Validated({ValidationUpdateFolder.class}) @RequestBody FolderRequest folderRequest);

    @Operation(summary = "Move folder")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = MoveFolderTask.class), mediaType = "application/json")})})
    @PutMapping(value = "/folder/{userId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<MoveFolderTask> moveFolder(@PathVariable Long userId, @Validated({ValidationUpdateFolder.class}) @RequestBody MoveFolderRequest moveFolderRequest) ;

    @Operation(summary = "Get folder task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = MoveFolderTask.class), mediaType = "application/json")})})
    @GetMapping(value = "/folder/task/{userId}/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<MoveFolderTask> getFolderTask(@PathVariable Long userId, @PathVariable String taskId);
    @Operation(summary = "Get all folder task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = MoveFolderTask.class)), mediaType = "application/json")})})
    @GetMapping(value = "/folder/task/{userId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<MoveFolderTask>> getAllFolderTask(@PathVariable Long userId);
    @Operation(summary = "Cancel folder task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = MoveFolderTask.class)), mediaType = "application/json")})})
    @PutMapping(value = "/folder/task/{userId}/{taskId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<MoveFolderTask> cancelFolderTask(@PathVariable Long userId, @PathVariable String taskId);

    @Operation(summary = "Stop folder task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = MoveFolderTask.class)), mediaType = "application/json")})})
    @PutMapping(value = "/folder/task/{userId}/{taskId}/stop")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<MoveFolderTask> stopFolderTask(@PathVariable Long userId, @PathVariable String taskId);
    @Operation(summary = "Resume folder task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = MoveFolderTask.class)), mediaType = "application/json")})})
    @PutMapping(value = "/folder/task/{userId}/{taskId}/resume")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<MoveFolderTask> resumeFolderTask(@PathVariable Long userId, @PathVariable String taskId);

}
