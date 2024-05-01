package com.vitai.events.usecases.event;

import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateEventUsecase {

    @Autowired
    private EventRepository eventRepository;

    public ResponseEntity<Event> execute(Event event) {
        Event createdEvent = eventRepository.save(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

}
