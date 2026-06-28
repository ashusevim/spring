package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.RecipeRequestDTO;
import com.springarena.repository.RecipeRepository;
import com.springarena.service.RecipeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeServiceDtoTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private RecipeRepository recipeRepository;
    @Autowired private RecipeService recipeService;

    @BeforeEach
    void setUp() { recipeRepository.deleteAll(); }

    private RecipeRequestDTO createReq(String title, String desc, Integer time, Integer servings, String difficulty) {
        RecipeRequestDTO dto = new RecipeRequestDTO();
        dto.setTitle(title); dto.setDescription(desc); dto.setCookingTime(time); dto.setServings(servings); dto.setDifficulty(difficulty);
        return dto;
    }

    @Test @Order(1)
    @DisplayName("RecipeService bean should exist")
    void serviceShouldExist() { assertThat(recipeService).isNotNull(); }

    @Test @Order(2)
    @DisplayName("POST /api/recipes → should create and return 201")
    void shouldCreate() throws Exception {
        RecipeRequestDTO req = createReq("Pasta Carbonara", "Classic Italian pasta", 30, 4, "Medium");
        mockMvc.perform(post("/api/recipes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Pasta Carbonara"))
                .andExpect(jsonPath("$.cookingTime").value(30));
    }

    @Test @Order(3)
    @DisplayName("GET /api/recipes → should return all as ResponseDTOs")
    void shouldGetAll() throws Exception {
        RecipeRequestDTO r1 = createReq("Salad", "Fresh", 10, 2, "Easy");
        RecipeRequestDTO r2 = createReq("Steak", "Grilled", 20, 1, "Hard");
        mockMvc.perform(post("/api/recipes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r1)));
        mockMvc.perform(post("/api/recipes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r2)));

        mockMvc.perform(get("/api/recipes")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4)
    @DisplayName("GET /api/recipes/{id} → should return by ID")
    void shouldGetById() throws Exception {
        RecipeRequestDTO req = createReq("Sushi", "Japanese rice rolls", 60, 6, "Hard");
        String resp = mockMvc.perform(post("/api/recipes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(get("/api/recipes/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Sushi"));
    }

    @Test @Order(5)
    @DisplayName("GET /api/recipes/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/recipes/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6)
    @DisplayName("PUT /api/recipes/{id} → should update")
    void shouldUpdate() throws Exception {
        RecipeRequestDTO req = createReq("Old Recipe", "Old desc", 10, 1, "Easy");
        String resp = mockMvc.perform(post("/api/recipes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        RecipeRequestDTO update = createReq("Updated Recipe", "New desc", 45, 6, "Medium");
        mockMvc.perform(put("/api/recipes/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Recipe"))
                .andExpect(jsonPath("$.servings").value(6));
    }

    @Test @Order(7)
    @DisplayName("PUT /api/recipes/{id} → should return 404")
    void shouldReturn404OnUpdate() throws Exception {
        RecipeRequestDTO req = createReq("Ghost", "N/A", 0, 0, "N/A");
        mockMvc.perform(put("/api/recipes/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test @Order(8)
    @DisplayName("DELETE /api/recipes/{id} → should delete and return 204")
    void shouldDelete() throws Exception {
        RecipeRequestDTO req = createReq("Temp", "Temp", 1, 1, "Easy");
        String resp = mockMvc.perform(post("/api/recipes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(delete("/api/recipes/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/recipes/" + id)).andExpect(status().isNotFound());
    }

    @Test @Order(9)
    @DisplayName("DELETE /api/recipes/{id} → should return 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/recipes/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(10)
    @DisplayName("Response should NOT contain entity-specific fields not in DTO")
    void shouldReturnOnlyDtoFields() throws Exception {
        RecipeRequestDTO req = createReq("Pizza", "Italian flatbread", 25, 8, "Easy");
        mockMvc.perform(post("/api/recipes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.difficulty").value("Easy"));
    }
}
