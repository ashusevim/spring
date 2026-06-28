package com.springarena.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String instructor;
    private String category;
    private Integer maxStudents;
    private Integer enrolledStudents;
    private Double price;

    public Course() {}

    public Course(Long id, String title, String instructor, String category, Integer maxStudents, Integer enrolledStudents, Double price) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.category = category;
        this.maxStudents = maxStudents;
        this.enrolledStudents = enrolledStudents;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getMaxStudents() { return maxStudents; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }
    public Integer getEnrolledStudents() { return enrolledStudents; }
    public void setEnrolledStudents(Integer enrolledStudents) { this.enrolledStudents = enrolledStudents; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
