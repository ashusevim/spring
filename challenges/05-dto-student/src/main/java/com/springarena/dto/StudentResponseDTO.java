package com.springarena.dto;

public class StudentResponseDTO {
    private Long id;
    private String displayName;
    private String email;
    private Double gpa;

    public StudentResponseDTO() {}

    public StudentResponseDTO(Long id, String displayName, String email, Double gpa) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.gpa = gpa;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
}
