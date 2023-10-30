package org.brain.storageservice.repository;

import org.brain.storageservice.model.MoveFolderTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderTaskRepository extends CrudRepository<MoveFolderTask, String> {}
