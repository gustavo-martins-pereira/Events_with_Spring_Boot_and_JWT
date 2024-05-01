package com.vitai.events.usecases.event;

import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateEventByIdUsecase {

    @Autowired
    private EventRepository eventRepository;

    public ResponseEntity<Event> execute(UUID id, Event newEvent) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (optionalEvent.isEmpty()) return ResponseEntity.notFound().build();

        Event existingEvent = optionalEvent.get();

        existingEvent.setName(newEvent.getName());
        existingEvent.setDate(newEvent.getDate());
        existingEvent.setLocal(newEvent.getLocal());
        existingEvent.setMaxCapacity(newEvent.getMaxCapacity());

        Event updatedEvent = this.eventRepository.save(existingEvent);

        return ResponseEntity.ok(updatedEvent);
    }
}
