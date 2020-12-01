package com.gianvittorio.mongodb.springMongo.service;

public class ResultByStartDateAndCost {
    private String startDate;
    private Long total;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
