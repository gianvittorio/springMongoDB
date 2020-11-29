package com.gianvittorio.mongodb.springMongo.service;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;

public interface ProjectService {
    public void saveProject(Project p);
    public void saveTask(Task t);
}
