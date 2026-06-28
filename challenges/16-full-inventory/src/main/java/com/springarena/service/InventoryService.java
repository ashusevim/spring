package com.springarena.service;

import com.springarena.dto.InventoryRequestDTO;
import com.springarena.dto.InventoryResponseDTO;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    List<InventoryResponseDTO> getAllItems();
    Optional<InventoryResponseDTO> getItemById(Long id);
    List<InventoryResponseDTO> getItemsByCategory(String category);
    List<InventoryResponseDTO> getItemsBySupplier(String supplier);
    InventoryResponseDTO createItem(InventoryRequestDTO dto);
    Optional<InventoryResponseDTO> updateItem(Long id, InventoryRequestDTO dto);
    boolean deleteItem(Long id);
}
