package com.example.labs.dao;

import com.example.labs.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectDAO extends JpaRepository<Project, Long> {

    List<Project> findByNameProjectIsContainingIgnoreCaseOrDescriptionProjectIsContainingIgnoreCase(String pattern0,
                                                                                                    String pattern1);

    @Query("SELECT p.id, (SELECT COUNT(t) FROM Task t WHERE t.isCompleted = False AND p.id = t.project.id) FROM Project p")
    public List<Object[]> findProjectsAndTaskCount();
}
