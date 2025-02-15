package com.griotold.backend_onboarding.infra.repository;

import com.griotold.backend_onboarding.domain.User;
import com.griotold.backend_onboarding.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepositoryImpl extends JpaRepository<User, Long>, UserRepository {

    Optional<User> findByUsername(String username);

}
