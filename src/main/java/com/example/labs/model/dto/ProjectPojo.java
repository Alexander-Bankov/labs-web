package com.example.labs.model.dto;

import com.example.labs.model.other.Project;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Data
public class ProjectPojo {
    private String nameProject;
    private String descriptionProject;
    private LocalDate startDate;
    private LocalDate finishDate;

    public static ProjectPojo fromEntity (Project project) {
        ProjectPojo pojo = new ProjectPojo();
        pojo.setDescriptionProject(project.getDescriptionProject());
        pojo.setFinishDate(project.getFinishDate());
        pojo.setNameProject(project.getNameProject());
        pojo.setStartDate(project.getStartDate());
        return pojo;
    }

    public static Project toEntity (ProjectPojo pojo) {
        Project project = new Project();
        project.setDescriptionProject(pojo.getDescriptionProject());
        project.setNameProject(pojo.getNameProject());
        project.setStartDate(pojo.getStartDate());
        project.setFinishDate(pojo.getFinishDate());
        return project;
    }
}
