package com.thg.accelerator.todolist.domain.taskDomain;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString

public class TaskItemDomain {
    private long id;
    private TaskPriority taskPriority;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private TaskStatus taskStatus;
    private long listId;

    public TaskItemDomain(long id,
                       TaskPriority taskPriority,
                       String title,
                       String description,
                       LocalDateTime dueDate,
                       LocalDateTime createdDate,
                       LocalDateTime updatedDate,
                       TaskStatus taskStatus) {
        this.id = id;
        this.taskPriority = taskPriority;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.taskStatus = taskStatus;
    }

//    public boolean isTaskOnGoing
}
