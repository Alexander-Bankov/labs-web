package com.example.labs.controller;

import com.example.labs.model.dto.TaskDto;
import com.example.labs.model.dto.TaskPojo;
import com.example.labs.model.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTasks (@PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(taskService.getAllTasks(projectId), HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById (@PathVariable("projectId") long projectId,
                                          @PathVariable("taskId") long taskId) {
        TaskPojo taskPojo = taskService.getTaskById(projectId, taskId);
        return new ResponseEntity<>(taskPojo, taskPojo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask (@PathVariable("projectId") long projectId,
                                         @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.createTaskForProject(projectId, taskDto), HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask (@PathVariable("projectId") long projectId,
                                         @PathVariable("taskId") long taskId,
                                         @RequestBody TaskDto taskDto) {
        TaskDto tDto = taskService.updateTaskByProjectId(projectId, taskId, taskDto);
        return new ResponseEntity<>(tDto, tDto == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTaskById (@PathVariable("projectId") long projectId,
                                             @PathVariable("taskId") long taskId) {
        taskService.deleteTaskById(projectId, taskId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/clean")
    public ResponseEntity<?> deleteCompletedTask (@PathVariable("projectId") long projectId) {
        taskService.deleteCompletedTaskByProjectId(projectId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
