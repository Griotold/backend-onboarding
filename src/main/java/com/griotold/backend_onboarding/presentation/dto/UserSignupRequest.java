package com.griotold.backend_onboarding.presentation.dto;

import com.griotold.backend_onboarding.application.dto.UserSignup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignupRequest(
        @NotBlank
        @Size(min = 4, max = 10, message = "유저 이름은 최소 4자 이상, 10자 이하여야 합니다.")
        @Pattern(regexp = "^[a-z]+$", message = "유저 이름은 영어 소문자로만 구성되어야 합니다.")
        String username,

        @NotBlank
        @Size(min = 8, max = 15, message = "비밀번호은 최소 8자 이상, 15자 이하여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "비밀번호는 영어 대소문자, 숫자, 특수문자를 포함해야 합니다.")
        String password,

        @NotBlank
        @Size(min = 4, max = 10, message = "닉네임은 최소 4자 이상, 10자 이하여야 합니다.")
        @Pattern(regexp = "^[a-z]+$", message = "닉네임은 영어 소문자로만 구성되어야 합니다.")
        String nickname
) {

    public UserSignup toServiceDto() {
        return new UserSignup(username, password, nickname);
    }
}
