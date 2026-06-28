package com.springarena.controller;

import com.springarena.dto.CourseRequestDTO;
import com.springarena.dto.CourseResponseDTO;
import com.springarena.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponseDTO> getAllCourses() {
        // TODO: Get all courses via courseService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        // TODO: Get course by id or return 404
        return null;
    }

    @GetMapping("/category/{category}")
    public List<CourseResponseDTO> getCoursesByCategory(@PathVariable String category) {
        // TODO: Get courses by category via courseService
        return null;
    }

    @GetMapping("/instructor/{instructor}")
    public List<CourseResponseDTO> getCoursesByInstructor(@PathVariable String instructor) {
        // TODO: Get courses by instructor via courseService
        return null;
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO dto) {
        // TODO: Create course and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseRequestDTO dto) {
        // TODO: Update course and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        // TODO: Delete course and return 204 or 404
        return null;
    }
}
