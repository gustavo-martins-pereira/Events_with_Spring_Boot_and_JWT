package com.vitai.events.controllers.event;

import com.vitai.events.controllers.event.dtos.EventDTO;
import com.vitai.events.controllers.event.dtos.SubscribeUserInEventDTO;
import com.vitai.events.domain.Event;
import com.vitai.events.usecases.event.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/events")
@Tag(name = "Event", description = "Endpoints for Event")
public class EventController {

    @Autowired
    private GetAllEventsUsecase getAllEventsUsecase;

    @Autowired
    private GetEventByIdUsecase getEventByIdUsecase;

    @Autowired
    private CreateEventUsecase createEventUsecase;

    @Autowired
    private SubscribeInEventUsecase subscribeInEventUsecase;

    @Autowired
    private UpdateEventByIdUsecase updateEventByIdUsecase;

    @Autowired
    private DeleteEventByIdUsecase deleteEventByIdUsecase;

    @GetMapping
    @Operation(
            summary = "Get all events - (Protected by Guards): ANY",
            description = "Retrieves a list of all events available.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of events retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EventDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return getAllEventsUsecase.execute();
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get an Event by the UUID - (Protected by Guards): ANY",
            description = "Retrieves an event by its unique identifier (UUID).",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    public ResponseEntity<EventDTO> getEventById(@PathVariable UUID id) {
        Optional<Event> eventOptional = getEventByIdUsecase.execute(id);

        return eventOptional
                .map(event -> ResponseEntity.ok(EventDTO.eventToDTO(event)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @Operation(
            summary = "Create a new Event - (Protected by Guards): ADMIN",
            description = "Creates a new event with the provided details.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Event created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventDTO eventDTO) {
        Event createdEvent = createEventUsecase.execute(EventDTO.dtoToEvent(eventDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PostMapping(value = "/subscribe")
    @Operation(
            summary = "Subscribe a User to an Event - (Protected by Guards): ADMIN",
            description = "Subscribes a user to a specific event.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User subscribed successfully",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request OR event is already full OR user is already subscribed in this event",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event or user not found",
                            content = @Content()
                    )
            }
    )
    public ResponseEntity<Object> subscribeUserInEvent(@RequestBody @Valid SubscribeUserInEventDTO subscribeUserInEventDTO) {
        return subscribeInEventUsecase.execute(UUID.fromString(subscribeUserInEventDTO.getEventId()), UUID.fromString(subscribeUserInEventDTO.getUserId()));
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Update an Event by the UUID - (Protected by Guards): ADMIN",
            description = "Updates an existing event with the provided details.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content()
                    )
            }
    )
    public ResponseEntity<Event> updateEventById(@PathVariable UUID id, @RequestBody @Valid EventDTO eventDTO) {
        Event newEvent = EventDTO.dtoToEvent(eventDTO);
        Optional<Event> optionalUpdatedEvent = updateEventByIdUsecase.execute(id, newEvent);

        if (optionalUpdatedEvent.isEmpty()) return ResponseEntity.notFound().build();

        Event updatedEvent = optionalUpdatedEvent.get();

        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete an Event by the UUID - (Protected by Guards): ADMIN",
            description = "Deletes an event by its unique identifier (UUID).",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event deleted successfully",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content()
                    )
            }
    )
    public ResponseEntity<Event> deleteEventById(@PathVariable UUID id) {
        Optional<Event> optionalDeletedEvent = deleteEventByIdUsecase.execute(id);

        if (optionalDeletedEvent.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

}
