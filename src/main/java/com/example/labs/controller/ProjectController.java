package com.example.labs.controller;

import com.example.labs.model.dto.ProjectPojo;
import com.example.labs.model.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getProjectByDescFilter (@RequestParam("search") Optional<String> phrase) {
        List<ProjectPojo> listPojos = projectService.getProjectByDescFilter(phrase);
        return new ResponseEntity<>(listPojos,
                listPojos == null || listPojos.size() == 0 ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable("projectId") long projectId) {
        ProjectPojo project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, project == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectPojo ProjectPojo) {
        return new ResponseEntity<>(projectService.createProject(ProjectPojo), HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProjectById(@PathVariable("projectId") long projectId,
                                               @RequestBody ProjectPojo projectPojo) {
        ProjectPojo updatedProject = projectService.updateProjectById(projectId, projectPojo);
        return new ResponseEntity<>(updatedProject, updatedProject == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable("projectId") long projectId) {
        projectService.deleteProjectById(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/open")
    public ResponseEntity<?> getOpenedTasks() {
        return new ResponseEntity<>(projectService.getOpenedTask(), HttpStatus.OK);
    }
}
