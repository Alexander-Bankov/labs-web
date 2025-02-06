package com.example.labs.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "task", schema = "public")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskId")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "projectId",
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @JsonManagedReference
    private Project project;

    @Column(name = "nameTask")
    private String nameTask;

    @Column(name = "descriptionTask")
    private String descriptionTask;

    @Column(name = "plannedFinishDate")
    private LocalDate plannedFinishDate;

    @Column(name = "isCompleted")
    private boolean isCompleted;
}

