package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.PatientRequestDTO;
import com.springarena.repository.PatientRepository;
import com.springarena.service.PatientService;
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
class ClinicMockTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PatientRepository patientRepository;
    @Autowired private PatientService patientService;

    @BeforeEach
    void setUp() { patientRepository.deleteAll(); }

    private PatientRequestDTO createReq(String first, String last, Integer age, String condition, String doctor) {
        PatientRequestDTO dto = new PatientRequestDTO();
        dto.setFirstName(first); dto.setLastName(last); dto.setAge(age); dto.setCondition(condition); dto.setDoctor(doctor);
        return dto;
    }

    @Test @Order(1) @DisplayName("Service should exist")
    void serviceShouldExist() { assertThat(patientService).isNotNull(); }

    @Test @Order(2) @DisplayName("POST → 201 with fullName")
    void shouldCreate() throws Exception {
        PatientRequestDTO req = createReq("John", "Doe", 35, "Flu", "Dr. Smith");
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.condition").value("Flu"));
    }

    @Test @Order(3) @DisplayName("Response should NOT expose firstName/lastName")
    void shouldNotExposeNames() throws Exception {
        PatientRequestDTO req = createReq("Jane", "Smith", 28, "Cold", "Dr. Johnson");
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(jsonPath("$.fullName").value("Jane Smith"))
                .andExpect(jsonPath("$.firstName").doesNotExist())
                .andExpect(jsonPath("$.lastName").doesNotExist());
    }

    @Test @Order(4) @DisplayName("GET → all")
    void shouldGetAll() throws Exception {
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","A",20,"X","Dr.A"))));
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","B",30,"Y","Dr.B"))));
        mockMvc.perform(get("/api/patients")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(5) @DisplayName("GET /{id}")
    void shouldGetById() throws Exception {
        PatientRequestDTO req = createReq("Charlie", "Brown", 45, "Headache", "Dr. Patel");
        String resp = mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(get("/api/patients/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.fullName").value("Charlie Brown"));
    }

    @Test @Order(6) @DisplayName("GET /{id} → 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/patients/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(7) @DisplayName("GET /doctor/{doctor}")
    void shouldFilterByDoctor() throws Exception {
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","A",20,"X","Dr.Smith"))));
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","B",30,"Y","Dr.Smith"))));
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C","C",40,"Z","Dr.Jones"))));
        mockMvc.perform(get("/api/patients/doctor/Dr.Smith")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(8) @DisplayName("GET /condition/{condition}")
    void shouldFilterByCondition() throws Exception {
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("A","A",20,"Flu","Dr.A"))));
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("B","B",30,"Flu","Dr.B"))));
        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C","C",40,"Cold","Dr.A"))));
        mockMvc.perform(get("/api/patients/condition/Flu")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(9) @DisplayName("PUT → update")
    void shouldUpdate() throws Exception {
        String resp = mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("Old","Name",25,"Cold","Dr.A"))))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(put("/api/patients/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("New","Name",26,"Recovered","Dr.B"))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.fullName").value("New Name")).andExpect(jsonPath("$.condition").value("Recovered"));
    }

    @Test @Order(10) @DisplayName("PUT → 404")
    void shouldReturn404OnUpdate() throws Exception {
        mockMvc.perform(put("/api/patients/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("G","G",0,"G","G"))))
                .andExpect(status().isNotFound());
    }

    @Test @Order(11) @DisplayName("DELETE → 204")
    void shouldDelete() throws Exception {
        String resp = mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("T","T",1,"T","T"))))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(delete("/api/patients/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/patients/" + id)).andExpect(status().isNotFound());
    }

    @Test @Order(12) @DisplayName("DELETE → 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/patients/9999")).andExpect(status().isNotFound());
    }
}
