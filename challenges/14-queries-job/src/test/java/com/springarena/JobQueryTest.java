package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.Job;
import com.springarena.repository.JobRepository;
import com.springarena.service.JobService;
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
class JobQueryTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private JobRepository jobRepository;
    @Autowired private JobService jobService;

    @BeforeEach
    void setUp() { jobRepository.deleteAll(); }

    private Job createJob(String title, String company, String location, Double salary, Boolean remote) {
        Job j = new Job(); j.setTitle(title); j.setCompany(company); j.setLocation(location); j.setSalary(salary); j.setRemote(remote);
        return j;
    }

    @Test @Order(1) @DisplayName("JobService should exist")
    void serviceShouldExist() { assertThat(jobService).isNotNull(); }

    @Test @Order(2) @DisplayName("POST /api/jobs → 201")
    void shouldCreate() throws Exception {
        Job j = createJob("Backend Dev", "Google", "Mountain View", 150000.0, false);
        mockMvc.perform(post("/api/jobs").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(j)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("Backend Dev"));
    }

    @Test @Order(3) @DisplayName("GET /api/jobs/company/{company}")
    void shouldFilterByCompany() throws Exception {
        jobRepository.save(createJob("Dev", "Google", "MV", 150000.0, false));
        jobRepository.save(createJob("PM", "Google", "NYC", 140000.0, true));
        jobRepository.save(createJob("Dev", "Meta", "Menlo", 160000.0, false));
        mockMvc.perform(get("/api/jobs/company/Google")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4) @DisplayName("GET /api/jobs/location/{location}")
    void shouldFilterByLocation() throws Exception {
        jobRepository.save(createJob("Dev", "Co1", "NYC", 100000.0, false));
        jobRepository.save(createJob("PM", "Co2", "NYC", 110000.0, false));
        jobRepository.save(createJob("Dev", "Co3", "LA", 95000.0, true));
        mockMvc.perform(get("/api/jobs/location/NYC")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(5) @DisplayName("GET /api/jobs/remote → only remote")
    void shouldFilterRemote() throws Exception {
        jobRepository.save(createJob("Dev", "Co1", "NYC", 100000.0, true));
        jobRepository.save(createJob("PM", "Co2", "LA", 110000.0, false));
        jobRepository.save(createJob("QA", "Co3", "Remote", 90000.0, true));
        mockMvc.perform(get("/api/jobs/remote")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(6) @DisplayName("GET /api/jobs/salary/{min} → salary >= min")
    void shouldFilterBySalary() throws Exception {
        jobRepository.save(createJob("Jr Dev", "Co1", "NYC", 70000.0, false));
        jobRepository.save(createJob("Sr Dev", "Co2", "LA", 150000.0, true));
        jobRepository.save(createJob("Lead", "Co3", "SF", 200000.0, false));
        mockMvc.perform(get("/api/jobs/salary/100000")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(7) @DisplayName("GET /api/jobs/{id} → 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/jobs/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(8) @DisplayName("PUT /api/jobs/{id} → update")
    void shouldUpdate() throws Exception {
        Job saved = jobRepository.save(createJob("Old", "Old", "Old", 1.0, false));
        Job upd = createJob("New Title", "New Co", "New Loc", 99999.0, true);
        mockMvc.perform(put("/api/jobs/" + saved.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("New Title"));
    }

    @Test @Order(9) @DisplayName("DELETE /api/jobs/{id} → 204")
    void shouldDelete() throws Exception {
        Job saved = jobRepository.save(createJob("Temp", "T", "T", 1.0, false));
        mockMvc.perform(delete("/api/jobs/" + saved.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/jobs/" + saved.getId())).andExpect(status().isNotFound());
    }

    @Test @Order(10) @DisplayName("DELETE /api/jobs/{id} → 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/jobs/9999")).andExpect(status().isNotFound());
    }
}
