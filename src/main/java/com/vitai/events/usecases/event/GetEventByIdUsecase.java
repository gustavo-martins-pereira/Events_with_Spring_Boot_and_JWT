package com.vitai.events.usecases.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetEventByIdUsecase {

    @Autowired
    private EventRepository eventRepository;

    public ResponseEntity<EventDTO> execute(UUID id) {
        Optional<Event> eventOptional = this.eventRepository.findById(id);

        return eventOptional
                .map(event -> ResponseEntity.ok(EventDTO.eventToDTO(event)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
