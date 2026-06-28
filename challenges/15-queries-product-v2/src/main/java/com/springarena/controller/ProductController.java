package com.springarena.controller;

import com.springarena.model.Product;
import com.springarena.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        // TODO: Get all products via productService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        // TODO: Get product by id via productService or 404
        return null;
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        // TODO: Get products by category via productService
        return null;
    }

    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand) {
        // TODO: Get products by brand via productService
        return null;
    }

    @GetMapping("/in-stock")
    public List<Product> getInStockProducts() {
        // TODO: Get in-stock products via productService
        return null;
    }

    @GetMapping("/max-price/{maxPrice}")
    public List<Product> getProductsByMaxPrice(@PathVariable Double maxPrice) {
        // TODO: Get products by max price via productService
        return null;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // TODO: Create product and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        // TODO: Update product and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // TODO: Delete product and return 204 or 404
        return null;
    }
}
