package com.vitai.events.services.event;

import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import com.vitai.events.usecases.event.DeleteEventByIdUsecase;
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
public class DeleteEventByIdUsecaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private DeleteEventByIdUsecase deleteEventByIdUsecase;

    @Test
    @DisplayName("Should delete event by ID successfully")
    void testDeleteEventByIdSuccess() {
        UUID eventId = UUID.randomUUID();
        Event event = Event.builder()
                .id(eventId)
                .name("Test event")
                .build();

        when(this.eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ResponseEntity<Object> response = this.deleteEventByIdUsecase.execute(eventId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(this.eventRepository, times(1)).findById(any(UUID.class));
        verify(this.eventRepository, times(1)).delete(any(Event.class));
    }

    @Test
    @DisplayName("Should return 404 when event to delete is not found")
    void testDeleteEventByIdNotFound() {
        UUID eventId = UUID.randomUUID();

        when(this.eventRepository.findById(eventId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = this.deleteEventByIdUsecase.execute(eventId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(this.eventRepository, times(1)).findById(any(UUID.class));
        verify(this.eventRepository, never()).delete(any(Event.class));
    }

}
