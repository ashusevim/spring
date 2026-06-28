package com.springarena.controller;

import com.springarena.dto.StockItemRequestDTO;
import com.springarena.dto.StockItemResponseDTO;
import com.springarena.service.StockItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class StockItemController {

    private final StockItemService stockItemService;

    public StockItemController(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    @GetMapping
    public List<StockItemResponseDTO> getAllItems() {
        // TODO: Get all items via stockItemService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockItemResponseDTO> getItemById(@PathVariable Long id) {
        // TODO: Get item by id or return 404
        return null;
    }

    @GetMapping("/location/{location}")
    public List<StockItemResponseDTO> getItemsByLocation(@PathVariable String location) {
        // TODO: Get items by location via stockItemService
        return null;
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<StockItemResponseDTO> getItemBySku(@PathVariable String sku) {
        // TODO: Get item by sku or return 404
        return null;
    }

    @PostMapping
    public ResponseEntity<StockItemResponseDTO> createItem(@RequestBody StockItemRequestDTO dto) {
        // TODO: Create item and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockItemResponseDTO> updateItem(@PathVariable Long id, @RequestBody StockItemRequestDTO dto) {
        // TODO: Update item and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        // TODO: Delete item and return 204 or 404
        return null;
    }
}
