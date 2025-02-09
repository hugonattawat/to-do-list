package com.thg.accelerator.todolist.services.list;

import com.thg.accelerator.todolist.domain.listDomain.ListDomain;
import com.thg.accelerator.todolist.domain.taskDomain.TaskStatus;
import com.thg.accelerator.todolist.exception.ResourceNotFoundException;
import com.thg.accelerator.todolist.repositories.ListRepo;
import com.thg.accelerator.todolist.repositories.entity.ListEntity;
import com.thg.accelerator.todolist.transformer.list.DomainEntityTransformerList;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ListService {
    @Autowired
    private ListRepo listRepo;

    private DomainEntityTransformerList domainEntityTransformerList;

    public ListDomain getListOfTasks(Long id) {
        Optional<ListEntity> optionalInProgressList = listRepo.findById(id);
        if (optionalInProgressList.isPresent()) {
            return domainEntityTransformerList.transformEntityToDomain(optionalInProgressList.get());
        } else {
            throw new ResourceNotFoundException(String.format("List Id %d not found", id));
        }
    }

}
