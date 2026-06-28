package com.springarena.controller;

import com.springarena.dto.InventoryRequestDTO;
import com.springarena.dto.InventoryResponseDTO;
import com.springarena.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponseDTO> getAllItems() {
        // TODO: Get all items via inventoryService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getItemById(@PathVariable Long id) {
        // TODO: Get item by id or return 404
        return null;
    }

    @GetMapping("/category/{category}")
    public List<InventoryResponseDTO> getItemsByCategory(@PathVariable String category) {
        // TODO: Get items by category via inventoryService
        return null;
    }

    @GetMapping("/supplier/{supplier}")
    public List<InventoryResponseDTO> getItemsBySupplier(@PathVariable String supplier) {
        // TODO: Get items by supplier via inventoryService
        return null;
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createItem(@RequestBody InventoryRequestDTO dto) {
        // TODO: Create item and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> updateItem(@PathVariable Long id, @RequestBody InventoryRequestDTO dto) {
        // TODO: Update item and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        // TODO: Delete item and return 204 or 404
        return null;
    }
}
