package com.griotold.backend_onboarding.application.dto;

public record UserSignup(
        String username,
        String password,
        String nickname
) {
}
