package com.thg.accelerator.todolist.repositories.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task_items")
@ToString
@AllArgsConstructor // for transformer
@NoArgsConstructor // for JPA entity
public class TaskItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String taskPriority;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String taskStatus;

    @ManyToOne
    @JoinColumn(name = "listId", referencedColumnName = "listId")
    private ListEntity listEntity;
}


