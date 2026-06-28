package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.StudentRequestDTO;
import com.springarena.repository.StudentRepository;
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
import com.springarena.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentDtoTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private StudentRepository studentRepository;
    @Autowired private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    @Order(0)
    @DisplayName("Service should exist")
    void serviceShouldExist() {
        assertThat(studentService).isNotNull();
    }

    private StudentRequestDTO createRequest(String first, String last, String email, String grade, Double gpa) {
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setFirstName(first);
        dto.setLastName(last);
        dto.setEmail(email);
        dto.setGrade(grade);
        dto.setGpa(gpa);
        return dto;
    }

    @Test @Order(1)
    @DisplayName("POST /api/students → should create and return 201 with ResponseDTO")
    void shouldCreateStudent() throws Exception {
        StudentRequestDTO req = createRequest("John", "Doe", "john@school.com", "10th", 3.8);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.displayName").value("10th - John Doe"))
                .andExpect(jsonPath("$.email").value("john@school.com"))
                .andExpect(jsonPath("$.gpa").value(3.8));
    }

    @Test @Order(2)
    @DisplayName("POST /api/students → response should NOT expose firstName, lastName, grade separately")
    void shouldNotExposeEntityFields() throws Exception {
        StudentRequestDTO req = createRequest("Jane", "Smith", "jane@school.com", "12th", 3.9);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.displayName").value("12th - Jane Smith"))
                .andExpect(jsonPath("$.firstName").doesNotExist())
                .andExpect(jsonPath("$.lastName").doesNotExist())
                .andExpect(jsonPath("$.grade").doesNotExist());
    }

    @Test @Order(3)
    @DisplayName("GET /api/students → should return list of ResponseDTOs")
    void shouldGetAll() throws Exception {
        StudentRequestDTO r1 = createRequest("Alice", "A", "alice@school.com", "9th", 3.5);
        StudentRequestDTO r2 = createRequest("Bob", "B", "bob@school.com", "11th", 3.2);
        mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r1)));
        mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r2)));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].displayName").exists());
    }

    @Test @Order(4)
    @DisplayName("GET /api/students/{id} → should return ResponseDTO")
    void shouldGetById() throws Exception {
        StudentRequestDTO req = createRequest("Charlie", "Brown", "charlie@school.com", "8th", 3.0);
        String resp = mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(get("/api/students/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("8th - Charlie Brown"));
    }

    @Test @Order(5)
    @DisplayName("GET /api/students/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/students/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6)
    @DisplayName("PUT /api/students/{id} → should update and return ResponseDTO")
    void shouldUpdate() throws Exception {
        StudentRequestDTO req = createRequest("Dave", "Old", "dave@school.com", "7th", 2.5);
        String resp = mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        StudentRequestDTO update = createRequest("Dave", "New", "dave.new@school.com", "8th", 3.5);
        mockMvc.perform(put("/api/students/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("8th - Dave New"))
                .andExpect(jsonPath("$.gpa").value(3.5));
    }

    @Test @Order(7)
    @DisplayName("PUT /api/students/{id} → should return 404 for non-existent")
    void shouldReturn404OnUpdate() throws Exception {
        StudentRequestDTO req = createRequest("Ghost", "User", "ghost@school.com", "N/A", 0.0);
        mockMvc.perform(put("/api/students/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test @Order(8)
    @DisplayName("DELETE /api/students/{id} → should delete and return 204")
    void shouldDelete() throws Exception {
        StudentRequestDTO req = createRequest("Temp", "User", "temp@school.com", "1st", 1.0);
        String resp = mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(delete("/api/students/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/students/" + id)).andExpect(status().isNotFound());
    }

    @Test @Order(9)
    @DisplayName("DELETE /api/students/{id} → should return 404 for non-existent")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/students/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(10)
    @DisplayName("displayName should update when student is updated")
    void displayNameShouldReflectUpdate() throws Exception {
        StudentRequestDTO req = createRequest("Eve", "First", "eve@school.com", "9th", 3.7);
        String resp = mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        StudentRequestDTO update = createRequest("Eve", "Second", "eve@school.com", "10th", 3.9);
        mockMvc.perform(put("/api/students/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)))
                .andExpect(jsonPath("$.displayName").value("10th - Eve Second"));
    }
}
