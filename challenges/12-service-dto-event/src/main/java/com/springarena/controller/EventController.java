package com.springarena.controller;

import com.springarena.dto.EventRequestDTO;
import com.springarena.dto.EventResponseDTO;
import com.springarena.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponseDTO> getAllEvents() {
        // TODO: Return all events as ResponseDTOs via eventService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {
        // TODO: Return EventResponseDTO by ID or 404
        return null;
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO dto) {
        // TODO: Create event and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable Long id, @RequestBody EventRequestDTO dto) {
        // TODO: Update event and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        // TODO: Delete event and return 204 or 404
        return null;
    }
}
