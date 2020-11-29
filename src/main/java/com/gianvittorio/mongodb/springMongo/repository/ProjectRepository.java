package com.gianvittorio.mongodb.springMongo.repository;

import com.gianvittorio.mongodb.springMongo.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
