package com.gianvittorio.mongodb.springMongo.service;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;

public interface ProjectService {
    void saveProject(Project p);
    void saveTask(Task t);
    Project findProject(String id);
    Task findTask(String id);
    void deleteTask(String id);
}
