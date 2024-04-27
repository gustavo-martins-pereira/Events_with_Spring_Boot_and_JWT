package com.vitai.events.controllers.authentication.dtos;

import com.vitai.events.domain.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotNull(message = "The name cannot be blank")
        @Size(min = 3, max = 30, message = "Name length must be between 3 and 30 characters")
        String name,

        @NotNull(message = "The login cannot be blank")
        @Size(min = 3, max = 30, message = "Login length must be between 3 and 30 characters")
        String login,

        @NotNull(message = "The password cannot be blank")
        @Size(min = 6, max = 30, message = "Password length must be between 6 and 30 characters")
        String password,

        @NotNull(message = "The password cannot be blank")
        UserRole role
) {}
