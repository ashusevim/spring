package com.springarena.dto;

public class EmployeeResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String department;
    private Double salary;

    public EmployeeResponseDTO() {}

    public EmployeeResponseDTO(Long id, String fullName, String email, String department, Double salary) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
}
