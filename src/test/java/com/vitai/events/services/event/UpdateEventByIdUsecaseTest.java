package com.vitai.events.services.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import com.vitai.events.usecases.event.UpdateEventByIdUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdateEventByIdUsecaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private UpdateEventByIdUsecase updateEventByIdUsecase;

    @Test
    @DisplayName("Should update event by ID successfully")
    void testUpdateEventByIdSuccess() {
        UUID eventId = UUID.randomUUID();
        Event originalEvent = Event.builder()
                .id(eventId)
                .name("Original event")
                .build();
        Event editedEvent = Event.builder()
                .id(eventId)
                .name("Edited event")
                .build();

        when(this.eventRepository.findById(eventId)).thenReturn(Optional.of(originalEvent));
        when(this.eventRepository.save(editedEvent)).thenReturn(editedEvent);

        ResponseEntity<Event> response = this.updateEventByIdUsecase.execute(eventId, editedEvent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(editedEvent, response.getBody());
        assertInstanceOf(Event.class, response.getBody());

        verify(this.eventRepository, times(1)).findById(any(UUID.class));
        verify(this.eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should return 404 when event to update is not found")
    void testUpdateEventByIdNotFound() {
        UUID eventId = UUID.randomUUID();
        EventDTO eventDTO = new EventDTO();

        when(this.eventRepository.findById(eventId)).thenReturn(Optional.empty());

        ResponseEntity<Event> response = this.updateEventByIdUsecase.execute(eventId, EventDTO.dtoToEvent(eventDTO));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(this.eventRepository, times(1)).findById(any(UUID.class));
        verify(this.eventRepository, never()).save(any(Event.class));
    }
}
