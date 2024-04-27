package com.vitai.events.controllers.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.controllers.event.dtos.SubscribeUserInEventDTO;
import com.vitai.events.domain.Event;
import com.vitai.events.usecases.event.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @Autowired
    private SubscribeInEventUsecase subscribeInEventUsecase;

    @Autowired
    private UpdateEventByIdUsecase updateEventByIdUsecase;

    @Autowired
    private DeleteEventByIdUsecase deleteEventByIdUsecase;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> eventsDTO = getAllEventsUsecase.execute().stream().map(EventDTO::eventToDTO).toList();

        return ResponseEntity.ok(eventsDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable UUID id) {
        Optional<Event> eventOptional = getEventByIdUsecase.execute(id);

        return eventOptional
                .map(event -> ResponseEntity.ok(EventDTO.eventToDTO(event)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventDTO eventDTO) {
        Event createdEvent = createEventUsecase.execute(EventDTO.dtoToEvent(eventDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PostMapping(value = "/subscribe")
    public ResponseEntity<Object> subscribeUserInEvent(@RequestBody @Valid SubscribeUserInEventDTO subscribeUserInEventDTO) {
        return subscribeInEventUsecase.execute(UUID.fromString(subscribeUserInEventDTO.getEventId()), UUID.fromString(subscribeUserInEventDTO.getUserId()));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Event> updateEventById(@PathVariable UUID id, @RequestBody @Valid EventDTO eventDTO) {
        Event newEvent = EventDTO.dtoToEvent(eventDTO);
        Optional<Event> optionalUpdatedEvent = updateEventByIdUsecase.execute(id, newEvent);

        if (optionalUpdatedEvent.isEmpty()) return ResponseEntity.notFound().build();

        Event updatedEvent = optionalUpdatedEvent.get();

        return ResponseEntity.ok(updatedEvent);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Event> deleteEventById(@PathVariable UUID id) {
        Optional<Event> optionalDeletedEvent = deleteEventByIdUsecase.execute(id);

        if (optionalDeletedEvent.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

}
