package com.example.labs.controller;

import com.example.labs.model.dto.ProjectPojo;
import com.example.labs.model.dto.ProjectResponsePojo;
import com.example.labs.model.other.Project;
import com.example.labs.model.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private MainService service;

    @PostMapping
    public ResponseEntity<ProjectResponsePojo> creationProject(@RequestBody ProjectPojo project) {
        Project projectEntity = ProjectPojo.toEntity(project); // Преобразуем POJO в сущность
        Project createdProject = service.createProject(projectEntity); // Измените метод сервиса, чтобы принимать сущность

        if (createdProject != null) {
            return new ResponseEntity<>(ProjectResponsePojo.fromEntity(createdProject), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{projectid}")
    public ResponseEntity<?> modifyProject(@PathVariable("projectid") Long projectid, @RequestBody ProjectPojo project) {
        if (service.updateProject(projectid, project) == 1)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{projectid}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectid") Long projectid) {
        service.deleteProjectByName(projectid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{projectid}")
    public ResponseEntity<?> getProject(@PathVariable("projectid") Long projectid) {
        ProjectPojo project = service.getProjectByName(projectid);
        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getProjectWithFilter(@RequestParam("start_date")  LocalDate startDate,
                                                  @RequestParam("finish_date")  LocalDate finishDate) {

        return new ResponseEntity<>(service.getProjectsWithFilter(startDate, finishDate), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects() {
        return new ResponseEntity<>(service.getAllProjects(), HttpStatus.OK);
    }
}
