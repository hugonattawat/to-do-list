package com.thg.accelerator.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thg.accelerator.todolist.controllers.HomeController;
import com.thg.accelerator.todolist.domain.taskDomain.TaskItemDomain;
import com.thg.accelerator.todolist.domain.taskDomain.TaskPriority;
import com.thg.accelerator.todolist.domain.taskDomain.TaskStatus;
import com.thg.accelerator.todolist.exception.ResourceNotFoundException;
import com.thg.accelerator.todolist.services.taskItem.TaskItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
//@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc
public class HomeControllerTest {

    private static final TaskItemDomain TASK_ONE = new TaskItemDomain(1,
            TaskPriority.HIGH, "1", "Task 1",
            LocalDateTime.of(2024, Month.FEBRUARY, 2, 0, 0),
            LocalDateTime.now(), null, TaskStatus.ToDo);

    private static final TaskItemDomain TASK_TWO = new TaskItemDomain(2,
            TaskPriority.LOW, "2", "Task 2",
            LocalDateTime.of(2024, Month.FEBRUARY, 2, 0, 0),
            LocalDateTime.now(), null, TaskStatus.ToDo);

    private static final TaskItemDomain TASK_THREE = new TaskItemDomain(3,
            TaskPriority.HIGH, "3", "Task 3",
            LocalDateTime.of(2024, Month.FEBRUARY, 2, 0, 0),
            LocalDateTime.of(2024, Month.JANUARY, 30, 0, 0), LocalDateTime.now(),
            TaskStatus.DOING);

    private static final TaskItemDomain TASK_FOUR = new TaskItemDomain(4,
            TaskPriority.MEDIUM, "4", "Task 4",
            LocalDateTime.of(2024, Month.FEBRUARY, 2, 0, 0),
            LocalDateTime.of(2024, Month.JANUARY, 31, 0, 0), LocalDateTime.now(),
            TaskStatus.DOING);

    private static final TaskItemDomain TASK_FIVE = new TaskItemDomain(5,
            TaskPriority.HIGH, "5", "Task 5",
            LocalDateTime.of(2024, Month.FEBRUARY, 2, 0, 0),
            LocalDateTime.of(2024, Month.JANUARY, 31, 0, 0), LocalDateTime.now(),
            TaskStatus.DONE);

    private static final TaskItemDomain TASK_SIX = new TaskItemDomain(6,
            TaskPriority.LOW, "6", "Task 6",
            LocalDateTime.of(2024, Month.FEBRUARY, 2, 0, 0),
            LocalDateTime.of(2024, Month.JANUARY, 31, 0, 0), LocalDateTime.now(),
            TaskStatus.DONE);

    private static List<TaskItemDomain> ALL_TASKS = new ArrayList<>(Arrays.asList(TASK_ONE, TASK_TWO, TASK_THREE, TASK_FOUR, TASK_FIVE, TASK_SIX));
    private static List<TaskItemDomain> ALL_TASKS_IN_PRIORITY = new ArrayList<>(Arrays.asList(TASK_ONE, TASK_THREE, TASK_FIVE, TASK_FOUR, TASK_TWO, TASK_SIX));
    private static List<TaskItemDomain> ALL_IN_PROGRESS_TASKS = new ArrayList<>(Arrays.asList(TASK_THREE, TASK_FOUR));
    private static List<TaskItemDomain> ALL_COMPLETED_TASKS = new ArrayList<>(Arrays.asList(TASK_FIVE, TASK_SIX));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskItemService taskItemService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    void testGetAllTasks_returnsOk() throws Exception {
//        when(taskItemService.getAll()).thenReturn(ALL_TASKS);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(MockMvcRequestBuilders.get("/api/v1/taskItems").accept(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
//
//        List<TaskItemDomain> content = Arrays.asList(objectMapper.readValue(response.getContentAsString(), TaskItemDomain[].class));
//        Assertions.assertEquals(content, ALL_TASKS);
//    }

//    @Test
//    void testGetTaskItemById_returnsOK() throws Exception {
//        when(taskItemService.getTaskItemById(1L)).thenReturn(TASK_ONE);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(MockMvcRequestBuilders.get("/api/v1/taskItems/1").accept(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
//
//        TaskItemDomain retrievedTaskItem = objectMapper.readValue(response.getContentAsString(), TaskItemDomain.class);
//        Assertions.assertEquals(retrievedTaskItem, TASK_ONE);
//    }

    @Test
    void testGetTaskItemById_returnsNotFound() throws Exception {
        when(taskItemService.getTaskItemById(0L)).thenThrow(new ResourceNotFoundException("Task Item Id 0 not found"));

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/taskItems/0").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
    }

//    @Test
//    void testCreateTaskItem_returnsCreated() throws Exception {
//        when(taskItemService.createTaskItem(any(TaskItemDomain.class))).thenReturn(TASK_TWO);
//
//        String task_Two_Json = objectMapper.writeValueAsString(TASK_TWO);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(MockMvcRequestBuilders.post("/api/v1/taskItems")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(task_Two_Json))
//                .andReturn()
//                .getResponse();
//
//        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
//        TaskItemDomain createdTaskItem = objectMapper.readValue(response.getContentAsString(), TaskItemDomain.class);
//        Assertions.assertEquals(createdTaskItem, TASK_TWO);
//    }

    @Test
    void testDeleteTaskItem_returnsOk() throws Exception {
        doNothing().when(taskItemService).deleteTaskItem(1L);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/v1/taskItems/1").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testDeleteTaskItem_returnsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Task Item Id 7 not found")).when(taskItemService).deleteTaskItem(7L);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/v1/taskItems/7").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
    }

//    @Test
//    void testGetAllTaskItemsInPriority_returnsOk() throws Exception {
//        when(taskItemService.getAllInPriorityOrder()).thenReturn(ALL_TASKS_IN_PRIORITY);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(MockMvcRequestBuilders.get("/api/v1/taskItems/priorityOrder").accept(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
//
//        List<TaskItemDomain> content = Arrays.asList(objectMapper.readValue(response.getContentAsString(), TaskItemDomain[].class));
//        Assertions.assertEquals(content, ALL_TASKS_IN_PRIORITY);
//    }

//    @Test
//    void testGetAllInProgressTaskItems_returnsOk() throws Exception {
//        when(taskItemService.getAllInProgress()).thenReturn(ALL_IN_PROGRESS_TASKS);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(MockMvcRequestBuilders.get("/api/v1/taskItems/inProgressTasks").accept(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
//
//        List<TaskItemDomain> content = Arrays.asList(objectMapper.readValue(response.getContentAsString(), TaskItemDomain[].class));
//        Assertions.assertEquals(content, ALL_IN_PROGRESS_TASKS);
//    }

//    @Test
//    void testGetAllCompletedTaskItems_returnsOk() throws Exception {
//        when(taskItemService.getAllCompleted()).thenReturn(ALL_COMPLETED_TASKS);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(MockMvcRequestBuilders.get("/api/v1/taskItems/completedTasks").accept(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
//
//        List<TaskItemDomain> content = Arrays.asList(objectMapper.readValue(response.getContentAsString(), TaskItemDomain[].class));
//        Assertions.assertEquals(content, ALL_COMPLETED_TASKS);
//    }
}
