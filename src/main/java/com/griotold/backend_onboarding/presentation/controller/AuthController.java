package com.griotold.backend_onboarding.presentation.controller;

import com.griotold.backend_onboarding.application.dto.UserSignupResponse;
import com.griotold.backend_onboarding.application.service.AuthService;
import com.griotold.backend_onboarding.presentation.dto.ApiResponse;
import com.griotold.backend_onboarding.presentation.dto.UserSigninRequest;
import com.griotold.backend_onboarding.presentation.dto.UserSignupRequest;
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

    @PostMapping("/signin")
    public ApiResponse<String> signin(@RequestBody @Valid UserSigninRequest request) {
        log.info("signIn.UserSigninRequest: {}", request);
        return ApiResponse.success(authService.signin(request.toServiceDto()));
    }
}
