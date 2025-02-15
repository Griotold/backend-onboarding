package com.griotold.backend_onboarding.application.service;

import com.griotold.backend_onboarding.application.dto.UserSignup;
import com.griotold.backend_onboarding.application.dto.UserSignupResponse;
import com.griotold.backend_onboarding.application.exception.AuthException;
import com.griotold.backend_onboarding.application.exception.ErrorCode;
import com.griotold.backend_onboarding.domain.Role;
import com.griotold.backend_onboarding.domain.User;
import com.griotold.backend_onboarding.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        // given
        UserSignup signup = new UserSignup("testUser", "pass1234", "mentos");

        // when
        UserSignupResponse response = authService.signup(signup);

        // then - 응답 확인
        assertThat(response.username()).isEqualTo(signup.username());
        assertThat(response.nickname()).isEqualTo(signup.nickname());
        assertThat(response.authorityName()).isEqualTo(Role.USER.name());

        // and - 데이터베이스 확인
        User user = userRepository.findByUsername("testUser").get();
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getNickname()).isEqualTo("mentos");
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 username")
    void signup_whenUsernameIsDuplicated() {
        // given
        userRepository.save(User.create("testUser", "pass1234", "mentos"));
        UserSignup signup = new UserSignup("testUser", "pass4567", "nick");

        // when & then
        assertThatThrownBy(() -> authService.signup(signup))
                .isInstanceOf(AuthException.class)
                .hasMessage(ErrorCode.DUPLICATE_USERNAME.getMessage());

    }

}