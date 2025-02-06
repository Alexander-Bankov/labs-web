package com.example.labs.model.dto;

import com.example.labs.model.entity.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskPojo {
    private long id;
    private String nameTask;
    private String descriptionTask;
    private LocalDate plannedFinishDate;
    private boolean isCompleted;

    public static TaskPojo fromEntity (Task task) {
        TaskPojo pojo = new TaskPojo();
        pojo.setId(task.getId());
        pojo.setNameTask(task.getNameTask());
        pojo.setDescriptionTask(task.getDescriptionTask());
        pojo.setPlannedFinishDate(task.getPlannedFinishDate());
        pojo.setCompleted(task.isCompleted());
        return pojo;
    }

    public static Task toEntity (TaskPojo pojo) {
        Task task = new Task();
        task.setId(pojo.getId());
        task.setNameTask(pojo.getNameTask());
        task.setDescriptionTask(pojo.getDescriptionTask());
        task.setPlannedFinishDate(pojo.getPlannedFinishDate());
        task.setCompleted(pojo.isCompleted());
        return task;
    }
}

