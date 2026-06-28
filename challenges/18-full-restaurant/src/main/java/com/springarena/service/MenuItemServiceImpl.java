package com.springarena.service;

import com.springarena.dto.MenuItemRequestDTO;
import com.springarena.dto.MenuItemResponseDTO;
import com.springarena.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItemResponseDTO> getAllItems() {
        // TODO: Get all menu items, map to ResponseDTOs
        return null;
    }

    @Override
    public Optional<MenuItemResponseDTO> getItemById(Long id) {
        // TODO: Get item by id, map to ResponseDTO
        return Optional.empty();
    }

    @Override
    public List<MenuItemResponseDTO> getItemsByCategory(String category) {
        // TODO: Get items by category, map to ResponseDTOs
        return null;
    }

    @Override
    public List<MenuItemResponseDTO> getVegetarianItems() {
        // TODO: Get vegetarian items, map to ResponseDTOs
        return null;
    }

    @Override
    public MenuItemResponseDTO createItem(MenuItemRequestDTO dto) {
        // TODO: Map RequestDTO to Entity, save, map to ResponseDTO
        return null;
    }

    @Override
    public Optional<MenuItemResponseDTO> updateItem(Long id, MenuItemRequestDTO dto) {
        // TODO: Update item if exists, return updated ResponseDTO
        return Optional.empty();
    }

    @Override
    public boolean deleteItem(Long id) {
        // TODO: Delete item and return true if existed, false otherwise
        return false;
    }
}
