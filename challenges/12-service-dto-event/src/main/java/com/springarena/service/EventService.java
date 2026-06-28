package com.springarena.service;

import com.springarena.dto.EventRequestDTO;
import com.springarena.dto.EventResponseDTO;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<EventResponseDTO> getAllEvents();
    Optional<EventResponseDTO> getEventById(Long id);
    EventResponseDTO createEvent(EventRequestDTO dto);
    Optional<EventResponseDTO> updateEvent(Long id, EventRequestDTO dto);
    boolean deleteEvent(Long id);
}
