package com.springarena.service;

import com.springarena.dto.MenuItemRequestDTO;
import com.springarena.dto.MenuItemResponseDTO;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    List<MenuItemResponseDTO> getAllItems();
    Optional<MenuItemResponseDTO> getItemById(Long id);
    List<MenuItemResponseDTO> getItemsByCategory(String category);
    List<MenuItemResponseDTO> getVegetarianItems();
    MenuItemResponseDTO createItem(MenuItemRequestDTO dto);
    Optional<MenuItemResponseDTO> updateItem(Long id, MenuItemRequestDTO dto);
    boolean deleteItem(Long id);
}
