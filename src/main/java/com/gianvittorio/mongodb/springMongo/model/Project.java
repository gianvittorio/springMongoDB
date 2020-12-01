package com.gianvittorio.mongodb.springMongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.util.List;

@Document(collection = "project")
@CompoundIndex(def = "{ 'name' : 1, 'cost' : 1 }", name = "nameCostIndex")
public class Project {
    @Id
    private String _id;

    @Indexed(name = "nameIndex", direction = IndexDirection.ASCENDING)
    @TextIndexed(weight = 2)
    private String name;

    private String code;

    @Field("desc")
    @TextIndexed(weight = 8)
    private String description;
    private String startDate;
    private String endDate;
    @Field("cost")
    private long estimatedCost;

    private List<String> countryList;
    @Version
    private Long version;

    @TextScore
    private float score;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(long estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public List<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<String> countryList) {
        this.countryList = countryList;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
