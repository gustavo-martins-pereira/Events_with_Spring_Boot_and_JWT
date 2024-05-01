package com.vitai.events.usecases.event;

import com.vitai.events.domain.Event;
import com.vitai.events.domain.User;
import com.vitai.events.repositories.EventRepository;
import com.vitai.events.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubscribeInEventUsecase {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> execute(UUID eventId, UUID userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (existingUser.isEmpty() || optionalEvent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Event not found");
        }

        User user = existingUser.get();
        Event event = optionalEvent.get();

        if (event.getUsers().size() >= event.getMaxCapacity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The event is already full.");
        }

        if (event.getUsers().contains(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The user is already registered for this event.");
        }

        event.getUsers().add(user);
        user.getEvents().add(event);
        eventRepository.save(event);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}
