package com.vitai.events.usecases.event;

import com.vitai.events.domain.Event;
import com.vitai.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllEventsUsecase {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> execute() {
        return eventRepository.findAll();
    }

}
