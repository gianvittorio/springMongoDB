package com.gianvittorio.mongodb.springMongo.service;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProjectService {
    void saveProject(Project p);
    void saveTask(Task t);
    Project findProject(String id);
    Task findTask(String id);
    void deleteTask(String id);

    List<Project> findByName(String name);

    List<Project> findByNameNot(String name);

    List<Project> findByEstimatedCostGreaterThan(Long cost);

    List<Project> findByEstimatedCostBetween(Long from, Long to);

    List<Project> findByNameLike(String name);

    List<Project> findByNameRegex(String name);

    List<Project> findProjectByNameQuery(String name);

    List<Project> findProjectByNameAndCostQuery(String name, Long cost);

    List<Project> findByEstimatedCostBetweenQuery(Long from, Long to, Sort sort);

    List<Project> findByNameRegexQuery(String regex);

    List<Project> findProjectByNameQueryWithTemplate(String name);

    List<Project> findProjectByNameAndCostQueryWithTemplate(String name, Long cost);

    List<Project> findByEstimatedCostBetweenQueryWithTemplate(Long from, Long to);

    List<Project> findByNameRegexQueryWithTemplate(String regex);

    void upsertCostWithCriteriaTemplate(String id, Long cost);

    void deleteWithCriteriaTemplate(String id);

    Long findNoOfProjectsCostGreaterThan(Long cost);

    List<ResultByStartDateAndCost> findCostsGroupByStartDateForProjectsCostGreaterThan(Long cost);

    List<ResultProjectTasks> findAllProjectTasks();
}
