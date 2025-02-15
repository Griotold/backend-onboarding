package com.griotold.backend_onboarding.infra.config.jwt;

import com.griotold.backend_onboarding.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String encodedKey = Encoders.BASE64URL.encode(secretKey.getEncoded());
        jwtTokenProvider = new JwtTokenProvider(encodedKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "issuer", "auth-service");
        ReflectionTestUtils.setField(jwtTokenProvider, "accessExpiration", 3600000L);
    }

    @Test
    @DisplayName("JWT 토큰 생성 테스트")
    void createAccessTokenTest() {
        // given
        Long userId = 1L;
        Role role = Role.USER;

        // when
        String token = jwtTokenProvider.createAccessToken(userId, role);

        // then
        assertThat(token).isNotNull();

        Claims claims = Jwts.parser()
                .verifyWith(jwtTokenProvider.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertThat(claims.get("user_id", Long.class)).isEqualTo(userId);
        assertThat(claims.get("role")).isEqualTo(role.name());
        assertThat(claims.getIssuer()).isEqualTo("auth-service");
    }
}