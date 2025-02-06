package com.example.labs.model.dto;

import com.example.labs.model.entity.Task;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private String nameTask;
    private String descriptionTask;
    private LocalDate plannedFinishDate;
    private boolean isCompleted;

    public static TaskDto fromEntity (Task task) {
        TaskDto dto = new TaskDto();
        dto.setNameTask(task.getNameTask());
        dto.setDescriptionTask(task.getDescriptionTask());
        dto.setPlannedFinishDate(task.getPlannedFinishDate());
        dto.setCompleted(task.isCompleted());
        return dto;
    }

    public static Task toEntity (TaskDto dto) {
        Task task = new Task();
        task.setNameTask(dto.getNameTask());
        task.setDescriptionTask(dto.getDescriptionTask());
        task.setPlannedFinishDate(dto.getPlannedFinishDate());
        task.setCompleted(dto.isCompleted());
        return task;
    }
}
