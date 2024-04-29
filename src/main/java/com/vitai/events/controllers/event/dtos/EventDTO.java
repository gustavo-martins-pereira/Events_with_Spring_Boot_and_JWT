package com.vitai.events.controllers.event.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vitai.events.domain.Event;
import com.vitai.events.utils.annotations.FutureDate;
import com.vitai.events.utils.serialization.CustomInstantDeserializer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class EventDTO {

    @NotNull(message = "The name of the event cannot be blank")
    @NotBlank(message = "The name of the event cannot be blank")
    @Size(min = 5, max = 30, message = "Event name length must be between 5 and 30 characters")
    private String name;

    @NotNull(message = "The date of the event cannot be null")
    @FutureDate
    @JsonDeserialize(using = CustomInstantDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Instant date;

    @NotNull(message = "The local cannot be blank")
    @NotBlank(message = "The local cannot be blank")
    @Size(min = 3, max = 30, message = "Local length must be between 3 and 30 characters")
    private String local;

    @NotNull(message = "The maxCapacity is required")
    @Positive(message = "maxCapacity must be positive")
    private Integer maxCapacity;

    public static Event dtoToEvent(EventDTO eventDTO) {
        return Event.builder()
                .name(eventDTO.getName())
                .date(eventDTO.getDate())
                .local(eventDTO.getLocal())
                .maxCapacity(eventDTO.getMaxCapacity())
                .build();
    }

    public static EventDTO eventToDTO(Event event) {
        return EventDTO.builder()
                .name(event.getName())
                .date(event.getDate())
                .local(event.getLocal())
                .maxCapacity(event.getMaxCapacity())
                .build();
    }

}
