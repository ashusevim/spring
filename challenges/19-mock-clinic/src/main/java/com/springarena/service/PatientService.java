package com.springarena.service;

import com.springarena.dto.PatientRequestDTO;
import com.springarena.dto.PatientResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<PatientResponseDTO> getAllPatients();
    Optional<PatientResponseDTO> getPatientById(Long id);
    List<PatientResponseDTO> getPatientsByDoctor(String doctor);
    List<PatientResponseDTO> getPatientsByCondition(String condition);
    PatientResponseDTO createPatient(PatientRequestDTO dto);
    Optional<PatientResponseDTO> updatePatient(Long id, PatientRequestDTO dto);
    boolean deletePatient(Long id);
}
