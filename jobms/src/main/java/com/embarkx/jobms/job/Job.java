package com.embarkx.jobms.job;

import jakarta.persistence.*;

@Entity
//@Table(name = "job_table")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // this is to auto increment the id. JPA now manages the id.
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
//    many jobs can belong to one company
    private Long companyId;

    public Job(String title, String description, String minSalary, String maxSalary, String location, Long id) {
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
        this.id = id;
    }

//    needed for JPA because it needs to create an instance of the class
    public Job() {

    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
