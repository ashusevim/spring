package com.springarena.dto;

public class PatientResponseDTO {
    private Long id;
    private String fullName;
    private Integer age;
    private String condition;
    private String doctor;

    public PatientResponseDTO() {}

    public PatientResponseDTO(Long id, String fullName, Integer age, String condition, String doctor) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.condition = condition;
        this.doctor = doctor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }
}
