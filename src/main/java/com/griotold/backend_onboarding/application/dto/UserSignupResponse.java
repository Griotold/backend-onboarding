package com.griotold.backend_onboarding.application.dto;

import com.griotold.backend_onboarding.domain.User;

public record UserSignupResponse(
        String  username,
        String nickname,
        String authorityName
) {
    public static UserSignupResponse from(final User user) {
        return new UserSignupResponse(
                user.getUsername(), user.getNickname(), user.getRole().name()
        );
    }
}
