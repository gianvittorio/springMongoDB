package com.gianvittorio.mongodb.springMongo.controller;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;
import com.gianvittorio.mongodb.springMongo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/findByName")
    public ResponseEntity<List<Project>> findByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findByName(name));
    }

    @GetMapping("/findByNameNot")
    public ResponseEntity<List<Project>> findByNameNot(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findByNameNot(name));
    }

    @GetMapping("/findByEstimatedCostGreaterThan")
    public ResponseEntity<List<Project>> findByEstimatedCostGreaterThan(@RequestParam Long cost) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findByEstimatedCostGreaterThan(cost));
    }

    @GetMapping("/findByEstimatedCostBetween")
    public ResponseEntity<List<Project>> findByEstimatedCostBetween(
            @RequestParam Long from,
            @RequestParam Long to
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findByEstimatedCostBetween(from, to));
    }

    @GetMapping("/findByNameLike")
    public ResponseEntity<List<Project>> findByNameLike(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findByNameLike(name));
    }

    @GetMapping("/findByNameRegex")
    public ResponseEntity<List<Project>> findByNameRegex(@RequestParam String name) {
        String regex = "^.*" + name + ".*$";

        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findByNameRegex(regex));
    }

    @GetMapping("/findProjectByNameQuery")
    public ResponseEntity<List<Project>> findProjectByNameQuery(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findProjectByNameQuery(name));
    }

    @GetMapping("/findProjectByNameAndCostQuery")
    public ResponseEntity<List<Project>> findProjectByNameAndCostQuery(
            @RequestParam String name,
            @RequestParam Long cost
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findProjectByNameAndCostQuery(name, cost));
    }

    @GetMapping("/findByEstimatedCostBetweenQuery")
    public ResponseEntity<List<Project>> findByEstimatedCostBetweenQuery(
            @RequestParam Long from,
            @RequestParam Long to
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findByEstimatedCostBetweenQuery(
                        from,
                        to,
                        Sort.by(Sort.Direction.DESC, "cost")
                        )
                );
    }

    @GetMapping("/findByNameRegexQuery")
    public ResponseEntity<List<Project>> findByNameRegexQuery(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.findByNameRegexQuery("^.*" + name + ".*$"));
    }

    @GetMapping("/findProjectByNameQueryWithTemplate")
    public ResponseEntity<List<Project>> findProjectByNameQueryWithTemplate(@RequestParam String name) {
        return ResponseEntity.ok(projectService.findProjectByNameQueryWithTemplate(name));
    }

    @GetMapping("/findProjectByNameAndCostQueryWithTemplate")
    public ResponseEntity<List<Project>> findProjectByNameAndCostQueryWithTemplate(
            @RequestParam String name,
            @RequestParam Long cost) {
        return ResponseEntity.ok(projectService.findProjectByNameAndCostQueryWithTemplate(name, cost));
    }

    @GetMapping("/findByEstimatedBetweenCostQueryWithTemplate")
    public ResponseEntity<List<Project>> findByEstimatedBetweenCostQueryWithTemplate(
            @RequestParam Long from,
            @RequestParam Long to) {
        return ResponseEntity.ok(projectService.findByEstimatedCostBetweenQueryWithTemplate(from, to));
    }

    @GetMapping("/findByNameRegexQueryWithTemplate")
    public ResponseEntity<List<Project>> findByNameRegexQueryWithTemplate(String name) {
        return ResponseEntity.ok(projectService.findByNameRegexQueryWithTemplate("^.*" + name + ".*$"));
    }

    @PostMapping("/upsertCostWithCriteriaTemplate/{id}/{cost}")
    public void upsertCostWithCriteriaTemplate(
            @PathVariable("id") String id,
            @PathVariable("cost") Long cost
    ) {
        projectService.upsertCostWithCriteriaTemplate(id, cost);
    }

    @DeleteMapping("/deleteWithCriteriaTemplate/{id}")
    public void deleteWithCriteriaTemplate(@PathVariable("id") String id) {
        projectService.deleteWithCriteriaTemplate(id);
    }
}

