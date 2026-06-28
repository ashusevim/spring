package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.TaskRequestDTO;
import com.springarena.repository.TaskRepository;
import com.springarena.service.TaskService;
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
class TaskServiceDtoTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private TaskRepository taskRepository;
    @Autowired private TaskService taskService;

    @BeforeEach
    void setUp() { taskRepository.deleteAll(); }

    private TaskRequestDTO createReq(String title, String desc, String status, String priority, String dueDate) {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTitle(title); dto.setDescription(desc); dto.setStatus(status); dto.setPriority(priority); dto.setDueDate(dueDate);
        return dto;
    }

    @Test @Order(1)
    @DisplayName("TaskService bean should exist")
    void serviceShouldExist() { assertThat(taskService).isNotNull(); }

    @Test @Order(2)
    @DisplayName("POST /api/tasks → should create and return 201")
    void shouldCreate() throws Exception {
        TaskRequestDTO req = createReq("Fix bug", "Fix login bug", "TODO", "HIGH", "2024-12-31");
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Fix bug"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test @Order(3)
    @DisplayName("GET /api/tasks → should return all")
    void shouldGetAll() throws Exception {
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("T1","D1","TODO","LOW","2024-01-01"))));
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("T2","D2","DONE","HIGH","2024-06-01"))));
        mockMvc.perform(get("/api/tasks")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4)
    @DisplayName("GET /api/tasks/{id} → should return by ID")
    void shouldGetById() throws Exception {
        TaskRequestDTO req = createReq("Deploy app", "Deploy to prod", "IN_PROGRESS", "CRITICAL", "2024-03-15");
        String resp = mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(get("/api/tasks/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Deploy app"));
    }

    @Test @Order(5)
    @DisplayName("GET /api/tasks/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/tasks/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6)
    @DisplayName("PUT /api/tasks/{id} → should update")
    void shouldUpdate() throws Exception {
        TaskRequestDTO req = createReq("Old task", "Old", "TODO", "LOW", "2024-01-01");
        String resp = mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        TaskRequestDTO update = createReq("Updated task", "New desc", "DONE", "HIGH", "2024-12-31");
        mockMvc.perform(put("/api/tasks/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated task"))
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test @Order(7)
    @DisplayName("PUT /api/tasks/{id} → should return 404")
    void shouldReturn404OnUpdate() throws Exception {
        mockMvc.perform(put("/api/tasks/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("G","G","G","G","G"))))
                .andExpect(status().isNotFound());
    }

    @Test @Order(8)
    @DisplayName("DELETE /api/tasks/{id} → should return 204")
    void shouldDelete() throws Exception {
        TaskRequestDTO req = createReq("Temp", "Temp", "TODO", "LOW", "2024-01-01");
        String resp = mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(delete("/api/tasks/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/tasks/" + id)).andExpect(status().isNotFound());
    }

    @Test @Order(9)
    @DisplayName("DELETE /api/tasks/{id} → should return 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/tasks/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(10)
    @DisplayName("Priority and status should persist correctly")
    void shouldPersistStatusAndPriority() throws Exception {
        TaskRequestDTO req = createReq("Review PR", "Review pull request", "IN_PROGRESS", "MEDIUM", "2024-07-01");
        String resp = mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        mockMvc.perform(get("/api/tasks/" + id))
                .andExpect(jsonPath("$.priority").value("MEDIUM"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }
}
