package com.springarena.dto;

public class RecipeRequestDTO {
    private String title;
    private String description;
    private Integer cookingTime;
    private Integer servings;
    private String difficulty;

    public RecipeRequestDTO() {}

    public RecipeRequestDTO(String title, String description, Integer cookingTime, Integer servings, String difficulty) {
        this.title = title;
        this.description = description;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.difficulty = difficulty;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getCookingTime() { return cookingTime; }
    public void setCookingTime(Integer cookingTime) { this.cookingTime = cookingTime; }
    public Integer getServings() { return servings; }
    public void setServings(Integer servings) { this.servings = servings; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}
