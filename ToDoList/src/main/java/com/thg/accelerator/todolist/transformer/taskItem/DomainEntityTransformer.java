package com.thg.accelerator.todolist.transformer.taskItem;

import com.thg.accelerator.todolist.domain.taskDomain.TaskItemDomain;
import com.thg.accelerator.todolist.domain.taskDomain.TaskPriority;
import com.thg.accelerator.todolist.domain.taskDomain.TaskStatus;
import com.thg.accelerator.todolist.exception.ResourceNotFoundException;
import com.thg.accelerator.todolist.repositories.ListRepo;
import com.thg.accelerator.todolist.repositories.entity.ListEntity;
import com.thg.accelerator.todolist.repositories.entity.TaskItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainEntityTransformer {
    @Autowired
    private ListRepo listRepo;
    public DomainEntityTransformer(){
    }

    public TaskItemEntity transformDomainToEntity(TaskItemDomain taskItemDomain) {
        // Fetch corresponding ListEntity from the database
        ListEntity listEntity = listRepo.findById(taskItemDomain.getListId())
                .orElseThrow(() -> new ResourceNotFoundException("ListEntity not found"));

        return new TaskItemEntity(taskItemDomain.getId(),
                taskItemDomain.getTaskPriority().toString(),
                taskItemDomain.getTitle(),
                taskItemDomain.getDescription(),
                taskItemDomain.getDueDate(),
                taskItemDomain.getCreatedDate(),
                taskItemDomain.getUpdatedDate(),
                taskItemDomain.getTaskStatus().toString(),
                listEntity);
    }

    public TaskItemDomain transformEntityToDomain(TaskItemEntity taskItemEntity) {
        return new TaskItemDomain(taskItemEntity.getId(),
                TaskPriority.valueOf(taskItemEntity.getTaskPriority()),
                taskItemEntity.getTitle(),
                taskItemEntity.getDescription(),
                taskItemEntity.getDueDate(),
                taskItemEntity.getCreatedDate(),
                taskItemEntity.getUpdatedDate(),
                TaskStatus.valueOf(taskItemEntity.getTaskStatus()));
    }
}
