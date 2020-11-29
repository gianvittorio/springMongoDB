package com.gianvittorio.mongodb.springMongo;

import com.gianvittorio.mongodb.springMongo.repository.ProjectRepository;
import com.gianvittorio.mongodb.springMongo.repository.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {ProjectRepository.class, TaskRepository.class})
public class SpringMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoApplication.class, args);
    }

}
