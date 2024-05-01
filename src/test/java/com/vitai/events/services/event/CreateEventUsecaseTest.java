package com.vitai.events.services.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import com.vitai.events.usecases.event.CreateEventUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreateEventUsecaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private CreateEventUsecase createEventUsecase;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new Event with success returning 201")
    void testCreateNewEventSuccess() {
        EventDTO eventDTO = EventDTO.builder()
                .name("New event")
                .local("Location")
                .date(Instant.now())
                .maxCapacity(100)
                .build();

        when(this.eventRepository.save(EventDTO.dtoToEvent(eventDTO))).thenReturn(new Event());

        ResponseEntity<Event> response = this.createEventUsecase.execute(EventDTO.dtoToEvent(eventDTO));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertInstanceOf(Event.class, response.getBody());

        verify(this.eventRepository, times(1)).save(any(Event.class));
    }

}
