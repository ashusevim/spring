package com.springarena.service;

import com.springarena.dto.StudentRequestDTO;
import com.springarena.dto.StudentResponseDTO;
import com.springarena.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        // TODO: Get all students, map them to StudentResponseDTOs, and return them
        return null;
    }

    @Override
    public Optional<StudentResponseDTO> getStudentById(Long id) {
        // TODO: Find student by id, map to Response DTO, and return Optional
        return Optional.empty();
    }

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        // TODO: Create student, convert to Response DTO, and return
        return null;
    }

    @Override
    public Optional<StudentResponseDTO> updateStudent(Long id, StudentRequestDTO dto) {
        // TODO: Update student, convert to Response DTO, and return Optional
        return Optional.empty();
    }

    @Override
    public boolean deleteStudent(Long id) {
        // TODO: Delete student and return true if existed, false otherwise
        return false;
    }
}
