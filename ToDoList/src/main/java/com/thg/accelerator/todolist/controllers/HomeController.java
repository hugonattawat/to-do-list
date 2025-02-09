package com.thg.accelerator.todolist.controllers;

import com.thg.accelerator.todolist.controllers.dto.ListDto;
import com.thg.accelerator.todolist.controllers.dto.TaskItemDto;
import com.thg.accelerator.todolist.domain.listDomain.ListDomain;
import com.thg.accelerator.todolist.domain.taskDomain.TaskItemDomain;
import com.thg.accelerator.todolist.exception.ResourceNotFoundException;
import com.thg.accelerator.todolist.services.list.ListService;
import com.thg.accelerator.todolist.services.taskItem.TaskItemService;
import com.thg.accelerator.todolist.transformer.list.DtoDomainTransformerList;
import com.thg.accelerator.todolist.transformer.taskItem.DtoDomainTransformer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class HomeController {
    private final TaskItemService taskItemService;
    private final DtoDomainTransformer dtoDomainTransformer;
    private final ListService listService;
    private final DtoDomainTransformerList dtoDomainTransformerList;

    public HomeController(TaskItemService taskItemService,
                          DtoDomainTransformer dtoDomainTransformer,
                          ListService listService,
                          DtoDomainTransformerList dtoDomainTransformerList) {
        this.taskItemService = taskItemService;
        this.dtoDomainTransformer = dtoDomainTransformer;
        this.listService = listService;
        this.dtoDomainTransformerList = dtoDomainTransformerList;
    }

    @GetMapping("/taskItems/{id}")
    public ResponseEntity<TaskItemDto> getTaskItemById(@PathVariable final Long id) {
        try {
            TaskItemDomain taskItemDomain = taskItemService.getTaskItemById(id);
            return ResponseEntity.ok(dtoDomainTransformer.transformDomainToDto(taskItemDomain));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Helper method
    private LocalDateTime parseDueDate(String dueDateString) {
        if (dueDateString != null && !dueDateString.isEmpty()) {
            LocalDate dueDate = LocalDate.parse(dueDateString, DateTimeFormatter.ISO_LOCAL_DATE);
            return dueDate.atStartOfDay();
        }
        return null;
    }

    //Post - Created
    @PostMapping("/taskItems")
    public ResponseEntity<TaskItemDto> createTaskItem(@RequestBody final TaskItemDto taskItemDto) {
        LocalDateTime dueDateTime = parseDueDate(taskItemDto.getDueDateString());
        taskItemDto.setDueDate(dueDateTime);
        TaskItemDomain savedTask = taskItemService.createTaskItem(dtoDomainTransformer.transformDtoToDomain(taskItemDto));
        TaskItemDto savedTaskDto = dtoDomainTransformer.transformDomainToDto(savedTask);

//        var location = MvcUriComponentsBuilder
//                .fromMethodName(HomeController.class, "getTaskItemById", taskItemDto.getId())
//                .buildAndExpand(taskItemDto.getId())
//                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
//                .created(location)
                .body(savedTaskDto);
        // send response code with duplicate ID
    }

    //GET all
    @GetMapping("/taskItems")
    public ResponseEntity<List<TaskItemDto>> getAllTaskItems() {
        List<TaskItemDomain> taskItemList = taskItemService.getAll();

        return ResponseEntity.ok(
                taskItemList.stream().map(dtoDomainTransformer::transformDomainToDto).toList()
        );
    }

    @GetMapping("/taskItems/list/{listId}")
    public ResponseEntity<List<TaskItemDto>> getAllTaskItemsInList(@PathVariable Long listId) {
        List<TaskItemDomain> taskItemList = taskItemService.getTaskItemsByListId(listId);
        return ResponseEntity.ok(
                taskItemList.stream().map(dtoDomainTransformer::transformDomainToDto).toList()
        );
    }

    @GetMapping("/taskItems/sortedList/{listId}")
    public ResponseEntity<List<TaskItemDto>> getSortedTaskItemsInList(@PathVariable Long listId) {
        List<TaskItemDomain> taskItemList = taskItemService.getTaskItemsInListSorted(listId);
        return ResponseEntity.ok(
                taskItemList.stream().map(dtoDomainTransformer::transformDomainToDto).toList()
        );
    }

    @GetMapping("/taskItems/priorityOrder")
    public ResponseEntity<List<TaskItemDto>> getAllTaskItemsInPriority() {
        List<TaskItemDomain> taskItemList = taskItemService.getAllInPriorityOrder();
        return ResponseEntity.ok(
                taskItemList.stream().map(dtoDomainTransformer::transformDomainToDto).toList()
        );
    }

    @GetMapping("/taskItems/inProgressTasks")
    public ResponseEntity<List<TaskItemDto>> getAllInProgressTaskItems() {
        List<TaskItemDomain> taskItemList = taskItemService.getAllInProgress();
        return ResponseEntity.ok(
                taskItemList.stream().map(dtoDomainTransformer::transformDomainToDto).toList()
        );
    }

    @GetMapping("/taskItems/completedTasks")
    public ResponseEntity<List<TaskItemDto>> getAllCompletedTaskItems() {
        List<TaskItemDomain> taskItemList = taskItemService.getAllCompleted();
        return ResponseEntity.ok(
                taskItemList.stream().map(dtoDomainTransformer::transformDomainToDto).toList()
        );
    }

    //PUT - Ok / Not Found
    @PutMapping("/taskItems/{id}")
    public ResponseEntity<TaskItemDto> updateTaskItem(@PathVariable final Long id, @RequestBody final TaskItemDto taskItemDto) {
        try {
            LocalDateTime dueDateTime = parseDueDate(taskItemDto.getDueDateString());
            taskItemDto.setDueDate(dueDateTime);
            TaskItemDomain savedTask = taskItemService.updateTaskItem(id, dtoDomainTransformer.transformDtoToDomain(taskItemDto));
            TaskItemDto savedTaskDto = dtoDomainTransformer.transformDomainToDto(savedTask);
            return ResponseEntity.ok(savedTaskDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/taskItems/{id}")
    public ResponseEntity<TaskItemDto> deleteTaskItem(@PathVariable final Long id) {
        try {
            taskItemService.deleteTaskItem(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<ListDto> getListById(@PathVariable final long id) {
        try {
            ListDomain listDomain = listService.getListOfTasks(id);
            return ResponseEntity.ok(dtoDomainTransformerList.transformDomainToDto(listDomain));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
