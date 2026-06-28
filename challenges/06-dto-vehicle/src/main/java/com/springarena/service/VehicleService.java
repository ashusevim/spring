package com.springarena.service;

import com.springarena.dto.VehicleRequestDTO;
import com.springarena.dto.VehicleResponseDTO;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<VehicleResponseDTO> getAllVehicles();
    Optional<VehicleResponseDTO> getVehicleById(Long id);
    VehicleResponseDTO createVehicle(VehicleRequestDTO dto);
    Optional<VehicleResponseDTO> updateVehicle(Long id, VehicleRequestDTO dto);
    boolean deleteVehicle(Long id);
}
