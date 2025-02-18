package com.example.labs.dao;

import com.example.labs.model.other.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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

    public Project createProject(Project project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO Project (nameProject, descriptionProject, startDate, finishDate) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, project.getNameProject());
                ps.setString(2, project.getDescriptionProject());
                ps.setObject(3, project.getStartDate());
                ps.setObject(4, project.getFinishDate());
                return ps;
            }, keyHolder);

            // Используйте getKeyList() для более чем одного ключа
            List<Map<String, Object>> keys = keyHolder.getKeyList();
            if (keys != null && keys.size() > 0) {
                project.setProjectId(((Number) keys.get(0).get("projectid")).longValue()); // Измените на свое имя поля
            }

            return project;
        } catch (DuplicateKeyException e) {
            return null; // В случае конфликта возвращаем null
        }
    }

    public int modifyProject(Long projectid, Project project) {
        return jdbcTemplate.update("update Project SET nameProject = ?, descriptionProject = ?, startDate = ?, finishDate = ? WHERE nameProject = ?",
                new Object[] { project.getNameProject(), project.getDescriptionProject(), project.getStartDate(), project.getFinishDate(), projectid }
        );
    }

    public void deleteProject(Long projectid) {
        Map<String, Long> param = Map.of("projectid", projectid);
        namedParameterJdbcTemplate.update("DELETE from Project where projectid = :projectid", param);
    }

    public Project getProject(Long projectid) {
        List<Project> resultList = jdbcTemplate.query("select * from project where projectid = ?",
                projectMapper,
                projectid
        );
        if (resultList.size() == 0)
            return null;
        else
            return resultList.get(0);
    }

    public List<Project> getProjectsWithFilter(LocalDate startTime, LocalDate finishTime) {
        return jdbcTemplate.query("select * from project where startdate > ? and finishdate < ?",
                projectMapper,
                startTime, finishTime
        );
    }

    public List<Project> getAllProjects() {
        return jdbcTemplate.query("select * from project", projectMapper);
    }

}
