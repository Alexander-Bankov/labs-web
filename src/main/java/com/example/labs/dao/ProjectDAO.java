package com.example.labs.dao;

import com.example.labs.model.other.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectDAO {
    public boolean createProject(Project project);
    public int modifyProject(String name, Project project);
    public void deleteProject(String nameProject);

    public Project getProject(String nameProject);

    public List<Project> getProjectsWithFilter(LocalDate startTime, LocalDate finishTime);

    public List<Project> getAllProjects();
}

