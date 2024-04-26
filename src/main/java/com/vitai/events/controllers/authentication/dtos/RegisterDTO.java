package com.vitai.events.controllers.authentication.dtos;

import com.vitai.events.domain.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
