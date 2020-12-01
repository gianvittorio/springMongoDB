package com.gianvittorio.mongodb.springMongo.service;

public class ResultProjectTasks {
    private String id;
    private String name;
    private String taskName;
    private String taskOwnername;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskOwnername() {
        return taskOwnername;
    }

    public void setTaskOwnername(String taskOwnername) {
        this.taskOwnername = taskOwnername;
    }
}
