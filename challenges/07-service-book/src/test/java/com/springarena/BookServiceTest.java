package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.Book;
import com.springarena.repository.BookRepository;
import com.springarena.service.BookService;
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
class BookServiceTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private BookRepository bookRepository;
    @Autowired private BookService bookService;

    @BeforeEach
    void setUp() { bookRepository.deleteAll(); }

    @Test @Order(1)
    @DisplayName("BookService bean should exist")
    void serviceShouldExist() {
        assertThat(bookService).isNotNull();
    }

    @Test @Order(2)
    @DisplayName("POST /api/books → should create book and return 201")
    void shouldCreate() throws Exception {
        Book book = new Book(); book.setTitle("Clean Code"); book.setAuthor("Robert Martin"); book.setGenre("Programming"); book.setPrice(39.99); book.setInStock(true);

        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.inStock").value(true));
    }

    @Test @Order(3)
    @DisplayName("GET /api/books → should return all books")
    void shouldGetAll() throws Exception {
        Book b1 = new Book(); b1.setTitle("Book A"); b1.setAuthor("Author A"); b1.setGenre("Fiction"); b1.setPrice(15.0); b1.setInStock(true);
        Book b2 = new Book(); b2.setTitle("Book B"); b2.setAuthor("Author B"); b2.setGenre("Science"); b2.setPrice(25.0); b2.setInStock(false);
        bookRepository.save(b1); bookRepository.save(b2);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4)
    @DisplayName("GET /api/books/{id} → should return book by ID")
    void shouldGetById() throws Exception {
        Book b = new Book(); b.setTitle("Effective Java"); b.setAuthor("Joshua Bloch"); b.setGenre("Programming"); b.setPrice(45.0); b.setInStock(true);
        Book saved = bookRepository.save(b);

        mockMvc.perform(get("/api/books/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }

    @Test @Order(5)
    @DisplayName("GET /api/books/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/books/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(6)
    @DisplayName("PUT /api/books/{id} → should update book")
    void shouldUpdate() throws Exception {
        Book b = new Book(); b.setTitle("Old Title"); b.setAuthor("Author"); b.setGenre("Fiction"); b.setPrice(10.0); b.setInStock(true);
        Book saved = bookRepository.save(b);

        Book updated = new Book(); updated.setTitle("New Title"); updated.setAuthor("Author"); updated.setGenre("Non-Fiction"); updated.setPrice(20.0); updated.setInStock(false);

        mockMvc.perform(put("/api/books/" + saved.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.genre").value("Non-Fiction"));
    }

    @Test @Order(7)
    @DisplayName("DELETE /api/books/{id} → should delete and return 204")
    void shouldDelete() throws Exception {
        Book b = new Book(); b.setTitle("Temp"); b.setAuthor("Temp"); b.setGenre("Temp"); b.setPrice(1.0); b.setInStock(false);
        Book saved = bookRepository.save(b);

        mockMvc.perform(delete("/api/books/" + saved.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/books/" + saved.getId())).andExpect(status().isNotFound());
    }

    @Test @Order(8)
    @DisplayName("DELETE /api/books/{id} → should return 404 for non-existent")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/books/9999")).andExpect(status().isNotFound());
    }
}
