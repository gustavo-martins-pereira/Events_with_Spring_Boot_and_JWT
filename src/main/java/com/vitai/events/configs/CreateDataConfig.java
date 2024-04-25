package com.vitai.events.configs;

import com.vitai.events.domain.Event;
import com.vitai.events.domain.User;
import com.vitai.events.domain.enums.UserRole;
import com.vitai.events.repositories.EventRepository;
import com.vitai.events.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CreateDataConfig implements CommandLineRunner {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Events
        List<Event> events = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            events.add(Event.builder()
                    .name("Event " + i)
                    .date(Instant.now().plusSeconds(60 * 60 * 24 * i))
                    .local(String.valueOf(i))
                    .maxCapacity(100 * i)
                    .build()
            );
        }

        eventRepository.saveAll(events);

        // Users
        List<User> users = new ArrayList<>();

        for (int i = 1; i < 25; i++) {
            users.add(User.builder()
                    .name("User " + i)
                    .role(i % 2 == 0 ? UserRole.ADMIN : UserRole.USER)
                    .build()
            );
        }

        userRepository.saveAll(users);
    }

}
