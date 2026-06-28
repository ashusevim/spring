package com.springarena.service;

import com.springarena.dto.StockItemRequestDTO;
import com.springarena.dto.StockItemResponseDTO;

import java.util.List;
import java.util.Optional;

public interface StockItemService {
    List<StockItemResponseDTO> getAllItems();
    Optional<StockItemResponseDTO> getItemById(Long id);
    List<StockItemResponseDTO> getItemsByLocation(String location);
    Optional<StockItemResponseDTO> getItemBySku(String sku);
    StockItemResponseDTO createItem(StockItemRequestDTO dto);
    Optional<StockItemResponseDTO> updateItem(Long id, StockItemRequestDTO dto);
    boolean deleteItem(Long id);
}
