package com.springarena.dto;

public class VehicleResponseDTO {
    private Long id;
    private String label;
    private String color;
    private Double price;

    public VehicleResponseDTO() {}

    public VehicleResponseDTO(Long id, String label, String color, Double price) {
        this.id = id;
        this.label = label;
        this.color = color;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
