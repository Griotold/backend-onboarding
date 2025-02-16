package com.griotold.backend_onboarding.application.service;

import com.griotold.backend_onboarding.application.dto.UserSignin;
import com.griotold.backend_onboarding.application.dto.UserSignup;
import com.griotold.backend_onboarding.application.dto.UserSignupResponse;
import com.griotold.backend_onboarding.application.exception.AuthException;
import com.griotold.backend_onboarding.application.exception.ErrorCode;
import com.griotold.backend_onboarding.application.interfaces.TokenProvider;
import com.griotold.backend_onboarding.domain.User;
import com.griotold.backend_onboarding.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserSignupResponse signup(UserSignup signup) {
        log.info("signup.UserSignup: {}", signup);

        validateDuplicateUsername(signup.username());

        User user = User.create(
                signup.username(),
                passwordEncoder.encode(signup.password()),
                signup.nickname()
        );
        return UserSignupResponse.from(userRepository.save(user));
    }

    private void validateDuplicateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATE_USERNAME);
        }
    }

    public String signin(UserSignin signin) {
        log.info("signin.UserSignin: {}", signin);
        User user = userRepository.findByUsername(signin.username())
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

        validatePassword(signin.password(), user.getPassword());

        return tokenProvider.createAccessToken(user.getId(), user.getRole());
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new AuthException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
