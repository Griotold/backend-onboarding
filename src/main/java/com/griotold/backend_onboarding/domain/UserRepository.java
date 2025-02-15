package com.griotold.backend_onboarding.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> findByUsername(String username);
}
