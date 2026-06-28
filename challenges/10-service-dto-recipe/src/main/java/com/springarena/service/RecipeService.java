package com.springarena.service;

import com.springarena.dto.RecipeRequestDTO;
import com.springarena.dto.RecipeResponseDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<RecipeResponseDTO> getAllRecipes();
    Optional<RecipeResponseDTO> getRecipeById(Long id);
    RecipeResponseDTO createRecipe(RecipeRequestDTO dto);
    Optional<RecipeResponseDTO> updateRecipe(Long id, RecipeRequestDTO dto);
    boolean deleteRecipe(Long id);
}
