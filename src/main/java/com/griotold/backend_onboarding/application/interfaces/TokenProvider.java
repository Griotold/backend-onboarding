package com.griotold.backend_onboarding.application.interfaces;

import com.griotold.backend_onboarding.domain.Role;

public interface TokenProvider {
    String createAccessToken(Long userId, Role role);
}
