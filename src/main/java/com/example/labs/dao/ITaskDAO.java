package com.example.labs.dao;


import com.example.labs.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ITaskDAO extends JpaRepository<Task, Long> {
     List<Task> findByProjectId(long id);

     void deleteAllByProjectIdAndIsCompletedTrue(long projectId);

     void deleteAllByProjectId(long projectId);

     Task findByIdAndProjectId(long id, long projectId);

    @Transactional
    void deleteByIdAndProjectId(long id, long projectId);
}

