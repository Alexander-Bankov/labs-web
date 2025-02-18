package com.example.labs.dao;

import com.example.labs.model.other.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectDAO {
    public Project createProject(Project project);
    public int modifyProject(Long projectid, Project project);
    public void deleteProject(Long projectid);

    public Project getProject(Long projectid);

    public List<Project> getProjectsWithFilter(LocalDate startTime, LocalDate finishTime);

    public List<Project> getAllProjects();
}

