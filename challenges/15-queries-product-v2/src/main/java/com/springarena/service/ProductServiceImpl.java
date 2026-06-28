package com.springarena.service;

import com.springarena.model.Product;
import com.springarena.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        // TODO: Get all products
        return null;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        // TODO: Get product by id
        return Optional.empty();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        // TODO: Get products by category via custom query
        return null;
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        // TODO: Get products by brand via custom query
        return null;
    }

    @Override
    public List<Product> getInStockProducts() {
        // TODO: Get in-stock products via custom query
        return null;
    }

    @Override
    public List<Product> getProductsByMaxPrice(Double maxPrice) {
        // TODO: Get products by max price via custom query
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        // TODO: Create product
        return null;
    }

    @Override
    public Optional<Product> updateProduct(Long id, Product product) {
        // TODO: Update product if exists
        return Optional.empty();
    }

    @Override
    public boolean deleteProduct(Long id) {
        // TODO: Delete product and return true if existed, false otherwise
        return false;
    }
}
