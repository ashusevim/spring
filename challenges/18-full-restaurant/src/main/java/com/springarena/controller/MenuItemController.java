package com.springarena.controller;

import com.springarena.dto.MenuItemRequestDTO;
import com.springarena.dto.MenuItemResponseDTO;
import com.springarena.service.MenuItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<MenuItemResponseDTO> getAllItems() {
        // TODO: Get all items via menuItemService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> getItemById(@PathVariable Long id) {
        // TODO: Get item by id or return 404
        return null;
    }

    @GetMapping("/category/{category}")
    public List<MenuItemResponseDTO> getItemsByCategory(@PathVariable String category) {
        // TODO: Get items by category via menuItemService
        return null;
    }

    @GetMapping("/vegetarian")
    public List<MenuItemResponseDTO> getVegetarianItems() {
        // TODO: Get vegetarian items via menuItemService
        return null;
    }

    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> createItem(@RequestBody MenuItemRequestDTO dto) {
        // TODO: Create item and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> updateItem(@PathVariable Long id, @RequestBody MenuItemRequestDTO dto) {
        // TODO: Update item and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        // TODO: Delete item and return 204 or 404
        return null;
    }
}
