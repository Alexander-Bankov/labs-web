package com.example.labs.dao;


import com.example.labs.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ITaskDAO extends JpaRepository<Task, Long> {
    public List<Task> findByProjectId(long id);

    public void deleteAllByProjectIdAndIsCompletedTrue(long projectId);

    public void deleteAllByProjectId(long projectId);

    public Task findByIdAndProjectId(long id, long projectId);

    @Transactional
    public void deleteByIdAndProjectId(long id, long projectId);
}

