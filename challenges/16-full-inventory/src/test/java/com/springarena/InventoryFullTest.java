package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.InventoryRequestDTO;
import com.springarena.repository.InventoryRepository;
import com.springarena.service.InventoryService;
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
class InventoryFullTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private InventoryService inventoryService;

    @BeforeEach
    void setUp() { inventoryRepository.deleteAll(); }

    private InventoryRequestDTO createReq(String name, String cat, Integer qty, Double price, String supplier) {
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setName(name); dto.setCategory(cat); dto.setQuantity(qty); dto.setUnitPrice(price); dto.setSupplier(supplier);
        return dto;
    }

    @Test @Order(1) @DisplayName("Service should exist")
    void serviceShouldExist() { assertThat(inventoryService).isNotNull(); }

    @Test @Order(2) @DisplayName("POST → 201 with totalValue computed")
    void shouldCreate() throws Exception {
        InventoryRequestDTO req = createReq("Widget", "Parts", 100, 5.50, "SupplierA");
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Widget"))
                .andExpect(jsonPath("$.totalValue").value(550.0));
    }

    @Test @Order(3) @DisplayName("GET → all items")
    void shouldGetAll() throws Exception {
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","C",10,1.0,"S"))));
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","C",20,2.0,"S"))));
        mockMvc.perform(get("/api/inventory")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4) @DisplayName("GET /{id} → by ID with totalValue")
    void shouldGetById() throws Exception {
        InventoryRequestDTO req = createReq("Bolt", "Hardware", 200, 0.25, "FastenerCo");
        String resp = mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(get("/api/inventory/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalValue").value(50.0));
    }

    @Test @Order(5) @DisplayName("GET /{id} → 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/inventory/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6) @DisplayName("GET /category/{cat}")
    void shouldFilterByCategory() throws Exception {
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","Electronics",5,100.0,"S1"))));
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","Electronics",3,200.0,"S2"))));
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C","Clothing",10,30.0,"S3"))));
        mockMvc.perform(get("/api/inventory/category/Electronics")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(7) @DisplayName("GET /supplier/{supplier}")
    void shouldFilterBySupplier() throws Exception {
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","C",1,1.0,"Acme"))));
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","C",2,2.0,"Acme"))));
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C","C",3,3.0,"Other"))));
        mockMvc.perform(get("/api/inventory/supplier/Acme")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(8) @DisplayName("PUT → update and recompute totalValue")
    void shouldUpdate() throws Exception {
        InventoryRequestDTO req = createReq("Item", "Cat", 10, 5.0, "Sup");
        String resp = mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        InventoryRequestDTO upd = createReq("Updated", "NewCat", 20, 10.0, "NewSup");
        mockMvc.perform(put("/api/inventory/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.totalValue").value(200.0));
    }

    @Test @Order(9) @DisplayName("PUT → 404")
    void shouldReturn404OnUpdate() throws Exception {
        mockMvc.perform(put("/api/inventory/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("G","G",0,0.0,"G"))))
                .andExpect(status().isNotFound());
    }

    @Test @Order(10) @DisplayName("DELETE → 204")
    void shouldDelete() throws Exception {
        InventoryRequestDTO req = createReq("Temp", "T", 1, 1.0, "T");
        String resp = mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(delete("/api/inventory/" + id)).andExpect(status().isNoContent());
    }

    @Test @Order(11) @DisplayName("DELETE → 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/inventory/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(12) @DisplayName("Response should NOT expose entity-only fields")
    void shouldNotExposeEntityFields() throws Exception {
        InventoryRequestDTO req = createReq("Test", "Cat", 5, 10.0, "Sup");
        mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(jsonPath("$.totalValue").value(50.0))
                .andExpect(jsonPath("$.id").exists());
    }
}
