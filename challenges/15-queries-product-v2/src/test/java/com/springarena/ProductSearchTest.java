package com.springarena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.model.Product;
import com.springarena.repository.ProductRepository;
import com.springarena.service.ProductService;
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
class ProductSearchTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ProductRepository productRepository;
    @Autowired private ProductService productService;

    @BeforeEach
    void setUp() { productRepository.deleteAll(); }

    private Product createProduct(String name, String category, Double price, Boolean inStock, String brand) {
        Product p = new Product(); p.setName(name); p.setCategory(category); p.setPrice(price); p.setInStock(inStock); p.setBrand(brand);
        return p;
    }

    @Test @Order(1) @DisplayName("ProductService should exist")
    void serviceShouldExist() { assertThat(productService).isNotNull(); }

    @Test @Order(2) @DisplayName("POST /api/products → 201")
    void shouldCreate() throws Exception {
        Product p = createProduct("iPhone 15", "Electronics", 999.0, true, "Apple");
        mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("iPhone 15"));
    }

    @Test @Order(3) @DisplayName("GET /api/products/category/{cat}")
    void shouldFilterByCategory() throws Exception {
        productRepository.save(createProduct("Phone", "Electronics", 999.0, true, "Apple"));
        productRepository.save(createProduct("Laptop", "Electronics", 1999.0, true, "Dell"));
        productRepository.save(createProduct("Shirt", "Clothing", 49.0, true, "Nike"));
        mockMvc.perform(get("/api/products/category/Electronics")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(4) @DisplayName("GET /api/products/brand/{brand}")
    void shouldFilterByBrand() throws Exception {
        productRepository.save(createProduct("iPhone", "Electronics", 999.0, true, "Apple"));
        productRepository.save(createProduct("MacBook", "Electronics", 1999.0, true, "Apple"));
        productRepository.save(createProduct("Galaxy", "Electronics", 899.0, true, "Samsung"));
        mockMvc.perform(get("/api/products/brand/Apple")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(5) @DisplayName("GET /api/products/in-stock → only in-stock")
    void shouldFilterInStock() throws Exception {
        productRepository.save(createProduct("P1", "C1", 10.0, true, "B1"));
        productRepository.save(createProduct("P2", "C2", 20.0, false, "B2"));
        productRepository.save(createProduct("P3", "C3", 30.0, true, "B3"));
        mockMvc.perform(get("/api/products/in-stock")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(6) @DisplayName("GET /api/products/max-price/{max} → price <= max")
    void shouldFilterByMaxPrice() throws Exception {
        productRepository.save(createProduct("Cheap", "C", 10.0, true, "B"));
        productRepository.save(createProduct("Mid", "C", 50.0, true, "B"));
        productRepository.save(createProduct("Expensive", "C", 200.0, true, "B"));
        mockMvc.perform(get("/api/products/max-price/50")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test @Order(7) @DisplayName("GET /api/products/{id} → 404")
    void shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/products/9999")).andExpect(status().isNotFound());
    }

    @Test @Order(8) @DisplayName("PUT /api/products/{id} → update")
    void shouldUpdate() throws Exception {
        Product saved = productRepository.save(createProduct("Old", "Old", 1.0, false, "Old"));
        Product upd = createProduct("New", "New", 999.0, true, "New");
        mockMvc.perform(put("/api/products/" + saved.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("New"));
    }

    @Test @Order(9) @DisplayName("DELETE /api/products/{id} → 204")
    void shouldDelete() throws Exception {
        Product saved = productRepository.save(createProduct("Temp", "T", 1.0, false, "T"));
        mockMvc.perform(delete("/api/products/" + saved.getId())).andExpect(status().isNoContent());
    }

    @Test @Order(10) @DisplayName("DELETE /api/products/{id} → 404")
    void shouldReturn404OnDelete() throws Exception {
        mockMvc.perform(delete("/api/products/9999")).andExpect(status().isNotFound());
    }
}
