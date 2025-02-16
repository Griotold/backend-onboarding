package com.griotold.backend_onboarding.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("고객"),
    ADMIN("관리자"),
    ;

    private final String description;
}
