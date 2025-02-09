package com.thg.accelerator.todolist.domain.listDomain;

import com.thg.accelerator.todolist.domain.taskDomain.TaskItemDomain;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString

public class ListDomain {
    private long listId;
    private ListType listType;
}
