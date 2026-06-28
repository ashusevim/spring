package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.BlogPost;
import com.springarena.repository.BlogPostRepository;
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
import com.springarena.service.BlogPostService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BlogPostQueryTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private BlogPostRepository blogPostRepository;
    @Autowired private BlogPostService blogPostService;

    @BeforeEach
    void setUp() { blogPostRepository.deleteAll(); }

    @Test
    @Order(0)
    @DisplayName("Service should exist")
    void serviceShouldExist() {
        assertThat(blogPostService).isNotNull();
    }

    private BlogPost createPost(String title, String content, String author, String category, Boolean published) {
        BlogPost p = new BlogPost();
        p.setTitle(title); p.setContent(content); p.setAuthor(author); p.setCategory(category); p.setPublished(published);
        return p;
    }

    @Test @Order(1)
    @DisplayName("POST /api/posts → should create and return 201")
    void shouldCreate() throws Exception {
        BlogPost p = createPost("Hello World", "My first post", "Alice", "Tech", true);
        mockMvc.perform(post("/api/posts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("Hello World"));
    }

    @Test @Order(2)
    @DisplayName("GET /api/posts → should return all")
    void shouldGetAll() throws Exception {
        blogPostRepository.save(createPost("P1", "C1", "Alice", "Tech", true));
        blogPostRepository.save(createPost("P2", "C2", "Bob", "Life", false));
        mockMvc.perform(get("/api/posts")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(3)
    @DisplayName("GET /api/posts/author/{author} → should filter by author")
    void shouldFilterByAuthor() throws Exception {
        blogPostRepository.save(createPost("P1", "C1", "Alice", "Tech", true));
        blogPostRepository.save(createPost("P2", "C2", "Alice", "Life", true));
        blogPostRepository.save(createPost("P3", "C3", "Bob", "Tech", true));

        mockMvc.perform(get("/api/posts/author/Alice")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4)
    @DisplayName("GET /api/posts/category/{category} → should filter by category")
    void shouldFilterByCategory() throws Exception {
        blogPostRepository.save(createPost("P1", "C1", "Alice", "Tech", true));
        blogPostRepository.save(createPost("P2", "C2", "Bob", "Tech", false));
        blogPostRepository.save(createPost("P3", "C3", "Charlie", "Life", true));

        mockMvc.perform(get("/api/posts/category/Tech")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(5)
    @DisplayName("GET /api/posts/published → should return only published posts")
    void shouldFilterPublished() throws Exception {
        blogPostRepository.save(createPost("P1", "C1", "Alice", "Tech", true));
        blogPostRepository.save(createPost("P2", "C2", "Bob", "Life", false));
        blogPostRepository.save(createPost("P3", "C3", "Charlie", "Tech", true));

        mockMvc.perform(get("/api/posts/published")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(6)
    @DisplayName("GET /api/posts/{id} → should return 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/posts/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(7)
    @DisplayName("PUT /api/posts/{id} → should update")
    void shouldUpdate() throws Exception {
        BlogPost saved = blogPostRepository.save(createPost("Old", "Old", "Auth", "Cat", false));
        BlogPost upd = createPost("New Title", "New Content", "Auth", "NewCat", true);
        mockMvc.perform(put("/api/posts/" + saved.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("New Title"));
    }

    @Test @Order(8)
    @DisplayName("DELETE /api/posts/{id} → should return 204")
    void shouldDelete() throws Exception {
        BlogPost saved = blogPostRepository.save(createPost("Temp", "T", "T", "T", false));
        mockMvc.perform(delete("/api/posts/" + saved.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/posts/" + saved.getId())).andExpect(status().isNotFound());
    }
}
