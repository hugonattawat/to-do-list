package com.thg.accelerator.todolist.controllers.dto;

import com.thg.accelerator.todolist.domain.taskDomain.TaskPriority;
import com.thg.accelerator.todolist.domain.taskDomain.TaskStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class TaskItemDto {
    private long id;
    private TaskPriority taskPriority;
    private String title;
    private String description;
    private String dueDateString;
    private LocalDateTime dueDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private TaskStatus taskStatus;

    public TaskItemDto(long id,
                       TaskPriority taskPriority,
                       String title,
                       String description,
                       String dueDateString,
//                       LocalDateTime dueDate,
                       LocalDateTime createdDate,
                       LocalDateTime updatedDate,
                       TaskStatus taskStatus) {
        this.id = id;
        this.taskPriority = taskPriority;
        this.title = title;
        this.description = description;
        this.dueDateString = dueDateString;
//        this.dueDate = dueDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.taskStatus = taskStatus;
    }
}
