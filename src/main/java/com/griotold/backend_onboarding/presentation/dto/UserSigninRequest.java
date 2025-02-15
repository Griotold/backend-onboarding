package com.griotold.backend_onboarding.presentation.dto;

import com.griotold.backend_onboarding.application.dto.UserSignin;
import jakarta.validation.constraints.NotBlank;

public record UserSigninRequest(
        @NotBlank
        String username,

        @NotBlank
        String password
) {

    public UserSignin toServiceDto() {
        return new UserSignin(username, password);
    }
}
