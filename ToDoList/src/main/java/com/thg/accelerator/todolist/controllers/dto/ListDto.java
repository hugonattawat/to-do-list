package com.thg.accelerator.todolist.controllers.dto;

import com.thg.accelerator.todolist.domain.listDomain.ListType;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ToString

public class ListDto {
    private long listId;
    private ListType listType;
}
