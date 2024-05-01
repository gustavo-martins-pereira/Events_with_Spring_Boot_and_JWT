package com.vitai.events.usecases.event;

import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteEventByIdUsecase {

    @Autowired
    private EventRepository eventRepository;

    public ResponseEntity<Object> execute(UUID id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) return ResponseEntity.notFound().build();

        Event event = optionalEvent.get();
        eventRepository.delete(event);

        return ResponseEntity.noContent().build();
    }
}
