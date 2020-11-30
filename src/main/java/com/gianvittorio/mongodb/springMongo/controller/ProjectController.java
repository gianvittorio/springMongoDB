package com.gianvittorio.mongodb.springMongo.controller;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;
import com.gianvittorio.mongodb.springMongo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public void saveProject(@RequestBody Project p) {
        projectService.saveProject(p);
    }

    @PostMapping(path = "/saveTask")
    public void saveTask(@RequestBody Task t) {
        projectService.saveTask(t);
    }

    @PutMapping
    public void updateProject(Project p) {
        projectService.saveProject(p);
    }

    @GetMapping
    public ResponseEntity<Project> findProject(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findProject(id));
    }

    @GetMapping("/findTask")
    public ResponseEntity<Task> findTask(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findTask(id));
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<String> deleteTask(@RequestParam String id) {
        projectService.deleteTask(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(id);
    }
}

