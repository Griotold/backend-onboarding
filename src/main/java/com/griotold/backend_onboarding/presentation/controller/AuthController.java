package com.griotold.backend_onboarding.presentation.controller;

import com.griotold.backend_onboarding.application.dto.UserSignupResponse;
import com.griotold.backend_onboarding.application.service.AuthService;
import com.griotold.backend_onboarding.presentation.dto.ApiResponse;
import com.griotold.backend_onboarding.presentation.dto.UserSigninRequest;
import com.griotold.backend_onboarding.presentation.dto.UserSignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Auth", description = "Auth api")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입 API", description = "회원가입", tags = {"Auth"})
    @PostMapping("/signup")
    public ApiResponse<UserSignupResponse> signup(@RequestBody @Valid UserSignupRequest request) {
        log.info("signUp.UserSignUpRequest: {}", request);
        return ApiResponse.success(authService.signup(request.toServiceDto()));
    }

    @Operation(summary = "로그인 API", description = "로그인", tags = {"Auth"})
    @PostMapping("/signin")
    public ApiResponse<String> signin(@RequestBody @Valid UserSigninRequest request) {
        log.info("signIn.UserSigninRequest: {}", request);
        return ApiResponse.success(authService.signin(request.toServiceDto()));
    }
}
