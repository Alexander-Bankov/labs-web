package com.example.labs.dao;

import com.example.labs.model.other.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProjectDAOImpl implements ProjectDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<Project> projectMapper = (rs, rowNum) -> {
        Project project = new Project();
        project.setNameProject(rs.getString("nameProject"));
        project.setDescriptionProject(rs.getString("descriptionProject"));
        project.setStartDate(rs.getDate("startDate").toLocalDate());
        project.setFinishDate(rs.getDate("finishDate").toLocalDate());
        return project;
    };

    public boolean createProject(Project project) {
        try {
            int result = jdbcTemplate.update("insert into Project (nameProject, descriptionProject, startDate, finishDate) values (?, ?, ?, ?)",
                    project.getNameProject(), project.getDescriptionProject(), project.getStartDate(), project.getFinishDate());
            if (result == 1)
                return true;
            else
                return false;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    public int modifyProject(String name, Project project) {
        return jdbcTemplate.update("update Project SET nameProject = ?, descriptionProject = ?, startDate = ?, finishDate = ? WHERE nameProject = ?",
                new Object[] { project.getNameProject(), project.getDescriptionProject(), project.getStartDate(), project.getFinishDate(), name }
        );
    }

    public void deleteProject(String nameProject) {
        Map<String, String> param = Map.of("nameProject", nameProject);
        namedParameterJdbcTemplate.update("DELETE from Project where nameProject = :nameProject", param);
    }

    public Project getProject(String nameProject) {
        List<Project> resultList = jdbcTemplate.query("select * from project where nameproject = ?",
                projectMapper,
                new Object[] { nameProject }
        );
        if (resultList.size() == 0)
            return null;
        else
            return resultList.get(0);
    }

    public List<Project> getProjectsWithFilter(LocalDate startTime, LocalDate finishTime) {
        return jdbcTemplate.query("select * from project where startdate > ? and finishdate < ?",
                projectMapper,
                new Object[] { startTime, finishTime }
        );
    }

    public List<Project> getAllProjects() {
        return jdbcTemplate.query("select * from project", projectMapper);
    }

}
