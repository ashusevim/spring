package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.CourseRequestDTO;
import com.springarena.repository.CourseRepository;
import com.springarena.service.CourseService;
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
class CourseFullTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseService courseService;

    @BeforeEach
    void setUp() { courseRepository.deleteAll(); }

    private CourseRequestDTO createReq(String title, String instructor, String category, Integer max, Integer enrolled, Double price) {
        CourseRequestDTO dto = new CourseRequestDTO();
        dto.setTitle(title); dto.setInstructor(instructor); dto.setCategory(category);
        dto.setMaxStudents(max); dto.setEnrolledStudents(enrolled); dto.setPrice(price);
        return dto;
    }

    @Test @Order(1) @DisplayName("Service should exist")
    void serviceShouldExist() { assertThat(courseService).isNotNull(); }

    @Test @Order(2) @DisplayName("POST → 201 with spotsLeft computed")
    void shouldCreate() throws Exception {
        CourseRequestDTO req = createReq("Java 101", "Prof Smith", "Programming", 30, 12, 299.99);
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Java 101"))
                .andExpect(jsonPath("$.spotsLeft").value(18));
    }

    @Test @Order(3) @DisplayName("GET → all courses")
    void shouldGetAll() throws Exception {
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C1","I1","Cat1",20,5,100.0))));
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C2","I2","Cat2",30,10,200.0))));
        mockMvc.perform(get("/api/courses")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4) @DisplayName("GET /{id} → by ID")
    void shouldGetById() throws Exception {
        CourseRequestDTO req = createReq("Spring Boot", "Prof Java", "Backend", 25, 20, 399.99);
        String resp = mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(get("/api/courses/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.spotsLeft").value(5));
    }

    @Test @Order(5) @DisplayName("GET /{id} → 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/courses/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6) @DisplayName("GET /category/{cat}")
    void shouldFilterByCategory() throws Exception {
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C1","I","Programming",20,5,100.0))));
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C2","I","Programming",30,10,200.0))));
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C3","I","Design",15,3,150.0))));
        mockMvc.perform(get("/api/courses/category/Programming")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(7) @DisplayName("GET /instructor/{instructor}")
    void shouldFilterByInstructor() throws Exception {
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C1","DrJava","P",20,5,100.0))));
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C2","DrJava","P",30,10,200.0))));
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("C3","DrPython","P",15,3,150.0))));
        mockMvc.perform(get("/api/courses/instructor/DrJava")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(8) @DisplayName("PUT → update and recompute spotsLeft")
    void shouldUpdate() throws Exception {
        CourseRequestDTO req = createReq("Old", "Old", "Old", 10, 5, 50.0);
        String resp = mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        CourseRequestDTO upd = createReq("Updated", "NewInst", "NewCat", 50, 10, 499.99);
        mockMvc.perform(put("/api/courses/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.spotsLeft").value(40));
    }

    @Test @Order(9) @DisplayName("PUT → 404")
    void shouldReturn404OnUpdate() throws Exception {
        mockMvc.perform(put("/api/courses/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("G","G","G",0,0,0.0))))
                .andExpect(status().isNotFound());
    }

    @Test @Order(10) @DisplayName("DELETE → 204")
    void shouldDelete() throws Exception {
        String resp = mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("T","T","T",1,0,1.0))))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(delete("/api/courses/" + id)).andExpect(status().isNoContent());
    }

    @Test @Order(11) @DisplayName("DELETE → 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/courses/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(12) @DisplayName("spotsLeft should NOT exist as entity field")
    void spotsLeftShouldBeComputed() throws Exception {
        CourseRequestDTO req = createReq("Test", "Test", "Test", 100, 37, 99.99);
        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(jsonPath("$.spotsLeft").value(63));
    }
}
