package com.example.labs.model.service;

import com.example.labs.dao.IProjectDAO;
import com.example.labs.dao.ITaskDAO;
import com.example.labs.model.dto.ProjectPojo;
import com.example.labs.model.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final IProjectDAO projectDAO;
    private final ITaskDAO taskDAO;

    public List<ProjectPojo> getProjectByDescFilter (Optional<String> phrase) {
        System.out.println(phrase);
        List<ProjectPojo> listPojos = new ArrayList<>();
        if (phrase.isEmpty()) {
            List<Project> result = projectDAO.findAll();
            for (int i = 0; i < result.size(); i++)
                listPojos.add(ProjectPojo.fromEntity(result.get(i)));
            return listPojos;
        }

        List<Project> resultList = projectDAO.findByNameProjectIsContainingIgnoreCaseOrDescriptionProjectIsContainingIgnoreCase(phrase.get(), phrase.get());
        for (int i = 0; i < resultList.size(); i++)
            listPojos.add(ProjectPojo.fromEntity(resultList.get(i)));
        return listPojos;
    }

    public ProjectPojo getProjectById(long projectId) {
        try {
            Project project = projectDAO.findById(projectId).get();
            return ProjectPojo.fromEntity(project);
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    public ProjectPojo createProject(ProjectPojo projectPojo) {
        Project project = ProjectPojo.toEntity(projectPojo);
        project.setTasks(new ArrayList<>());
        return ProjectPojo.fromEntity(projectDAO.save(project));
    }

    public ProjectPojo updateProjectById(long projectId, ProjectPojo projectPojo) {
        Project project = ProjectPojo.toEntity(projectPojo); // I think it's needn't
        Optional<Project> oldProject = projectDAO.findById(projectId);
        if (oldProject.isPresent()) {
            oldProject.get().setNameProject(project.getNameProject());
            oldProject.get().setDescriptionProject(project.getDescriptionProject());
            oldProject.get().setTasks(project.getTasks());
            oldProject.get().setFinishDate(project.getFinishDate());
            oldProject.get().setStartDate(project.getStartDate());
            projectDAO.save(oldProject.get());
            return ProjectPojo.fromEntity(oldProject.get());
        }
        return null;
    }

    public void deleteProjectById(long projectId) {
        projectDAO.deleteById(projectId);
        taskDAO.deleteAllByProjectId(projectId);
    }

    public HashMap<Long, Long> getOpenedTask() {
        List<Object[]> result = projectDAO.findProjectsAndTaskCount();
        HashMap<Long, Long> openedTaskDict = new HashMap<>(result.size());
        for (int i = 0; i < result.size(); i++) {
            long key = ((Long) result.get(i)[0]).longValue();
            long value = ((Long) result.get(i)[1]).longValue();
            openedTaskDict.put(key, value);
        }
        return openedTaskDict;
    }

}
