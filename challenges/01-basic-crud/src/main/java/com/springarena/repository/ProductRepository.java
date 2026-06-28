package com.springarena.repository;

// TODO: Create a repository interface for Product
// It should extend JpaRepository<Product, Long>

import org.springframework.data.jpa.repository.JpaRepository;

import com.springarena.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}