package com.vitai.events.controllers.event.dtos;

import com.vitai.events.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventDTO {

    private String name;
    private Instant date;
    private String local;
    private Integer maxCapacity;

    public static Event DtoToEvent(EventDTO eventDTO) {
        return Event.builder()
                .name(eventDTO.getName())
                .date(eventDTO.getDate())
                .local(eventDTO.getLocal())
                .maxCapacity(eventDTO.getMaxCapacity())
                .build();
    }

}
