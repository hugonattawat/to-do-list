package com.thg.accelerator.todolist.repositories.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "task_items_list")
@AllArgsConstructor
@NoArgsConstructor
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long listId;
    private String listType;

    @OneToMany(mappedBy = "listEntity")
    private List<TaskItemEntity> taskItems;
}
