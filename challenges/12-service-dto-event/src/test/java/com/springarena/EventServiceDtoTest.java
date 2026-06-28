package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.EventRequestDTO;
import com.springarena.repository.EventRepository;
import com.springarena.service.EventService;
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
class EventServiceDtoTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private EventRepository eventRepository;
    @Autowired private EventService eventService;

    @BeforeEach
    void setUp() { eventRepository.deleteAll(); }

    private EventRequestDTO createReq(String name, String loc, String start, String end, String org) {
        EventRequestDTO dto = new EventRequestDTO();
        dto.setName(name); dto.setLocation(loc); dto.setStartTime(start); dto.setEndTime(end); dto.setOrganizer(org);
        return dto;
    }

    @Test @Order(1)
    @DisplayName("EventService bean should exist")
    void serviceShouldExist() { assertThat(eventService).isNotNull(); }

    @Test @Order(2)
    @DisplayName("POST /api/events → should create and return 201 with schedule")
    void shouldCreate() throws Exception {
        EventRequestDTO req = createReq("Tech Conference", "Hall A", "10:00 AM", "4:00 PM", "TechCorp");
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Tech Conference"))
                .andExpect(jsonPath("$.schedule").value("10:00 AM to 4:00 PM"));
    }

    @Test @Order(3)
    @DisplayName("Response should NOT expose startTime/endTime separately")
    void shouldNotExposeStartEnd() throws Exception {
        EventRequestDTO req = createReq("Workshop", "Room B", "9:00 AM", "12:00 PM", "DevTeam");
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(jsonPath("$.schedule").value("9:00 AM to 12:00 PM"))
                .andExpect(jsonPath("$.startTime").doesNotExist())
                .andExpect(jsonPath("$.endTime").doesNotExist());
    }

    @Test @Order(4)
    @DisplayName("GET /api/events → should return all")
    void shouldGetAll() throws Exception {
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("E1","L1","1PM","2PM","O1"))));
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("E2","L2","3PM","5PM","O2"))));
        mockMvc.perform(get("/api/events")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(5)
    @DisplayName("GET /api/events/{id} → should return by ID")
    void shouldGetById() throws Exception {
        EventRequestDTO req = createReq("Meetup", "Park", "5:00 PM", "8:00 PM", "Community");
        String resp = mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(get("/api/events/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.schedule").value("5:00 PM to 8:00 PM"));
    }

    @Test @Order(6)
    @DisplayName("GET /api/events/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/events/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(7)
    @DisplayName("PUT /api/events/{id} → should update and recompute schedule")
    void shouldUpdate() throws Exception {
        EventRequestDTO req = createReq("Old Event", "Old Loc", "1PM", "3PM", "Old Org");
        String resp = mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();

        EventRequestDTO update = createReq("New Event", "New Loc", "6:00 PM", "10:00 PM", "New Org");
        mockMvc.perform(put("/api/events/" + id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Event"))
                .andExpect(jsonPath("$.schedule").value("6:00 PM to 10:00 PM"));
    }

    @Test @Order(8)
    @DisplayName("PUT /api/events/{id} → should return 404")
    void shouldReturn404OnUpdate() throws Exception {
        mockMvc.perform(put("/api/events/9999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createReq("G","G","G","G","G"))))
                .andExpect(status().isNotFound());
    }

    @Test @Order(9)
    @DisplayName("DELETE /api/events/{id} → should return 204")
    void shouldDelete() throws Exception {
        EventRequestDTO req = createReq("Temp", "T", "1", "2", "T");
        String resp = mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(delete("/api/events/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/events/" + id)).andExpect(status().isNotFound());
    }

    @Test @Order(10)
    @DisplayName("DELETE /api/events/{id} → should return 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/events/9999")).andExpect(status().isNotFound());
    }
}
