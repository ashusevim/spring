package com.springarena.controller;

import com.springarena.dto.RecipeRequestDTO;
import com.springarena.dto.RecipeResponseDTO;
import com.springarena.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<RecipeResponseDTO> getAllRecipes() {
        // TODO: Return all recipes as ResponseDTOs via recipeService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> getRecipeById(@PathVariable Long id) {
        // TODO: Return RecipeResponseDTO by ID or 404
        return null;
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDTO> createRecipe(@RequestBody RecipeRequestDTO dto) {
        // TODO: Create recipe and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> updateRecipe(@PathVariable Long id, @RequestBody RecipeRequestDTO dto) {
        // TODO: Update recipe and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        // TODO: Delete recipe and return 204 or 404
        return null;
    }
}
