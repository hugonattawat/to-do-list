package com.thg.accelerator.todolist.repositories;

import com.thg.accelerator.todolist.repositories.entity.ListEntity;
import com.thg.accelerator.todolist.repositories.entity.TaskItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepo extends JpaRepository<ListEntity, Long> {
    ListEntity findByListType(String listType);
}
