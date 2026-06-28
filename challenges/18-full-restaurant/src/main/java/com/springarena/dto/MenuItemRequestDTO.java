package com.springarena.dto;

public class MenuItemRequestDTO {
    private String name;
    private String category;
    private Double price;
    private boolean vegetarian;
    private String spiceLevel;

    public MenuItemRequestDTO() {}

    public MenuItemRequestDTO(String name, String category, Double price, boolean vegetarian, String spiceLevel) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.vegetarian = vegetarian;
        this.spiceLevel = spiceLevel;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public boolean isVegetarian() { return vegetarian; }
    public void setVegetarian(boolean vegetarian) { this.vegetarian = vegetarian; }
    public String getSpiceLevel() { return spiceLevel; }
    public void setSpiceLevel(String spiceLevel) { this.spiceLevel = spiceLevel; }
}
