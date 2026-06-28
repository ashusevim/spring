package com.springarena.dto;

public class PatientRequestDTO {
    private String firstName;
    private String lastName;
    private Integer age;
    private String condition;
    private String doctor;

    public PatientRequestDTO() {}

    public PatientRequestDTO(String firstName, String lastName, Integer age, String condition, String doctor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.condition = condition;
        this.doctor = doctor;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }
}
