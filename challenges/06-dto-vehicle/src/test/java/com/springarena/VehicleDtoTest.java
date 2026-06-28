package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.VehicleRequestDTO;
import com.springarena.repository.VehicleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import com.springarena.service.VehicleService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleDtoTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private VehicleService vehicleService;

    @BeforeEach
    void setUp() { vehicleRepository.deleteAll(); }

    @Test @Order(0)
    @DisplayName("Service should exist")
    void serviceShouldExist() {
        assertThat(vehicleService).isNotNull();
    }

    private VehicleRequestDTO createRequest(String make, String model, Integer year, String color, Double price) {
        VehicleRequestDTO dto = new VehicleRequestDTO();
        dto.setMake(make); dto.setModel(model); dto.setYear(year); dto.setColor(color); dto.setPrice(price);
        return dto;
    }

    @Test @Order(1)
    @DisplayName("POST /api/vehicles → should create and return 201 with ResponseDTO")
    void shouldCreate() throws Exception {
        VehicleRequestDTO req = createRequest("Toyota", "Camry", 2024, "Silver", 28000.0);
        mockMvc.perform(post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.label").value("2024 Toyota Camry"))
                .andExpect(jsonPath("$.color").value("Silver"))
                .andExpect(jsonPath("$.price").value(28000.0));
    }

    @Test @Order(2)
    @DisplayName("POST → response should NOT expose make, model, year separately")
    void shouldNotExposeEntityFields() throws Exception {
        VehicleRequestDTO req = createRequest("Honda", "Civic", 2023, "Blue", 25000.0);
        mockMvc.perform(post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.label").value("2023 Honda Civic"))
                .andExpect(jsonPath("$.make").doesNotExist())
                .andExpect(jsonPath("$.model").doesNotExist())
                .andExpect(jsonPath("$.year").doesNotExist());
    }

    @Test @Order(3)
    @DisplayName("GET /api/vehicles → should return list of ResponseDTOs")
    void shouldGetAll() throws Exception {
        VehicleRequestDTO r1 = createRequest("Ford", "Mustang", 2024, "Red", 45000.0);
        VehicleRequestDTO r2 = createRequest("BMW", "M3", 2023, "Black", 72000.0);
        mockMvc.perform(post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r1)));
        mockMvc.perform(post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r2)));

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].label").exists());
    }

    @Test @Order(4)
    @DisplayName("GET /api/vehicles/{id} → should return ResponseDTO")
    void shouldGetById() throws Exception {
        VehicleRequestDTO req = createRequest("Tesla", "Model 3", 2024, "White", 42000.0);
        String resp = mockMvc.perform(post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(get("/api/vehicles/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("2024 Tesla Model 3"));
    }

    @Test @Order(5)
    @DisplayName("GET /api/vehicles/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/vehicles/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6)
    @DisplayName("PUT /api/vehicles/{id} → should update and return ResponseDTO")
    void shouldUpdate() throws Exception {
        VehicleRequestDTO req = createRequest("Audi", "A4", 2022, "Gray", 38000.0);
        String resp = mockMvc.perform(post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        VehicleRequestDTO update = createRequest("Audi", "A6", 2024, "Black", 55000.0);
        mockMvc.perform(put("/api/vehicles/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("2024 Audi A6"))
                .andExpect(jsonPath("$.price").value(55000.0));
    }

    @Test @Order(7)
    @DisplayName("PUT /api/vehicles/{id} → should return 404 for non-existent")
    void shouldReturn404OnUpdate() throws Exception {
        VehicleRequestDTO req = createRequest("Ghost", "Car", 2000, "N/A", 0.0);
        mockMvc.perform(put("/api/vehicles/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test @Order(8)
    @DisplayName("DELETE /api/vehicles/{id} → should delete and return 204")
    void shouldDelete() throws Exception {
        VehicleRequestDTO req = createRequest("Temp", "Car", 2020, "N/A", 1000.0);
        String resp = mockMvc.perform(post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(delete("/api/vehicles/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/vehicles/" + id)).andExpect(status().isNotFound());
    }

    @Test @Order(9)
    @DisplayName("DELETE /api/vehicles/{id} → should return 404 for non-existent")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/vehicles/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(10)
    @DisplayName("label should update when vehicle is updated")
    void labelShouldReflectUpdate() throws Exception {
        VehicleRequestDTO req = createRequest("Kia", "Seltos", 2023, "White", 22000.0);
        String resp = mockMvc.perform(post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        VehicleRequestDTO update = createRequest("Kia", "Sportage", 2024, "Red", 30000.0);
        mockMvc.perform(put("/api/vehicles/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)))
                .andExpect(jsonPath("$.label").value("2024 Kia Sportage"));
    }
}
