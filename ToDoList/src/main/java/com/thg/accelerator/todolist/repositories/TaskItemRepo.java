package com.thg.accelerator.todolist.repositories;

import com.thg.accelerator.todolist.repositories.entity.TaskItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskItemRepo extends JpaRepository<TaskItemEntity, Long> {
    @Query("SELECT t FROM TaskItemEntity t WHERE t.listEntity.listId = :listId")
    List<TaskItemEntity> findByListId(@Param("listId") Long listId);
}
