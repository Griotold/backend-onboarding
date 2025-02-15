package com.griotold.backend_onboarding.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("findByUsername - username으로 조회")
    @Test
    void findByUsername_success() {
        // given
        User user = User.create("testUser", "1234", "mentos");
        userRepository.save(user);

        // when
        Optional<User> optionalUser = userRepository.findByUsername("testUser");

        // then
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get().getUsername()).isEqualTo("testUser");
    }

    @DisplayName("findByUsername - 존재하지 않는 username으로 조회시 빈 Optional 반환")
    @Test
    void findByUsername_whenUserNotExists() {
        // given
        String nonExistentUsername = "nonexistent";

        // when
        Optional<User> optionalUser = userRepository.findByUsername(nonExistentUsername);

        // then
        assertThat(optionalUser).isEmpty();
    }

}