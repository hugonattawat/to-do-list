package com.thg.accelerator.todolist.services.taskItem;

import com.thg.accelerator.todolist.domain.listDomain.ListDomain;
import com.thg.accelerator.todolist.domain.listDomain.ListType;
import com.thg.accelerator.todolist.domain.taskDomain.TaskItemDomain;
import com.thg.accelerator.todolist.domain.taskDomain.TaskStatus;
import com.thg.accelerator.todolist.exception.ResourceNotFoundException;
import com.thg.accelerator.todolist.repositories.ListRepo;
import com.thg.accelerator.todolist.repositories.TaskItemRepo;
import com.thg.accelerator.todolist.repositories.entity.ListEntity;
import com.thg.accelerator.todolist.repositories.entity.TaskItemEntity;
import com.thg.accelerator.todolist.transformer.list.DomainEntityTransformerList;
import com.thg.accelerator.todolist.transformer.taskItem.DomainEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Comparator;

// interfacing with TaskItemRepo; add a new TaskItem, get all items via Service
@Service
public class TaskItemService {
    @Autowired
    private TaskItemRepo taskItemRepo;

    private DomainEntityTransformer domainEntityTransformer;

    @Autowired
    private ListRepo listRepo;

    public TaskItemService(
            TaskItemRepo taskItemRepo,
            DomainEntityTransformer domainEntityTransformer) {
        this.taskItemRepo = taskItemRepo;
        this.domainEntityTransformer = domainEntityTransformer;
    }

    public List<TaskItemDomain> getAll() {
        List<TaskItemEntity> taskItemList = taskItemRepo.findAll();
        return taskItemList.stream().map(domainEntityTransformer::transformEntityToDomain).toList();
    }

    public List<TaskItemDomain> getTaskItemsByListId(Long listId) {
        List<TaskItemEntity> taskItemList = taskItemRepo.findByListId(listId);
        return taskItemList.stream().map(domainEntityTransformer::transformEntityToDomain).toList();
    }

    public List<TaskItemDomain> getAllInProgress() {
        List<TaskItemEntity> taskItemList = taskItemRepo.findAll();
        List<TaskItemEntity> taskItemInProgList = new ArrayList<>();

        for (TaskItemEntity taskItem : taskItemList) {
            if (taskItem.getTaskStatus().equalsIgnoreCase("doing")) {
                taskItemInProgList.add(taskItem);
            }
        }
        return taskItemInProgList.stream().map(domainEntityTransformer::transformEntityToDomain).toList();
    }

    public List<TaskItemDomain> getAllCompleted() {
        List<TaskItemEntity> taskItemList = taskItemRepo.findAll();
        List<TaskItemEntity> taskItemCompList = new ArrayList<>();

        for (TaskItemEntity taskItem : taskItemList) {
            if (taskItem.getTaskStatus().equalsIgnoreCase("done")) {
                taskItemCompList.add(taskItem);
            }
        }
        return taskItemCompList.stream().map(domainEntityTransformer::transformEntityToDomain).toList();
    }

    public List<TaskItemDomain> getAllInPriorityOrder() {
        List<TaskItemEntity> taskItemList = taskItemRepo.findAll();
        return taskItemList.stream()
                .sorted(Comparator.comparingInt(taskItem -> getPriorityValue(taskItem.getTaskPriority())))
                .map(domainEntityTransformer::transformEntityToDomain)
                .toList();
    }

    public List<TaskItemDomain> getTaskItemsInListSorted(Long listId) {
        List<TaskItemEntity> taskItemList = taskItemRepo.findByListId(listId);
        return taskItemList.stream()
                .sorted(Comparator.comparingInt(taskItem -> getPriorityValue(taskItem.getTaskPriority())))
                .map(domainEntityTransformer::transformEntityToDomain)
                .toList();
    }

    private int getPriorityValue(String priority) {
        switch (priority.toLowerCase()) {
            case "high":
                return 1;
            case "medium":
                return 2;
            case "low":
                return 3;
            default:
                return 4;
        }
    }

    public TaskItemDomain getTaskItemById(Long id) {
        Optional<TaskItemEntity> optionalTaskItemEntity = taskItemRepo.findById(id);
        if (optionalTaskItemEntity.isPresent()) {
            return domainEntityTransformer.transformEntityToDomain(optionalTaskItemEntity.get());
        } else {
            throw new ResourceNotFoundException(String.format("Task Item Id %d not found", id));
        }
    }

    public TaskItemDomain createTaskItem(TaskItemDomain taskItemDomain) {
        if (taskItemDomain.getId() == 0L) {
            taskItemDomain.setCreatedDate(LocalDateTime.now());
            taskItemDomain.setUpdatedDate(LocalDateTime.now());
            taskItemDomain.setListId(getListIdFromListEntity(taskItemDomain.getTaskStatus()));
        }

        TaskItemEntity savedTask = taskItemRepo.save((domainEntityTransformer.transformDomainToEntity(taskItemDomain)));
//        TaskItemDomain savedTaskDomain = domainEntityTransformer.transformEntityToDomain(savedTask);
        return domainEntityTransformer.transformEntityToDomain(savedTask);
    }

    private long getListIdFromListEntity(TaskStatus taskStatus) {
        ListEntity listEntity = listRepo.findByListType(taskStatus.toString());
        if (listEntity != null) {
            return listEntity.getListId();
        } else {
            throw new ResourceNotFoundException("List for status " + taskStatus + " not found");
        }
    }

    public void deleteTaskItem(Long id) {
        Optional<TaskItemEntity> optionalTaskItemEntity = taskItemRepo.findById(id);

        if (optionalTaskItemEntity.isPresent()) {
            taskItemRepo.delete(optionalTaskItemEntity.get());
        } else {
            throw new ResourceNotFoundException(String.format("Task Item Id %d not found", id));
        }
    }

    public TaskItemDomain updateTaskItem(Long id, TaskItemDomain updatedTaskItemDomain) {
        Optional<TaskItemEntity> optionalTaskItemEntity = taskItemRepo.findById(id);

        if (optionalTaskItemEntity.isPresent()) {
            TaskItemEntity taskItem = optionalTaskItemEntity.get();
            taskItem.setTitle(updatedTaskItemDomain.getTitle());
            taskItem.setDescription(updatedTaskItemDomain.getDescription());
            taskItem.setDueDate(updatedTaskItemDomain.getDueDate());
            taskItem.setTaskStatus(updatedTaskItemDomain.getTaskStatus().toString());
            taskItem.setTaskPriority(updatedTaskItemDomain.getTaskPriority().toString());
            taskItem.setUpdatedDate(updatedTaskItemDomain.getUpdatedDate());

            TaskItemEntity savedTask = taskItemRepo.save(taskItem);
            return domainEntityTransformer.transformEntityToDomain(savedTask);
        } else {
            throw new ResourceNotFoundException(String.format("Task Item Id %d not found", id));
        }
    }

}
