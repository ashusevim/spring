package com.springarena.service;

import com.springarena.dto.EventRequestDTO;
import com.springarena.dto.EventResponseDTO;
import com.springarena.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventResponseDTO> getAllEvents() {
        // TODO: Get all events, map to ResponseDTOs with schedule = startTime + " to " + endTime
        return null;
    }

    @Override
    public Optional<EventResponseDTO> getEventById(Long id) {
        // TODO: Get event by id, map to ResponseDTO
        return Optional.empty();
    }

    @Override
    public EventResponseDTO createEvent(EventRequestDTO dto) {
        // TODO: Create event
        return null;
    }

    @Override
    public Optional<EventResponseDTO> updateEvent(Long id, EventRequestDTO dto) {
        // TODO: Update event if exists
        return Optional.empty();
    }

    @Override
    public boolean deleteEvent(Long id) {
        // TODO: Delete event
        return false;
    }
}
