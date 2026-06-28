package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.Note;
import com.springarena.repository.NoteRepository;
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
import com.springarena.service.NoteService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NoteCrudTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private NoteRepository noteRepository;
    @Autowired private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
    }

    @Test
    @Order(0)
    @DisplayName("Service should exist")
    void serviceShouldExist() {
        assertThat(noteService).isNotNull();
    }

    @Test
    @Order(1)
    @DisplayName("POST /api/notes → should create note and return 201")
    void shouldCreateNote() throws Exception {
        Note note = new Note();
        note.setTitle("Meeting Notes");
        note.setContent("Discuss project timeline");
        note.setAuthor("Alice");
        note.setPinned(true);

        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Meeting Notes"))
                .andExpect(jsonPath("$.pinned").value(true));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/notes → should return all notes")
    void shouldGetAllNotes() throws Exception {
        Note n1 = new Note(); n1.setTitle("Note 1"); n1.setContent("Content 1"); n1.setAuthor("Alice"); n1.setPinned(false);
        Note n2 = new Note(); n2.setTitle("Note 2"); n2.setContent("Content 2"); n2.setAuthor("Bob"); n2.setPinned(true);
        noteRepository.save(n1);
        noteRepository.save(n2);

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/notes/{id} → should return note by ID")
    void shouldGetNoteById() throws Exception {
        Note n = new Note(); n.setTitle("Important"); n.setContent("Very important note"); n.setAuthor("Charlie"); n.setPinned(true);
        Note saved = noteRepository.save(n);

        mockMvc.perform(get("/api/notes/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Important"))
                .andExpect(jsonPath("$.author").value("Charlie"));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/notes/{id} → should return 404 for non-existent note")
    void shouldReturn404WhenNoteNotFound() throws Exception {
        mockMvc.perform(get("/api/notes/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    @DisplayName("PUT /api/notes/{id} → should update existing note")
    void shouldUpdateNote() throws Exception {
        Note n = new Note(); n.setTitle("Draft"); n.setContent("WIP"); n.setAuthor("Dave"); n.setPinned(false);
        Note saved = noteRepository.save(n);

        Note updated = new Note();
        updated.setTitle("Final Version");
        updated.setContent("Complete content");
        updated.setAuthor("Dave");
        updated.setPinned(true);

        mockMvc.perform(put("/api/notes/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Final Version"))
                .andExpect(jsonPath("$.pinned").value(true));
    }

    @Test
    @Order(6)
    @DisplayName("PUT /api/notes/{id} → should return 404 for non-existent note")
    void shouldReturn404WhenUpdatingNonExistent() throws Exception {
        Note n = new Note(); n.setTitle("Ghost"); n.setContent("N/A"); n.setAuthor("N/A"); n.setPinned(false);

        mockMvc.perform(put("/api/notes/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(n)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    @DisplayName("DELETE /api/notes/{id} → should delete note and return 204")
    void shouldDeleteNote() throws Exception {
        Note n = new Note(); n.setTitle("Temp"); n.setContent("Delete me"); n.setAuthor("Test"); n.setPinned(false);
        Note saved = noteRepository.save(n);

        mockMvc.perform(delete("/api/notes/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/notes/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    @DisplayName("DELETE /api/notes/{id} → should return 404 for non-existent note")
    void shouldReturn404WhenDeletingNonExistent() throws Exception {
        mockMvc.perform(delete("/api/notes/9999"))
                .andExpect(status().isNotFound());
    }
}
