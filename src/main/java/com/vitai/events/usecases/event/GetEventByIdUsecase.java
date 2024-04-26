package com.vitai.events.usecases.event;

import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetEventByIdUsecase {

    @Autowired
    private EventRepository eventRepository;

    public Optional<Event> execute(UUID id) {
        return eventRepository.findById(id);
    }

}
