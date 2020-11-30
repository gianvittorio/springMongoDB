package com.gianvittorio.mongodb.springMongo.service;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;
import com.gianvittorio.mongodb.springMongo.repository.ProjectRepository;
import com.gianvittorio.mongodb.springMongo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveProject(Project p) {
        projectRepository.save(p);
    }

    @Override
    public void saveTask(Task t) {
        taskRepository.save(t);
    }

    @Override
    public Project findProject(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public Task findTask(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public void deleteTask(String id) {
        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(t);
    }

    @Override
    public List<Project> findByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    public List<Project> findByNameNot(String name) {
        return projectRepository.findByNameNot(name);
    }

    @Override
    public List<Project> findByEstimatedCostGreaterThan(Long cost) {
        return projectRepository.findByEstimatedCostGreaterThan(cost);
    }

    @Override
    public List<Project> findByEstimatedCostBetween(Long from, Long to) {
        return projectRepository.findByEstimatedCostBetween(from, to);
    }

    @Override
    public List<Project> findByNameLike(String name) {
        return projectRepository.findByNameLike(name);
    }

    @Override
    public List<Project> findByNameRegex(String name) {
        return projectRepository.findByNameRegex(name);
    }

    @Override
    public List<Project> findProjectByNameQuery(String name) {
        return projectRepository.findProjectByNameQuery(name);
    }

    @Override
    public List<Project> findProjectByNameAndCostQuery(String name, Long cost) {
        return projectRepository.findProjectByNameAndCostQuery(name, cost);
    }

    @Override
    public List<Project> findByEstimatedCostBetweenQuery(Long from, Long to, Sort sort) {
        return projectRepository.findByEstimatedCostBetweenQuery(
                from,
                to,
                sort
        );
    }

    @Override
    public List<Project> findByNameRegexQuery(String regex) {
        return projectRepository.findByNameRegexQuery(regex);
    }

    @Override
    public List<Project> findProjectByNameQueryWithTemplate(String name) {
        Query query = new Query();

        query.addCriteria(Criteria.where("name").is(name));

        return mongoTemplate.find(query, Project.class);
    }

    @Override
    public List<Project> findProjectByNameAndCostQueryWithTemplate(String name, Long cost) {
        Query query = new Query()
                .addCriteria(Criteria.where("name").is(name))
                .addCriteria(Criteria.where("cost").is(cost));

        return mongoTemplate.find(query, Project.class);
    }

    @Override
    public List<Project> findByEstimatedCostBetweenQueryWithTemplate(Long from, Long to) {
        Query query = new Query()
                .with(Sort.by(Sort.Direction.ASC, "cost"))
                .addCriteria(Criteria.where("cost").gt(from).lt(to));

        return mongoTemplate.find(query, Project.class);
    }

    @Override
    public List<Project> findByNameRegexQueryWithTemplate(String regex) {
        Query query = new Query()
                .addCriteria(Criteria.where("name").regex(regex));

        return mongoTemplate.find(query, Project.class);
    }

    @Override
    public void upsertCostWithCriteriaTemplate(String id, Long cost) {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(id));

        Update update = new Update()
                .set("cost", cost);

        mongoTemplate.upsert(query, update, Project.class);
    }

    @Override
    public void deleteWithCriteriaTemplate(String id) {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(id));

        mongoTemplate.remove(query, Project.class);
    }
}
