package com.vitai.events.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN(1),
    USER(2);

    private final int code;

    private UserRole(int code) {
        this.code = code;
    }

    public static UserRole valueOf(int code) {
        for(UserRole value : UserRole.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid UserRole code");
    }

}
