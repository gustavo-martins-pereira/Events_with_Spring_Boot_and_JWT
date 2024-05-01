package com.vitai.events.services.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import com.vitai.events.usecases.event.GetAllEventsUsecase;
import com.vitai.events.usecases.event.GetEventByIdUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

@SpringBootTest
class GetEventByIdUsecaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private GetEventByIdUsecase getEventByIdUsecase;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a single Event by Id with code 200")
    void testGetEventByIdSuccess()  {
        Event event = new Event();
        event.setId(UUID.randomUUID());
        when(this.eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        ResponseEntity<EventDTO> response = this.getEventByIdUsecase.execute(event.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(EventDTO.class, response.getBody());

        verify(this.eventRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should not return an Event with code 204")
    void testGetEventByIdWithNoEvents()  {
        UUID id = UUID.randomUUID();
        when(this.eventRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<EventDTO> response = this.getEventByIdUsecase.execute(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(this.eventRepository, times(1)).findById(any());
    }
}
