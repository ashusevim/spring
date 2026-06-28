package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.Contact;
import com.springarena.repository.ContactRepository;
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
import com.springarena.service.ContactService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactCrudTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ContactRepository contactRepository;
    @Autowired private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
    }

    @Test
    @Order(0)
    @DisplayName("Service should exist")
    void serviceShouldExist() {
        assertThat(contactService).isNotNull();
    }

    @Test
    @Order(1)
    @DisplayName("POST /api/contacts → should create contact and return 201")
    void shouldCreateContact() throws Exception {
        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setEmail("john@example.com");
        contact.setPhone("555-1234");

        mockMvc.perform(post("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contact)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/contacts → should return all contacts")
    void shouldGetAllContacts() throws Exception {
        Contact c1 = new Contact(); c1.setFirstName("Alice"); c1.setLastName("Smith"); c1.setEmail("alice@test.com"); c1.setPhone("555-0001");
        Contact c2 = new Contact(); c2.setFirstName("Bob"); c2.setLastName("Jones"); c2.setEmail("bob@test.com"); c2.setPhone("555-0002");
        contactRepository.save(c1);
        contactRepository.save(c2);

        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").exists())
                .andExpect(jsonPath("$[1].firstName").exists());
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/contacts/{id} → should return contact by ID")
    void shouldGetContactById() throws Exception {
        Contact c = new Contact(); c.setFirstName("Charlie"); c.setLastName("Brown"); c.setEmail("charlie@test.com"); c.setPhone("555-0003");
        Contact saved = contactRepository.save(c);

        mockMvc.perform(get("/api/contacts/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Charlie"))
                .andExpect(jsonPath("$.phone").value("555-0003"));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/contacts/{id} → should return 404 for non-existent contact")
    void shouldReturn404WhenContactNotFound() throws Exception {
        mockMvc.perform(get("/api/contacts/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    @DisplayName("PUT /api/contacts/{id} → should update existing contact")
    void shouldUpdateContact() throws Exception {
        Contact c = new Contact(); c.setFirstName("Dave"); c.setLastName("Wilson"); c.setEmail("dave@test.com"); c.setPhone("555-0004");
        Contact saved = contactRepository.save(c);

        Contact updated = new Contact();
        updated.setFirstName("David");
        updated.setLastName("Wilson Jr.");
        updated.setEmail("david@test.com");
        updated.setPhone("555-9999");

        mockMvc.perform(put("/api/contacts/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("David"))
                .andExpect(jsonPath("$.phone").value("555-9999"));
    }

    @Test
    @Order(6)
    @DisplayName("PUT /api/contacts/{id} → should return 404 for non-existent contact")
    void shouldReturn404WhenUpdatingNonExistent() throws Exception {
        Contact c = new Contact(); c.setFirstName("Ghost"); c.setLastName("N/A"); c.setEmail("ghost@test.com"); c.setPhone("000");

        mockMvc.perform(put("/api/contacts/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    @DisplayName("DELETE /api/contacts/{id} → should delete contact and return 204")
    void shouldDeleteContact() throws Exception {
        Contact c = new Contact(); c.setFirstName("Temp"); c.setLastName("User"); c.setEmail("temp@test.com"); c.setPhone("000");
        Contact saved = contactRepository.save(c);

        mockMvc.perform(delete("/api/contacts/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/contacts/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    @DisplayName("DELETE /api/contacts/{id} → should return 404 for non-existent contact")
    void shouldReturn404WhenDeletingNonExistent() throws Exception {
        mockMvc.perform(delete("/api/contacts/9999"))
                .andExpect(status().isNotFound());
    }
}
