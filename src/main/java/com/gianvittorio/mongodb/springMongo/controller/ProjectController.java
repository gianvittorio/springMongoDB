package com.gianvittorio.mongodb.springMongo.controller;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;
import com.gianvittorio.mongodb.springMongo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

