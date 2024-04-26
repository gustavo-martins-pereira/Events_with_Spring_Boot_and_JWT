package com.vitai.events.controllers;

import com.vitai.events.domain.Event;
import com.vitai.events.usecases.event.GetAllEventsUsecase;
import com.vitai.events.usecases.event.GetEventByIdUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private GetAllEventsUsecase getAllEventsUsecase;

    @Autowired
    private GetEventByIdUsecase getEventByIdUsecase;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(getAllEventsUsecase.execute());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID id) {
        var eventOptional = getEventByIdUsecase.execute(id);
        System.out.println(id);

        return eventOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

}
