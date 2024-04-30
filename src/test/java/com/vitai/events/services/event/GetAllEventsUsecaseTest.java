package com.vitai.events.services.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import com.vitai.events.usecases.event.GetAllEventsUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetAllEventsUsecaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private GetAllEventsUsecase getAllEventsUsecase;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should show a list of Events with code 200")
    void testGetAllEventsSuccess()  {
        List<Event> eventsMock = Arrays.asList(new Event(), new Event(), new Event());
        when(this.eventRepository.findAll()).thenReturn(eventsMock);

        List<EventDTO> events = this.getAllEventsUsecase.execute();

        assertNotNull(events);
        assertFalse(events.isEmpty());

        for (EventDTO eventDTO : events) {
            assertInstanceOf(EventDTO.class, eventDTO);
        }
    }
}
