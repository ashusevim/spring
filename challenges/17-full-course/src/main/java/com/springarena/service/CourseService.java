package com.springarena.service;

import com.springarena.dto.CourseRequestDTO;
import com.springarena.dto.CourseResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseResponseDTO> getAllCourses();
    Optional<CourseResponseDTO> getCourseById(Long id);
    List<CourseResponseDTO> getCoursesByCategory(String category);
    List<CourseResponseDTO> getCoursesByInstructor(String instructor);
    CourseResponseDTO createCourse(CourseRequestDTO dto);
    Optional<CourseResponseDTO> updateCourse(Long id, CourseRequestDTO dto);
    boolean deleteCourse(Long id);
}
