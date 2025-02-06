package com.example.labs.model.service;

import com.example.labs.dao.ProjectDAOImpl;
import com.example.labs.model.dto.ProjectPojo;
import com.example.labs.model.other.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    @Autowired
    private ProjectDAOImpl projectDAO;

    public ProjectPojo getProjectByName(String nameProject) {
        Project project = projectDAO.getProject(nameProject);
        if (project != null)
            return ProjectPojo.fromEntity(project);
        else
            return null;
    }

    public boolean createProject(ProjectPojo pojo) {
        Project project = ProjectPojo.toEntity(pojo);
        return projectDAO.createProject(project);
    }

    public int updateProject (String name, ProjectPojo pojo) {
        Project project = ProjectPojo.toEntity(pojo);
        return projectDAO.modifyProject(name, project);
    }

    public void deleteProjectByName (String projectName) {
        projectDAO.deleteProject(projectName);
    }

    public List<ProjectPojo> getProjectsWithFilter (LocalDate start, LocalDate finish) {
        List<Project> resultList = projectDAO.getProjectsWithFilter(start, finish);
        List<ProjectPojo> convertedList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++)
            convertedList.add(ProjectPojo.fromEntity(resultList.get(i)));
        return convertedList;
    }

    public List<ProjectPojo> getAllProjects () {
        List<Project> resultList = projectDAO.getAllProjects();
        List<ProjectPojo> convertedList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++)
            convertedList.add(ProjectPojo.fromEntity(resultList.get(i)));
        return convertedList;
    }


}

