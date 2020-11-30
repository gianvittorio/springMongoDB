package com.gianvittorio.mongodb.springMongo.service;

import com.gianvittorio.mongodb.springMongo.model.Project;
import com.gianvittorio.mongodb.springMongo.model.Task;
import com.gianvittorio.mongodb.springMongo.repository.ProjectRepository;
import com.gianvittorio.mongodb.springMongo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

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
}
