package com.springarena.dto;

public class StudentRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String grade;
    private Double gpa;

    public StudentRequestDTO() {}

    public StudentRequestDTO(String firstName, String lastName, String email, String grade, Double gpa) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.gpa = gpa;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
}
