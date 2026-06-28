package com.springarena.controller;

import com.springarena.dto.PatientRequestDTO;
import com.springarena.dto.PatientResponseDTO;
import com.springarena.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientResponseDTO> getAllPatients() {
        // TODO: Get all patients via patientService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        // TODO: Get patient by id or return 404
        return null;
    }

    @GetMapping("/doctor/{doctor}")
    public List<PatientResponseDTO> getPatientsByDoctor(@PathVariable String doctor) {
        // TODO: Get patients by doctor via patientService
        return null;
    }

    @GetMapping("/condition/{condition}")
    public List<PatientResponseDTO> getPatientsByCondition(@PathVariable String condition) {
        // TODO: Get patients by condition via patientService
        return null;
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO dto) {
        // TODO: Create patient and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @RequestBody PatientRequestDTO dto) {
        // TODO: Update patient and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        // TODO: Delete patient and return 204 or 404
        return null;
    }
}
