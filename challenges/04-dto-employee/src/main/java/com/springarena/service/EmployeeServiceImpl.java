package com.springarena.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springarena.dto.EmployeeRequestDTO;
import com.springarena.dto.EmployeeResponseDTO;
import com.springarena.model.Employee;
import com.springarena.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        // TODO: Get all employees and map them to EmployeeResponseDTOs (with computed
        // fullName)
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> ans = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
            employeeResponseDTO.setId(employee.getId());
            employeeResponseDTO.setFullName(employee.getFirstName());
            employeeResponseDTO.setEmail(employee.getEmail());
            employeeResponseDTO.setDepartment(employee.getDepartment());
            employeeResponseDTO.setSalary(employee.getSalary());
            ans.add(employeeResponseDTO);
        }
        return ans;
    }

    @Override
    public Optional<EmployeeResponseDTO> getEmployeeById(Long id) {
        // TODO: Find employee by id, convert to DTO (with computed fullName)
        Optional<Employee> emp = employeeRepository.findById(id);
        if (emp.isPresent()) {
            Employee employee = emp.get();
            EmployeeResponseDTO dto = new EmployeeResponseDTO();
            dto.setId(employee.getId());
            dto.setFullName(employee.getFirstName() + " " + employee.getLastName());
            dto.setEmail(employee.getEmail());
            dto.setDepartment(employee.getDepartment());
            dto.setSalary(employee.getSalary());

            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        // TODO: Create employee from request DTO, save, convert to response DTO (with
        // computed fullName)

        Employee newEmp = new Employee();
        newEmp.setFirstName(dto.getFirstName());
        newEmp.setLastName(dto.getLastName());
        newEmp.setEmail(dto.getEmail());
        newEmp.setDepartment(dto.getDepartment());
        newEmp.setSalary(dto.getSalary());

        employeeRepository.save(newEmp);

        EmployeeResponseDTO ansdto = new EmployeeResponseDTO();
        ansdto.setId(newEmp.getId());
        ansdto.setFullName(newEmp.getFirstName() + " " + newEmp.getLastName());
        ansdto.setEmail(newEmp.getEmail());
        ansdto.setDepartment(newEmp.getDepartment());
        ansdto.setSalary(newEmp.getSalary());

        return ansdto;
    }

    @Override
    public Optional<EmployeeResponseDTO> updateEmployee(Long id, EmployeeRequestDTO dto) {
        // TODO: Update employee from request DTO, convert to response DTO (with
        // computed fullName)

        Optional<Employee> found = employeeRepository.findById(id);

        if (found.isPresent()) {
            found.get().setFirstName(dto.getFirstName());
            found.get().setLastName(dto.getLastName());
            found.get().setEmail(dto.getEmail());
            found.get().setDepartment(dto.getDepartment());
            found.get().setSalary(dto.getSalary());

            employeeRepository.save(found.get());

            EmployeeResponseDTO ddto = new EmployeeResponseDTO();
            ddto.setId(found.get().getId());
            ddto.setFullName(found.get().getFirstName() + " " + found.get().getLastName());
            ddto.setEmail(found.get().getEmail());
            ddto.setDepartment(found.get().getDepartment());
            ddto.setSalary(found.get().getSalary());
            return Optional.of(ddto);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteEmployee(Long id) {
        // TODO: Delete employee and return true if existed, false otherwise
        Optional<Employee> found = employeeRepository.findById(id);
        if (found.isPresent()) {
            employeeRepository.delete(found.get());
            return true;
        }
        return false;
    }
}
