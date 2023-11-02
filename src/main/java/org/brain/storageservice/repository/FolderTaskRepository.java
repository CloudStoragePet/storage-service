package org.brain.storageservice.repository;

import org.brain.storageservice.model.MoveFolderTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderTaskRepository extends CrudRepository<MoveFolderTask, String> {
    List<MoveFolderTask> findByUserId(@Param("userId") Long userId);
}