package com.example.labs.model.dto;

import com.example.labs.model.other.Project;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Data
public class ProjectResponsePojo {
    private Long projectId;
    private String nameProject;
    private String descriptionProject;
    private LocalDate startDate;
    private LocalDate finishDate;

    public static ProjectResponsePojo fromEntity(Project project) {
        ProjectResponsePojo pojo = new ProjectResponsePojo();
        pojo.setProjectId(project.getProjectId());
        pojo.setNameProject(project.getNameProject());
        pojo.setDescriptionProject(project.getDescriptionProject());
        pojo.setStartDate(project.getStartDate());
        pojo.setFinishDate(project.getFinishDate());
        return pojo;
    }
}
