package com.example.labs.model.service;

import com.example.labs.dao.IProjectDAO;
import com.example.labs.dao.ITaskDAO;
import com.example.labs.model.dto.TaskDto;
import com.example.labs.model.dto.TaskPojo;
import com.example.labs.model.entity.Project;
import com.example.labs.model.entity.Task;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ITaskDAO taskDAO;
    private final IProjectDAO projectDAO;

    public List<TaskPojo> getAllTasks (long projectId) {
        List<Task> tasks = taskDAO.findByProjectId(projectId);
        List<TaskPojo> taskPojos = new ArrayList<>(tasks.size());
        for (Task task : tasks)
            taskPojos.add(TaskPojo.fromEntity(task));
        return taskPojos;
    }

    public TaskPojo getTaskById (long projectId, long taskId) {
        try {
            Task returnedTask = taskDAO.findByIdAndProjectId (taskId, projectId);
            return TaskPojo.fromEntity(returnedTask);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public TaskDto createTaskForProject (long projectId, TaskDto taskDto) {
        Task task = TaskDto.toEntity(taskDto);
        Project project = projectDAO.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectId));

        task.setProject(project);
        taskDAO.save(task);
        return taskDto;
    }

    public TaskDto updateTaskByProjectId (long projectId, long taskId, TaskDto taskDto) {
        try {
            Task returnedTask = taskDAO.findByIdAndProjectId (taskId, projectId);
            returnedTask.setCompleted(taskDto.isCompleted());
            returnedTask.setDescriptionTask(taskDto.getDescriptionTask());
            returnedTask.setNameTask(taskDto.getNameTask());
            returnedTask.setPlannedFinishDate(taskDto.getPlannedFinishDate());
            taskDAO.save(returnedTask);
            return taskDto;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Transactional
    public void deleteTaskById (long projectId, long taskId) {
        taskDAO.deleteByIdAndProjectId(taskId, projectId);
    }

    @Transactional
    public void deleteCompletedTaskByProjectId (long projectId) {
        taskDAO.deleteAllByProjectIdAndIsCompletedTrue(projectId);
    }
}

