package com.vitai.events.usecases.event;

import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateEventUsecase {

    @Autowired
    private EventRepository eventRepository;

    public Event execute(Event event) {
        return eventRepository.save(event);
    }

}
