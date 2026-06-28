package com.springarena.service;

import com.springarena.dto.PatientRequestDTO;
import com.springarena.dto.PatientResponseDTO;
import com.springarena.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        // TODO: Get all patients, map to ResponseDTOs with fullName computed from firstName + " " + lastName
        return null;
    }

    @Override
    public Optional<PatientResponseDTO> getPatientById(Long id) {
        // TODO: Get patient by id, map to ResponseDTO with fullName
        return Optional.empty();
    }

    @Override
    public List<PatientResponseDTO> getPatientsByDoctor(String doctor) {
        // TODO: Get patients by doctor, map to ResponseDTOs
        return null;
    }

    @Override
    public List<PatientResponseDTO> getPatientsByCondition(String condition) {
        // TODO: Get patients by condition, map to ResponseDTOs
        return null;
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        // TODO: Map RequestDTO to Entity, save, map to ResponseDTO
        return null;
    }

    @Override
    public Optional<PatientResponseDTO> updatePatient(Long id, PatientRequestDTO dto) {
        // TODO: Update patient if exists, return updated ResponseDTO
        return Optional.empty();
    }

    @Override
    public boolean deletePatient(Long id) {
        // TODO: Delete patient and return true if existed, false otherwise
        return false;
    }
}
