package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.Gadget;
import com.springarena.repository.GadgetRepository;
import com.springarena.service.GadgetService;
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
class GadgetServiceTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private GadgetRepository gadgetRepository;
    @Autowired private GadgetService gadgetService;

    @BeforeEach
    void setUp() { gadgetRepository.deleteAll(); }

    @Test @Order(1)
    @DisplayName("GadgetService bean should exist")
    void serviceShouldExist() { assertThat(gadgetService).isNotNull(); }

    @Test @Order(2)
    @DisplayName("POST /api/gadgets → should create and return 201")
    void shouldCreate() throws Exception {
        Gadget g = new Gadget(); g.setName("AirPods Pro"); g.setBrand("Apple"); g.setCategory("Audio"); g.setPrice(249.0); g.setAvailable(true);
        mockMvc.perform(post("/api/gadgets").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(g)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("AirPods Pro"));
    }

    @Test @Order(3)
    @DisplayName("GET /api/gadgets → should return all")
    void shouldGetAll() throws Exception {
        Gadget g1 = new Gadget(); g1.setName("G1"); g1.setBrand("B1"); g1.setCategory("C1"); g1.setPrice(10.0); g1.setAvailable(true);
        Gadget g2 = new Gadget(); g2.setName("G2"); g2.setBrand("B2"); g2.setCategory("C2"); g2.setPrice(20.0); g2.setAvailable(false);
        gadgetRepository.save(g1); gadgetRepository.save(g2);
        mockMvc.perform(get("/api/gadgets")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4)
    @DisplayName("GET /api/gadgets/{id} → should return by ID")
    void shouldGetById() throws Exception {
        Gadget g = new Gadget(); g.setName("Kindle"); g.setBrand("Amazon"); g.setCategory("Reader"); g.setPrice(139.0); g.setAvailable(true);
        Gadget saved = gadgetRepository.save(g);
        mockMvc.perform(get("/api/gadgets/" + saved.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Kindle"));
    }

    @Test @Order(5)
    @DisplayName("GET /api/gadgets/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/gadgets/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6)
    @DisplayName("PUT /api/gadgets/{id} → should update")
    void shouldUpdate() throws Exception {
        Gadget g = new Gadget(); g.setName("Old"); g.setBrand("Old"); g.setCategory("Old"); g.setPrice(1.0); g.setAvailable(false);
        Gadget saved = gadgetRepository.save(g);
        Gadget upd = new Gadget(); upd.setName("New Gadget"); upd.setBrand("New Brand"); upd.setCategory("New Cat"); upd.setPrice(99.0); upd.setAvailable(true);
        mockMvc.perform(put("/api/gadgets/" + saved.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("New Gadget"));
    }

    @Test @Order(7)
    @DisplayName("DELETE /api/gadgets/{id} → should delete and return 204")
    void shouldDelete() throws Exception {
        Gadget g = new Gadget(); g.setName("Temp"); g.setBrand("T"); g.setCategory("T"); g.setPrice(1.0); g.setAvailable(false);
        Gadget saved = gadgetRepository.save(g);
        mockMvc.perform(delete("/api/gadgets/" + saved.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/gadgets/" + saved.getId())).andExpect(status().isNotFound());
    }

    @Test @Order(8)
    @DisplayName("DELETE /api/gadgets/{id} → should return 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/gadgets/9999")).andExpect(status().isNotFound());
    }
}
