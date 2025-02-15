package com.griotold.backend_onboarding.presentation;

import com.griotold.backend_onboarding.application.dto.UserSignupResponse;
import com.griotold.backend_onboarding.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<UserSignupResponse> signup(@RequestBody @Valid UserSignupRequest request) {
        log.info("signUp.UserSignUpRequest: {}", request);
        return ApiResponse.success(authService.signup(request.toServiceDto()));
    }
}
