package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.MenuItemRequestDTO;
import com.springarena.repository.MenuItemRepository;
import com.springarena.service.MenuItemService;
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
class MenuFullTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MenuItemRepository menuItemRepository;
    @Autowired private MenuItemService menuItemService;

    @BeforeEach
    void setUp() { menuItemRepository.deleteAll(); }

    private MenuItemRequestDTO createReq(String name, String cat, Double price, Boolean veg, String spice) {
        MenuItemRequestDTO dto = new MenuItemRequestDTO();
        dto.setName(name); dto.setCategory(cat); dto.setPrice(price); dto.setVegetarian(veg); dto.setSpiceLevel(spice);
        return dto;
    }

    @Test @Order(1) @DisplayName("Service should exist")
    void serviceShouldExist() { assertThat(menuItemService).isNotNull(); }

    @Test @Order(2) @DisplayName("POST → 201")
    void shouldCreate() throws Exception {
        MenuItemRequestDTO req = createReq("Paneer Tikka", "Appetizer", 12.99, true, "Medium");
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Paneer Tikka"));
    }

    @Test @Order(3) @DisplayName("GET → all items")
    void shouldGetAll() throws Exception {
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","Main",10.0,true,"Mild"))));
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","Dessert",8.0,true,"None"))));
        mockMvc.perform(get("/api/menu")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4) @DisplayName("GET /{id}")
    void shouldGetById() throws Exception {
        MenuItemRequestDTO req = createReq("Butter Chicken", "Main", 15.99, false, "Medium");
        String resp = mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(get("/api/menu/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Butter Chicken"));
    }

    @Test @Order(5) @DisplayName("GET /{id} → 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/menu/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6) @DisplayName("GET /category/{cat}")
    void shouldFilterByCategory() throws Exception {
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","Main",10.0,false,"Hot"))));
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","Main",12.0,true,"Mild"))));
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C","Dessert",8.0,true,"None"))));
        mockMvc.perform(get("/api/menu/category/Main")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(7) @DisplayName("GET /vegetarian → only vegetarian")
    void shouldFilterVegetarian() throws Exception {
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("Salad","Main",9.0,true,"None"))));
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("Steak","Main",25.0,false,"Mild"))));
        mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("Pasta","Main",14.0,true,"None"))));
        mockMvc.perform(get("/api/menu/vegetarian")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(8) @DisplayName("PUT → update")
    void shouldUpdate() throws Exception {
        MenuItemRequestDTO req = createReq("Old", "Old", 1.0, false, "None");
        String resp = mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        MenuItemRequestDTO upd = createReq("New Item", "Dessert", 9.99, true, "None");
        mockMvc.perform(put("/api/menu/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("New Item"));
    }

    @Test @Order(9) @DisplayName("PUT → 404")
    void shouldReturn404OnUpdate() throws Exception {
        mockMvc.perform(put("/api/menu/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("G","G",0.0,false,"G"))))
                .andExpect(status().isNotFound());
    }

    @Test @Order(10) @DisplayName("DELETE → 204")
    void shouldDelete() throws Exception {
        String resp = mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("T","T",1.0,false,"T"))))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(delete("/api/menu/" + id)).andExpect(status().isNoContent());
    }

    @Test @Order(11) @DisplayName("DELETE → 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/menu/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(12) @DisplayName("Vegetarian field should persist correctly")
    void vegetarianShouldPersist() throws Exception {
        MenuItemRequestDTO req = createReq("Veg Burger", "Main", 11.99, true, "Mild");
        String resp = mockMvc.perform(post("/api/menu").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(get("/api/menu/" + id)).andExpect(jsonPath("$.vegetarian").value(true));
    }
}
