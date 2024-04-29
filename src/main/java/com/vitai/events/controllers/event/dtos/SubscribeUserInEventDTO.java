package com.vitai.events.controllers.event.dtos;

import com.vitai.events.utils.annotations.ValidUUID;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SubscribeUserInEventDTO {

    @NotNull(message = "The eventId cannot be blank")
    @ValidUUID
    private String eventId;

    @NotNull(message = "The userId cannot be blank")
    @ValidUUID
    private String userId;

}
