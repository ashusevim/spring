package com.springarena.repository;

import com.springarena.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long> {
    List<StockItem> findByLocation(String location);
    Optional<StockItem> findBySku(String sku);
}
