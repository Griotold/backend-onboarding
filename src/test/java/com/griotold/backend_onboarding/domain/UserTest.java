package com.griotold.backend_onboarding.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @DisplayName("create 메서드로 User를 생성한다.")
    @Test
    void createTest1() {
        // given
        String username = "testUser";
        String password = "password123";
        Role role = Role.ADMIN;
        String nickname = "테스트닉네임";

        // when
        User user = User.create(username, password, role, nickname);

        // then
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getRole()).isEqualTo(role);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getId()).isNull();
    }

    @DisplayName("create - Role을 지정하지 않으면 기본값은 Role.User 이다.")
    @Test
    void createTest2() {
        // given - Role을 지정하지 않았을 때
        String username = "testUser";
        String password = "password123";
        String nickname = "테스트닉네임";

        // when
        User user = User.create(username, password, nickname);

        // then
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getId()).isNull();
    }

}