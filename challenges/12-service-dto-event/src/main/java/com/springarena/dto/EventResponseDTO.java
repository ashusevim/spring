package com.springarena.dto;

public class EventResponseDTO {
    private Long id;
    private String name;
    private String location;
    private String schedule;
    private String organizer;

    public EventResponseDTO() {}

    public EventResponseDTO(Long id, String name, String location, String schedule, String organizer) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.schedule = schedule;
        this.organizer = organizer;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }
}
