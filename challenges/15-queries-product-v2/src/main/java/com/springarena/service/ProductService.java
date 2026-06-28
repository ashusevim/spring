package com.springarena.service;

import com.springarena.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getInStockProducts();
    List<Product> getProductsByMaxPrice(Double maxPrice);
    Product createProduct(Product product);
    Optional<Product> updateProduct(Long id, Product product);
    boolean deleteProduct(Long id);
}
