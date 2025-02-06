package com.example.labs.controller;

import com.example.labs.model.dto.ProjectPojo;
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
    public ResponseEntity<?> creationProject(@RequestBody ProjectPojo project) {
        if (service.createProject(project))
            return new ResponseEntity<>(project, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/{projectName}")
    public ResponseEntity<?> modifyProject(@PathVariable("projectName") String name, @RequestBody ProjectPojo project) {
        if (service.updateProject(name, project) == 1)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectName") String name) {
        service.deleteProjectByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<?> getProject(@PathVariable("projectName") String name) {
        ProjectPojo project = service.getProjectByName(name);
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
