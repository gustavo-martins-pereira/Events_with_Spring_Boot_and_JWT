package com.vitai.events.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("Admin"),
    USER("User");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

}
