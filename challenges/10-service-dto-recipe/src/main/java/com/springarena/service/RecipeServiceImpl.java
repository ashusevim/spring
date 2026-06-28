package com.springarena.service;

import com.springarena.dto.RecipeRequestDTO;
import com.springarena.dto.RecipeResponseDTO;
import com.springarena.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<RecipeResponseDTO> getAllRecipes() {
        // TODO: Get all recipes from repository, map to ResponseDTOs
        return null;
    }

    @Override
    public Optional<RecipeResponseDTO> getRecipeById(Long id) {
        // TODO: Get recipe by id, map to ResponseDTO
        return Optional.empty();
    }

    @Override
    public RecipeResponseDTO createRecipe(RecipeRequestDTO dto) {
        // TODO: Map RequestDTO to Entity, save, map back to ResponseDTO
        return null;
    }

    @Override
    public Optional<RecipeResponseDTO> updateRecipe(Long id, RecipeRequestDTO dto) {
        // TODO: Update recipe if exists, mapping to and from DTOs
        return Optional.empty();
    }

    @Override
    public boolean deleteRecipe(Long id) {
        // TODO: Delete recipe by id and return true if existed, false otherwise
        return false;
    }
}
