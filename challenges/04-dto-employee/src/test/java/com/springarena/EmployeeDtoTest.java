package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.EmployeeRequestDTO;
import com.springarena.repository.EmployeeRepository;
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
import com.springarena.service.EmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeDtoTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    @Order(0)
    @DisplayName("Service should exist")
    void serviceShouldExist() {
        assertThat(employeeService).isNotNull();
    }

    private EmployeeRequestDTO createRequest(String first, String last, String email, String dept, Double salary) {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setFirstName(first);
        dto.setLastName(last);
        dto.setEmail(email);
        dto.setDepartment(dept);
        dto.setSalary(salary);
        return dto;
    }

    @Test
    @Order(1)
    @DisplayName("POST /api/employees → should create employee and return 201 with ResponseDTO")
    void shouldCreateEmployee() throws Exception {
        EmployeeRequestDTO req = createRequest("John", "Doe", "john@company.com", "Engineering", 75000.0);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@company.com"))
                .andExpect(jsonPath("$.department").value("Engineering"))
                .andExpect(jsonPath("$.salary").value(75000.0));
    }

    @Test
    @Order(2)
    @DisplayName("POST /api/employees → response should NOT contain firstName or lastName separately")
    void responseShouldNotExposeEntityFields() throws Exception {
        EmployeeRequestDTO req = createRequest("Jane", "Smith", "jane@company.com", "Marketing", 65000.0);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Jane Smith"))
                .andExpect(jsonPath("$.firstName").doesNotExist())
                .andExpect(jsonPath("$.lastName").doesNotExist());
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/employees → should return list of ResponseDTOs")
    void shouldGetAllEmployees() throws Exception {
        EmployeeRequestDTO req1 = createRequest("Alice", "Wonder", "alice@company.com", "HR", 60000.0);
        EmployeeRequestDTO req2 = createRequest("Bob", "Builder", "bob@company.com", "Engineering", 80000.0);

        mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req1)));
        mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req2)));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fullName").exists())
                .andExpect(jsonPath("$[1].fullName").exists());
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/employees/{id} → should return ResponseDTO by ID")
    void shouldGetEmployeeById() throws Exception {
        EmployeeRequestDTO req = createRequest("Charlie", "Chaplin", "charlie@company.com", "Entertainment", 90000.0);

        String response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Charlie Chaplin"))
                .andExpect(jsonPath("$.salary").value(90000.0));
    }

    @Test
    @Order(5)
    @DisplayName("GET /api/employees/{id} → should return 404 for non-existent")
    void shouldReturn404WhenNotFound() throws Exception {
        mockMvc.perform(get("/api/employees/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    @DisplayName("PUT /api/employees/{id} → should update and return updated ResponseDTO")
    void shouldUpdateEmployee() throws Exception {
        EmployeeRequestDTO req = createRequest("Dave", "Original", "dave@company.com", "Sales", 50000.0);

        String response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        EmployeeRequestDTO update = createRequest("Dave", "Updated", "dave.updated@company.com", "Management", 95000.0);

        mockMvc.perform(put("/api/employees/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Dave Updated"))
                .andExpect(jsonPath("$.email").value("dave.updated@company.com"))
                .andExpect(jsonPath("$.salary").value(95000.0));
    }

    @Test
    @Order(7)
    @DisplayName("PUT /api/employees/{id} → should return 404 for non-existent")
    void shouldReturn404WhenUpdatingNonExistent() throws Exception {
        EmployeeRequestDTO req = createRequest("Ghost", "User", "ghost@company.com", "N/A", 0.0);

        mockMvc.perform(put("/api/employees/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    @DisplayName("DELETE /api/employees/{id} → should delete and return 204")
    void shouldDeleteEmployee() throws Exception {
        EmployeeRequestDTO req = createRequest("Temp", "User", "temp@company.com", "Temp", 10000.0);

        String response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/employees/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/employees/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    @DisplayName("DELETE /api/employees/{id} → should return 404 for non-existent")
    void shouldReturn404WhenDeletingNonExistent() throws Exception {
        mockMvc.perform(delete("/api/employees/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(10)
    @DisplayName("POST /api/employees → fullName should be properly computed from firstName + lastName")
    void fullNameShouldBeComputed() throws Exception {
        EmployeeRequestDTO req = createRequest("Mary", "Jane", "mary@company.com", "Design", 70000.0);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Mary Jane"));
    }
}
