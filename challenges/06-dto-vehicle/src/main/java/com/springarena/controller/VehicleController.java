package com.springarena.controller;

import com.springarena.dto.VehicleRequestDTO;
import com.springarena.dto.VehicleResponseDTO;
import com.springarena.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<VehicleResponseDTO> getAllVehicles() {
        // TODO: Get all vehicles via vehicleService
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long id) {
        // TODO: Get vehicle by id via vehicleService, map to response DTO, and return 200 or 404
        return vehicleService.getVehicleById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@RequestBody VehicleRequestDTO req) {
        // TODO: Create vehicle via vehicleService, convert to response DTO, and return 201
        VehicleResponseDTO created = vehicleService.createVehicle(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequestDTO req) {
        // TODO: Update vehicle via vehicleService, convert to response DTO, and return 200 or 404
        return vehicleService.updateVehicle(id, req)
                .map(dto -> ResponseEntity.ok(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        // TODO: Delete vehicle via vehicleService, return 204 or 404
        if (vehicleService.deleteVehicle(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
