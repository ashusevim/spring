package com.springarena.service;

import com.springarena.dto.VehicleRequestDTO;
import com.springarena.dto.VehicleResponseDTO;
import com.springarena.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<VehicleResponseDTO> getAllVehicles() {
        // TODO: Get all vehicles, map to response DTOs
        return null;
    }

    @Override
    public Optional<VehicleResponseDTO> getVehicleById(Long id) {
        // TODO: Find vehicle by id, map to response DTO, and return Optional
        return Optional.empty();
    }

    @Override
    public VehicleResponseDTO createVehicle(VehicleRequestDTO dto) {
        // TODO: Create vehicle, convert to response DTO, and return
        return null;
    }

    @Override
    public Optional<VehicleResponseDTO> updateVehicle(Long id, VehicleRequestDTO dto) {
        // TODO: Update vehicle, convert to response DTO, and return Optional
        return Optional.empty();
    }

    @Override
    public boolean deleteVehicle(Long id) {
        // TODO: Delete vehicle and return true if existed, false otherwise
        return false;
    }
}
