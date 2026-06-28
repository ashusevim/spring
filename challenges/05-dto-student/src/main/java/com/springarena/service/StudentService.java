package com.springarena.service;

import com.springarena.dto.StudentRequestDTO;
import com.springarena.dto.StudentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentResponseDTO> getAllStudents();
    Optional<StudentResponseDTO> getStudentById(Long id);
    StudentResponseDTO createStudent(StudentRequestDTO dto);
    Optional<StudentResponseDTO> updateStudent(Long id, StudentRequestDTO dto);
    boolean deleteStudent(Long id);
}
