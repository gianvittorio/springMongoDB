package com.gianvittorio.mongodb.springMongo.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;
import com.gianvittorio.mongodb.springMongo.repository.ProjectRepository;
import com.gianvittorio.mongodb.springMongo.repository.TaskRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

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

    @Override
    public Long findNoOfProjectsCostGreaterThan(Long cost) {
        MatchOperation matchStage = Aggregation.match(new Criteria("cost").gt(cost));
        CountOperation countStage = Aggregation.count().as("costly_projects");

        Aggregation aggregation
                = Aggregation.newAggregation(matchStage, countStage);
        AggregationResults<ResultCount> output
                = mongoTemplate.aggregate(aggregation, "project", ResultCount.class);

        return output.getMappedResults().get(0).getCostly_projects();
    }

    @Override
    public List<ResultByStartDateAndCost> findCostsGroupByStartDateForProjectsCostGreaterThan(Long cost) {

        MatchOperation filterCost = Aggregation.match(new Criteria("cost").gt(cost));
        GroupOperation groupByStartDateAndSumCost = Aggregation.group("startDate")
                .sum("cost").as("total");

        SortOperation sortByTotal = Aggregation.sort(Sort.by(Sort.Direction.DESC, "total"));

        Aggregation aggregation = Aggregation.newAggregation(
                filterCost, groupByStartDateAndSumCost, sortByTotal);
        AggregationResults<ResultByStartDateAndCost> result = mongoTemplate.aggregate(
                aggregation, "project", ResultByStartDateAndCost.class);
        return result.getMappedResults();
    }

    @Override
    public List<ResultProjectTasks> findAllProjectTasks() {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("task")
                .localField("_id")
                .foreignField("pid")
                .as("ProjectTasks");

        UnwindOperation unwindOperation = Aggregation.unwind("ProjectTasks");

        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("name").as("name")
                .andExpression("ProjectTasks.name").as("taskName")
                .andExpression("ProjectTasks.ownername").as("taskOwnerName");

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, projectionOperation);

        return mongoTemplate.aggregate(aggregation, "project", ResultProjectTasks.class)
                .getMappedResults();
    }

    @Override
    public List<Project> findNameDescriptionForMatchingTerm(String term) {
        Query query = TextQuery.queryText(
                TextCriteria.forDefaultLanguage()
                        .matching(term))
                .sortByScore()
                .with(Sort.by(Sort.Direction.DESC, "score"));

        return mongoOperations.find(query, Project.class);
    }

    @Override
    public List<Project> findNameDescriptionForMatchingAny(String... words) {
        Query query = TextQuery.queryText(
                TextCriteria.forDefaultLanguage()
                        .matchingAny(words))
                .sortByScore()
                .with(Sort.by(Sort.Direction.DESC, "score"));

        return mongoOperations.find(query, Project.class);
    }

    @Override
    public List<Project> findNameDescriptionForMatchingPhrase(String phrase) {
        Query query = TextQuery.queryText(
                TextCriteria.forDefaultLanguage()
                        .matchingPhrase(phrase))
                .sortByScore()
                .with(Sort.by(Sort.Direction.DESC, "score"));

        return mongoOperations.find(query, Project.class);
    }

    @Override
    @Transactional
    public void saveProjectAndTask(Project p, Task t) {
        taskRepository.save(t);
        projectRepository.save(p);
    }

    @Override
    public void chunkAndSaveProject(Project p) {
        String s = serializeToJson(p);

        InputStream targetStream = new ByteArrayInputStream(s.getBytes());

        DBObject metaData = new BasicDBObject();

        metaData.put("projectId", p.get_id());

        gridFsTemplate.store(targetStream, p.get_id(), metaData);
    }

    @Override
    public Project loadProjectFromGrid(String projectId) {
        GridFSFile file = gridFsTemplate.findOne(
                new Query().addCriteria(Criteria.where("metadata.projectId").is(projectId))
                        .with(Sort.by(Sort.Direction.DESC, "uploadDate"))
                        .limit(1)
        );

        Project p = null;

        if (file != null) {
            try {
                InputStream in = operations.getResource(file).getInputStream();

                String projectJson = new BufferedReader(
                        new InputStreamReader(in, StandardCharsets.UTF_8)
                )
                        .lines()
                        .collect(Collectors.joining("\n"));
                p = deserialize(projectJson);
            } catch (IOException e) {
                throw new RuntimeException("Project retrieval error " + projectId);
            }
        }

        return p;
    }

    @Override
    public void deleteProjectFromGrid(String projectId) {
        gridFsTemplate.delete(new Query(Criteria.where("metadata.ProjectId").is(projectId)));
    }

    private static String serializeToJson(Project p) {
        String s = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    private Project deserialize(String json) {
        Project p = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            p = objectMapper.readValue(json, Project.class);
        } catch (Exception i) {
            throw new RuntimeException(i);
        }

        return p;
    }
}
