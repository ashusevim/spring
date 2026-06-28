package com.springarena.service;

import com.springarena.dto.EmployeeRequestDTO;
import com.springarena.dto.EmployeeResponseDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeResponseDTO> getAllEmployees();
    Optional<EmployeeResponseDTO> getEmployeeById(Long id);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);
    Optional<EmployeeResponseDTO> updateEmployee(Long id, EmployeeRequestDTO dto);
    boolean deleteEmployee(Long id);
}
