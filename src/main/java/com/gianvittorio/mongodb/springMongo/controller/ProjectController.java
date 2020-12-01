package com.gianvittorio.mongodb.springMongo.controller;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;
import com.gianvittorio.mongodb.springMongo.service.ProjectService;
import com.gianvittorio.mongodb.springMongo.service.ResultByStartDateAndCost;
import com.gianvittorio.mongodb.springMongo.service.ResultProjectTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @GetMapping("/findNoOfProjectsCostGreaterThan")
    public ResponseEntity<Long> findNoOfProjectsCostGreaterThan(@RequestParam Long cost) {
        return ResponseEntity.ok(projectService.findNoOfProjectsCostGreaterThan(cost));
    }

    @GetMapping("/findCostsGroupByStartDateForProjectsCostGreaterThan")
    ResponseEntity<List<ResultByStartDateAndCost>> findCostsGroupByStartDateForProjectsCostGreaterThan(@RequestParam Long cost) {
        return ResponseEntity.ok(projectService.findCostsGroupByStartDateForProjectsCostGreaterThan(cost));
    }

    @GetMapping("/findAllProjectTasks")
    public ResponseEntity<List<ResultProjectTasks>> findAllProjectTasks() {
        return ResponseEntity.ok(projectService.findAllProjectTasks());
    }

    @GetMapping("/findNameDescriptionForMatchingTerm")
    public ResponseEntity<List<Project>> findNameDescriptionForMatchingTerm(@RequestParam String term) {
        return ResponseEntity.ok(projectService.findNameDescriptionForMatchingTerm(term));
    }

    @PostMapping("/findNameDescriptionForMatchingAny")
    public ResponseEntity<List<Project>> findNameDescriptionForMatchingAny(@RequestBody List<String> words) {
        String[] wordsArray = words.stream()
                .toArray(String[]::new);

        return ResponseEntity.ok(projectService.findNameDescriptionForMatchingAny(wordsArray));
    }

    @GetMapping("/findNameDescriptionForMatchingPhrase")
    public ResponseEntity<List<Project>> findNameDescriptionForMatchingPhrase(@RequestParam String phrase) {
        return ResponseEntity.ok(projectService.findNameDescriptionForMatchingPhrase(phrase));
    }

    @PostMapping("/saveProjectAndTask")
    public void saveProjectAndTask() {
        Project project = newProject();

        Task task = newTask();

        projectService.saveProjectAndTask(project, task);
    }

    private static Project newProject() {
        Project p = new Project();

        p.set_id("4");
        p.setCode("D");
        p.setCountryList(Arrays.asList("UK", "India"));
        p.setDescription("ProjectDescription");
        p.setStartDate("2020-01-01");
        p.setEndDate("2021-01-01");
        p.setEstimatedCost(5000);
        p.setName("ProjectD");

        return p;
    }

    private static Task newTask() {
        Task t = new Task();

        t.setId("8");
        t.setCost(3000);
        t.setDescription("taskDescription");
        t.setName("taskK");
        t.setOwnername("Tom");
        t.setProjectId("4");

        return t;
    }

    @PostMapping("/chunkAndSaveProject")
    public void chunkAndSaveProject() {
        Project p = new Project();

        p.set_id("5");
        p.setCode("E");
        p.setCountryList(Arrays.asList("UK", "India"));
        p.setDescription("ProjectDescription");
        p.setStartDate("2022-01-01");
        p.setEndDate("2023-01-01");
        p.setEstimatedCost(15000);
        p.setName("ProjectE");

        projectService.chunkAndSaveProject(p);
    }

    @GetMapping("/loadProjectFromGrid")
    public ResponseEntity<Project> loadProjectFromGrid(@RequestParam("project_id") String projectId) {
        return ResponseEntity.ok(projectService.loadProjectFromGrid(projectId));
    }

    @PostMapping("/deleteProjectFromGrid/{project_id}")
    public void deleteProjectFromGrid(@PathVariable("project_id") String projectId) {
        projectService.deleteProjectFromGrid(projectId);
    }
}

