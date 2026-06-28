package com.springarena.service;

import com.springarena.model.Product;
import com.springarena.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public Optional<Product> updateProduct(Long id, Product product) {
        Optional<Product> foundProduct = repo.findById(id);
        if (foundProduct.isPresent()) {
            Product pr = foundProduct.get();
            pr.setName(product.getName());
            pr.setDescription(product.getDescription());
            pr.setPrice(product.getPrice());
            pr.setQuantity(product.getQuantity());
            return Optional.of(repo.save(pr));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> product = repo.findById(id);
        if (product.isPresent()) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
