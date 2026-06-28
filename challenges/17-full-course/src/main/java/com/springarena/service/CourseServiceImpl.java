package com.springarena.service;

import com.springarena.dto.CourseRequestDTO;
import com.springarena.dto.CourseResponseDTO;
import com.springarena.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        // TODO: Get all courses, map to ResponseDTOs with spotsLeft computed
        return null;
    }

    @Override
    public Optional<CourseResponseDTO> getCourseById(Long id) {
        // TODO: Get course by id, map to ResponseDTO with spotsLeft
        return Optional.empty();
    }

    @Override
    public List<CourseResponseDTO> getCoursesByCategory(String category) {
        // TODO: Get courses by category, map to ResponseDTOs
        return null;
    }

    @Override
    public List<CourseResponseDTO> getCoursesByInstructor(String instructor) {
        // TODO: Get courses by instructor, map to ResponseDTOs
        return null;
    }

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {
        // TODO: Map RequestDTO to Entity, save, map to ResponseDTO
        return null;
    }

    @Override
    public Optional<CourseResponseDTO> updateCourse(Long id, CourseRequestDTO dto) {
        // TODO: Update course if exists, return updated ResponseDTO
        return Optional.empty();
    }

    @Override
    public boolean deleteCourse(Long id) {
        // TODO: Delete course and return true if existed, false otherwise
        return false;
    }
}
