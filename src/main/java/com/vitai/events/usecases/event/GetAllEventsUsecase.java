package com.vitai.events.usecases.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllEventsUsecase {

    @Autowired
    private EventRepository eventRepository;

    public ResponseEntity<List<EventDTO>> execute() {
        List<EventDTO> eventsDTO = this.eventRepository.findAll().stream().map(EventDTO::eventToDTO).toList();

        return ResponseEntity.ok(eventsDTO);

    }

}
