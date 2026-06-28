package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.Movie;
import com.springarena.repository.MovieRepository;
import com.springarena.service.MovieService;
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
class MovieServiceTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MovieRepository movieRepository;
    @Autowired private MovieService movieService;

    @BeforeEach
    void setUp() { movieRepository.deleteAll(); }

    @Test @Order(1)
    @DisplayName("MovieService bean should exist")
    void serviceShouldExist() { assertThat(movieService).isNotNull(); }

    @Test @Order(2)
    @DisplayName("POST /api/movies → should create and return 201")
    void shouldCreate() throws Exception {
        Movie m = new Movie(); m.setTitle("Inception"); m.setDirector("Christopher Nolan"); m.setYear(2010); m.setRating(8.8); m.setGenre("Sci-Fi");
        mockMvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(m)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.rating").value(8.8));
    }

    @Test @Order(3)
    @DisplayName("GET /api/movies → should return all")
    void shouldGetAll() throws Exception {
        Movie m1 = new Movie(); m1.setTitle("Movie A"); m1.setDirector("Dir A"); m1.setYear(2020); m1.setRating(7.0); m1.setGenre("Action");
        Movie m2 = new Movie(); m2.setTitle("Movie B"); m2.setDirector("Dir B"); m2.setYear(2021); m2.setRating(8.0); m2.setGenre("Drama");
        movieRepository.save(m1); movieRepository.save(m2);

        mockMvc.perform(get("/api/movies")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4)
    @DisplayName("GET /api/movies/{id} → should return by ID")
    void shouldGetById() throws Exception {
        Movie m = new Movie(); m.setTitle("The Matrix"); m.setDirector("Wachowskis"); m.setYear(1999); m.setRating(8.7); m.setGenre("Sci-Fi");
        Movie saved = movieRepository.save(m);
        mockMvc.perform(get("/api/movies/" + saved.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("The Matrix"));
    }

    @Test @Order(5)
    @DisplayName("GET /api/movies/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/movies/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6)
    @DisplayName("PUT /api/movies/{id} → should update")
    void shouldUpdate() throws Exception {
        Movie m = new Movie(); m.setTitle("Old"); m.setDirector("Dir"); m.setYear(2000); m.setRating(5.0); m.setGenre("Comedy");
        Movie saved = movieRepository.save(m);

        Movie upd = new Movie(); upd.setTitle("Updated"); upd.setDirector("New Dir"); upd.setYear(2024); upd.setRating(9.0); upd.setGenre("Thriller");
        mockMvc.perform(put("/api/movies/" + saved.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test @Order(7)
    @DisplayName("DELETE /api/movies/{id} → should delete and return 204")
    void shouldDelete() throws Exception {
        Movie m = new Movie(); m.setTitle("Temp"); m.setDirector("Temp"); m.setYear(2000); m.setRating(1.0); m.setGenre("Temp");
        Movie saved = movieRepository.save(m);
        mockMvc.perform(delete("/api/movies/" + saved.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/movies/" + saved.getId())).andExpect(status().isNotFound());
    }

    @Test @Order(8)
    @DisplayName("DELETE /api/movies/{id} → should return 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/movies/9999")).andExpect(status().isNotFound());
    }
}
