package com.gianvittorio.mongodb.springMongo.repository;

import com.gianvittorio.mongodb.springMongo.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
