package com.example.labs.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "project", schema = "public")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projectId")
    private long id;

    @Column(name = "nameProject")
    private String nameProject;

    @Column(name = "descriptionProject")
    private String descriptionProject;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "finishDate")
    private LocalDate finishDate;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Task> tasks;
}

