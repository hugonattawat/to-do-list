package com.thg.accelerator.todolist.transformer.taskItem;

import com.thg.accelerator.todolist.controllers.dto.TaskItemDto;
import com.thg.accelerator.todolist.domain.taskDomain.TaskItemDomain;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DtoDomainTransformer {

    public DtoDomainTransformer() {
    }

    public TaskItemDomain transformDtoToDomain(TaskItemDto taskItemDto) {
        return new TaskItemDomain(taskItemDto.getId(),
                taskItemDto.getTaskPriority(),
                taskItemDto.getTitle(),
                taskItemDto.getDescription(),
                taskItemDto.getDueDate(),
                taskItemDto.getCreatedDate(),
                taskItemDto.getUpdatedDate(),
                taskItemDto.getTaskStatus());
    }

    public TaskItemDto transformDomainToDto(TaskItemDomain taskItemDomain) {
        String dueDateString = null;
        if (taskItemDomain.getDueDate() != null) {
            dueDateString = taskItemDomain.getDueDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return new TaskItemDto(taskItemDomain.getId(),
                taskItemDomain.getTaskPriority(),
                taskItemDomain.getTitle(),
                taskItemDomain.getDescription(),
                dueDateString,
                taskItemDomain.getCreatedDate(),
                taskItemDomain.getUpdatedDate(),
                taskItemDomain.getTaskStatus());
    }
}
