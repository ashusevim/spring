package com.springarena.service;

import com.springarena.dto.TaskRequestDTO;
import com.springarena.dto.TaskResponseDTO;
import com.springarena.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        // TODO: Get all tasks, map to ResponseDTOs
        return null;
    }

    @Override
    public Optional<TaskResponseDTO> getTaskById(Long id) {
        // TODO: Get task by id, map to ResponseDTO
        return Optional.empty();
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        // TODO: Create task from request DTO, save, and return response DTO
        return null;
    }

    @Override
    public Optional<TaskResponseDTO> updateTask(Long id, TaskRequestDTO dto) {
        // TODO: Update task if exists, mapping to and from DTOs
        return Optional.empty();
    }

    @Override
    public boolean deleteTask(Long id) {
        // TODO: Delete task by id and return true if existed, false otherwise
        return false;
    }
}
