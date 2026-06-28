package com.springarena.service;

import com.springarena.dto.TaskRequestDTO;
import com.springarena.dto.TaskResponseDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskResponseDTO> getAllTasks();
    Optional<TaskResponseDTO> getTaskById(Long id);
    TaskResponseDTO createTask(TaskRequestDTO dto);
    Optional<TaskResponseDTO> updateTask(Long id, TaskRequestDTO dto);
    boolean deleteTask(Long id);
}
