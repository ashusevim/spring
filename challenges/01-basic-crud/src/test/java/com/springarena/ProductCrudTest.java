package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.Product;
import com.springarena.repository.ProductRepository;
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
import com.springarena.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCrudTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ProductRepository productRepository;
    @Autowired private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @Order(0)
    @DisplayName("Service should exist")
    void serviceShouldExist() {
        assertThat(productService).isNotNull();
    }

    @Test
    @Order(1)
    @DisplayName("POST /api/products → should create product and return 201")
    void shouldCreateProduct() throws Exception {
        Product product = new Product();
        product.setName("MacBook Pro");
        product.setDescription("Apple laptop");
        product.setPrice(1999.99);
        product.setQuantity(10);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("MacBook Pro"))
                .andExpect(jsonPath("$.price").value(1999.99));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/products → should return all products")
    void shouldGetAllProducts() throws Exception {
        Product p1 = new Product(); p1.setName("Phone"); p1.setDescription("Smartphone"); p1.setPrice(999.0); p1.setQuantity(5);
        Product p2 = new Product(); p2.setName("Tablet"); p2.setDescription("iPad"); p2.setPrice(799.0); p2.setQuantity(3);
        productRepository.save(p1);
        productRepository.save(p2);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists());
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/products/{id} → should return product by ID")
    void shouldGetProductById() throws Exception {
        Product p = new Product(); p.setName("Keyboard"); p.setDescription("Mechanical"); p.setPrice(149.99); p.setQuantity(20);
        Product saved = productRepository.save(p);

        mockMvc.perform(get("/api/products/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Keyboard"))
                .andExpect(jsonPath("$.price").value(149.99));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/products/{id} → should return 404 for non-existent product")
    void shouldReturn404WhenProductNotFound() throws Exception {
        mockMvc.perform(get("/api/products/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    @DisplayName("PUT /api/products/{id} → should update existing product")
    void shouldUpdateProduct() throws Exception {
        Product p = new Product(); p.setName("Mouse"); p.setDescription("Wireless"); p.setPrice(49.99); p.setQuantity(30);
        Product saved = productRepository.save(p);

        Product updated = new Product();
        updated.setName("Mouse Pro");
        updated.setDescription("Wireless Ergonomic");
        updated.setPrice(79.99);
        updated.setQuantity(25);

        mockMvc.perform(put("/api/products/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mouse Pro"))
                .andExpect(jsonPath("$.price").value(79.99))
                .andExpect(jsonPath("$.quantity").value(25));
    }

    @Test
    @Order(6)
    @DisplayName("PUT /api/products/{id} → should return 404 for non-existent product")
    void shouldReturn404WhenUpdatingNonExistentProduct() throws Exception {
        Product p = new Product(); p.setName("Ghost"); p.setDescription("N/A"); p.setPrice(0.0); p.setQuantity(0);

        mockMvc.perform(put("/api/products/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    @DisplayName("DELETE /api/products/{id} → should delete product and return 204")
    void shouldDeleteProduct() throws Exception {
        Product p = new Product(); p.setName("Temp"); p.setDescription("To delete"); p.setPrice(1.0); p.setQuantity(1);
        Product saved = productRepository.save(p);

        mockMvc.perform(delete("/api/products/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/products/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    @DisplayName("DELETE /api/products/{id} → should return 404 for non-existent product")
    void shouldReturn404WhenDeletingNonExistentProduct() throws Exception {
        mockMvc.perform(delete("/api/products/9999"))
                .andExpect(status().isNotFound());
    }
}
