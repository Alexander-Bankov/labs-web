package com.example.labs.model.other;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Data
public class Project {
    private Long projectId; // Добавляем идентификатор проекта
    private String nameProject;
    private String descriptionProject;
    private LocalDate startDate;
    private LocalDate finishDate;
}
