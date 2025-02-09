package com.thg.accelerator.todolist.transformer.list;

import com.thg.accelerator.todolist.domain.listDomain.ListDomain;
import com.thg.accelerator.todolist.domain.listDomain.ListType;
import com.thg.accelerator.todolist.repositories.TaskItemRepo;
import com.thg.accelerator.todolist.repositories.entity.ListEntity;
import com.thg.accelerator.todolist.repositories.entity.TaskItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DomainEntityTransformerList {
    @Autowired
    private TaskItemRepo taskItemRepo;
    public DomainEntityTransformerList() {
    }
    public ListEntity transformDomainToEntity(ListDomain listDomain){
        List<TaskItemEntity> taskItemEntityList = taskItemRepo.findByListId(listDomain.getListId());
        return new ListEntity(
                listDomain.getListId(),
                listDomain.getListType().toString(),
                taskItemEntityList);
    }

    public ListDomain transformEntityToDomain(ListEntity listEntity){
        return new ListDomain(
                listEntity.getListId(),
                ListType.valueOf(listEntity.getListType()));
    }
}
