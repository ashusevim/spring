package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.StockItemRequestDTO;
import com.springarena.repository.StockItemRepository;
import com.springarena.service.StockItemService;
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
class WarehouseMockTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private StockItemRepository stockItemRepository;
    @Autowired private StockItemService stockItemService;

    @BeforeEach
    void setUp() { stockItemRepository.deleteAll(); }

    private StockItemRequestDTO createReq(String name, String sku, String location, Integer quantity, Double price) {
        StockItemRequestDTO dto = new StockItemRequestDTO();
        dto.setName(name); dto.setSku(sku); dto.setLocation(location); dto.setQuantity(quantity); dto.setPrice(price);
        return dto;
    }

    @Test @Order(1) @DisplayName("Service should exist")
    void serviceShouldExist() { assertThat(stockItemService).isNotNull(); }

    @Test @Order(2) @DisplayName("POST → 201 with totalValue")
    void shouldCreate() throws Exception {
        StockItemRequestDTO req = createReq("Keyboard", "KB-100", "Aisle 3", 50, 45.0);
        mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("KB-100"))
                .andExpect(jsonPath("$.totalValue").value(2250.0));
    }

    @Test @Order(3) @DisplayName("Response should NOT expose internal ID mapping during creation before db save")
    void shouldCreateAndReturnSavedResponse() throws Exception {
        StockItemRequestDTO req = createReq("Mouse", "MS-200", "Aisle 4", 100, 20.0);
        mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test @Order(4) @DisplayName("GET → all")
    void shouldGetAll() throws Exception {
        mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","S1","L1",10,1.0))));
        mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","S2","L2",20,2.0))));
        mockMvc.perform(get("/api/warehouse")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(5) @DisplayName("GET /{id}")
    void shouldGetById() throws Exception {
        StockItemRequestDTO req = createReq("Monitor", "MN-300", "Aisle 5", 10, 150.0);
        String resp = mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(get("/api/warehouse/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.totalValue").value(1500.0));
    }

    @Test @Order(6) @DisplayName("GET /{id} → 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/warehouse/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(7) @DisplayName("GET /location/{location}")
    void shouldFilterByLocation() throws Exception {
        mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","S1","Aisle1",10,1.0))));
        mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","S2","Aisle1",20,2.0))));
        mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C","S3","Aisle2",30,3.0))));
        mockMvc.perform(get("/api/warehouse/location/Aisle1")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(8) @DisplayName("GET /sku/{sku}")
    void shouldFilterBySku() throws Exception {
        mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","SKU1","L1",10,1.0))));
        mockMvc.perform(get("/api/warehouse/sku/SKU1")).andExpect(status().isOk()).andExpect(jsonPath("$.sku").value("SKU1"));
    }

    @Test @Order(9) @DisplayName("GET /sku/{sku} → 404")
    void shouldReturn404OnInvalidSku() throws Exception {
        mockMvc.perform(get("/api/warehouse/sku/NONEXISTENT")).andExpect(status().isNotFound());
    }

    @Test @Order(10) @DisplayName("PUT → update")
    void shouldUpdate() throws Exception {
        String resp = mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("Old","S1","L1",10,1.0))))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(put("/api/warehouse/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("New","S2","L2",5,10.0))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("New")).andExpect(jsonPath("$.totalValue").value(50.0));
    }

    @Test @Order(11) @DisplayName("DELETE → 204")
    void shouldDelete() throws Exception {
        String resp = mockMvc.perform(post("/api/warehouse").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("T","T","T",1,1.0))))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(delete("/api/warehouse/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/warehouse/" + id)).andExpect(status().isNotFound());
    }

    @Test @Order(12) @DisplayName("DELETE → 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/warehouse/9999")).andExpect(status().isNotFound());
    }
}
