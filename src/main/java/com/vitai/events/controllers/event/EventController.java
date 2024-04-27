package com.vitai.events.controllers.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.domain.Event;
import com.vitai.events.usecases.event.CreateEventUsecase;
import com.vitai.events.usecases.event.GetAllEventsUsecase;
import com.vitai.events.usecases.event.GetEventByIdUsecase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private GetAllEventsUsecase getAllEventsUsecase;

    @Autowired
    private GetEventByIdUsecase getEventByIdUsecase;

    @Autowired
    private CreateEventUsecase createEventUsecase;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> eventsDTO = getAllEventsUsecase.execute().stream().map(EventDTO::eventToDTO).toList();

        return ResponseEntity.ok(eventsDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable UUID id) {
        var eventOptional = getEventByIdUsecase.execute(id);

        return eventOptional
                .map(event -> ResponseEntity.ok(EventDTO.eventToDTO(event)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventDTO eventDTO) {
        Event createdEvent = createEventUsecase.execute(EventDTO.dtoToEvent(eventDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

}
