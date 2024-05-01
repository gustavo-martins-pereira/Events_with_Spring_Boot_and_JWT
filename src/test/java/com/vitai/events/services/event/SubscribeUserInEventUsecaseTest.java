package com.vitai.events.services.event;

import com.vitai.events.domain.Event;
import com.vitai.events.domain.User;
import com.vitai.events.repositories.EventRepository;
import com.vitai.events.repositories.UserRepository;
import com.vitai.events.usecases.event.SubscribeInEventUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubscribeUserInEventUsecaseTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SubscribeInEventUsecase subscribeInEventUsecase;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should subscribe user to event successfully")
    void testSubscribeUserToEventSuccess() {
        UUID eventId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .events(new HashSet<>())
                .build();

        Event event = Event.builder()
                .id(userId)
                .users(new HashSet<>())
                .build();

        event.setMaxCapacity(10);

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(this.eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ResponseEntity<Object> response = this.subscribeInEventUsecase.execute(eventId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(this.eventRepository, times(1)).save(event);
        verify(this.userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should return 404 when user not found")
    void testSubscribeUserToEventUserNotFound() {
        UUID eventId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(this.userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = this.subscribeInEventUsecase.execute(eventId, userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(this.eventRepository, never()).save(any());
        verify(this.userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return 404 when event not found")
    void testSubscribeUserToEventEventNotFound() {
        UUID eventId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(this.eventRepository.findById(eventId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = this.subscribeInEventUsecase.execute(eventId, userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("User or Event not found"));

        verify(this.eventRepository, never()).save(any());
        verify(this.userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return 400 when event is already full")
    void testSubscribeUserToEventEventFull() {
        UUID eventId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Event event = Event.builder()
                .id(eventId)
                .maxCapacity(0)
                .users(new HashSet<>())
                .build();

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(this.eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ResponseEntity<Object> response = this.subscribeInEventUsecase.execute(eventId, userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("The event is already full"));

        verify(this.eventRepository, never()).save(any());
        verify(this.userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return 400 when user is already subscribed to the event")
    void testSubscribeUserToEventUserAlreadySubscribed() {
        UUID eventId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Event event = Event.builder()
                .id(eventId)
                .maxCapacity(10)
                .users(new HashSet<>())
                .build();
        event.getUsers().add(user);

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(this.eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ResponseEntity<Object> response = this.subscribeInEventUsecase.execute(eventId, userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("The user is already registered for this event."));

        verify(this.eventRepository, never()).save(any());
        verify(this.userRepository, never()).save(any());
    }
}
