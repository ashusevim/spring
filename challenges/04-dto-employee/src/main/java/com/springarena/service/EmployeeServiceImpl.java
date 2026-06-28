package com.springarena.service;

import com.springarena.dto.EmployeeRequestDTO;
import com.springarena.dto.EmployeeResponseDTO;
import com.springarena.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        // TODO: Get all employees and map them to EmployeeResponseDTOs (with computed fullName)
        return null;
    }

    @Override
    public Optional<EmployeeResponseDTO> getEmployeeById(Long id) {
        // TODO: Find employee by id, convert to DTO (with computed fullName)
        return Optional.empty();
    }

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        // TODO: Create employee from request DTO, save, convert to response DTO (with computed fullName)
        return null;
    }

    @Override
    public Optional<EmployeeResponseDTO> updateEmployee(Long id, EmployeeRequestDTO dto) {
        // TODO: Update employee from request DTO, convert to response DTO (with computed fullName)
        return Optional.empty();
    }

    @Override
    public boolean deleteEmployee(Long id) {
        // TODO: Delete employee and return true if existed, false otherwise
        return false;
    }
}
